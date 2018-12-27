package fr.kybox.service;

import fr.kybox.dao.*;
import fr.kybox.entities.*;
import fr.kybox.entities.ReservedBook;
import fr.kybox.entities.UserEntity;
import fr.kybox.gencode.*;

import fr.kybox.security.Password;
import fr.kybox.service.utils.CheckParams;
import fr.kybox.utils.Converter;
import fr.kybox.utils.DateUtils;
import fr.kybox.utils.Reflection;
import fr.kybox.utils.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.jws.WebMethod;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author Kybox
 * @version 1.0
 */


public class LibraryServiceImpl extends SpringBeanAutowiringSupport implements LibraryService {

    private final static Logger logger = LogManager.getLogger(LibraryServiceImpl.class);

    // ERROR CODE
    private final int INTERNAL_SERVER_ERROR = 500;
    private final int UNAUTHORIZED = 401;
    private final int BAD_REQUEST = 400;
    private final int FORBIDDEN = 403;
    private final int CONFLICT = 409;
    private final int CREATED = 201;
    private final int OK = 200;
    private final int NOT_MODIFIED = 304;
    private final int TOKEN_EXPIRED_INVALID = 498;

    // USER LEVEL
    private final int ADMIN = 1;
    private final int MANAGER = 2;

    @Autowired private BorrowedBooksRepository borrowedBooksRepository;
    @Autowired private AuthorRepository authorRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private BookRepository bookRepository;
    @Autowired private TokenRepository tokenRepository;
    @Autowired private ReservedBookRepository reservedBookRepository;

    @Value("${settings.nbWeeksToExtend}")
    private int nbWeeksToExtend;

    @Value("${settings.multiplierReservation}")
    private int multiplierReservation;

    @Autowired private Token tokenManager;
    @Autowired private ObjectFactory objectFactory;

    @Override
    @WebMethod
    public LoginResponse login(Login parameters) {

        LoginResponse loginResponse = objectFactory.createLoginResponse();

        if(CheckParams.login(parameters)){

            UserEntity userEntity = userRepository.findByEmail(parameters.getLogin());

            if (userEntity != null) {

                if (Password.match(parameters.getPassword(), userEntity.getPassword())) {
                    loginResponse.setUser((User) Reflection.EntityToWS(userEntity));

                    TokenStorage tokenStorage = tokenRepository.findByUserEntity(userEntity);
                    if(tokenStorage != null) tokenRepository.delete(tokenStorage);

                    tokenManager.generateNew(userEntity);
                    tokenRepository.save(tokenManager.getTokenStorage());
                    loginResponse.setToken(tokenManager.getTokenStorage().getToken());

                    loginResponse.setResult(OK);
                }
                else loginResponse.setResult(BAD_REQUEST);
            }
            else loginResponse.setResult(BAD_REQUEST);
        }

        return loginResponse;
    }

    @Override
    @WebMethod
    public int checkToken(String token) {

        Map<String, Object> tokenData = tokenManager.checkToken(token);
        if(tokenData.get("active") == Boolean.TRUE) return OK;
        else return TOKEN_EXPIRED_INVALID;
    }

    @Override
    @WebMethod
    public void logout(String token) {

        Optional<TokenStorage> optTokenStorage = tokenRepository.findByToken(token);
        optTokenStorage.ifPresent(t -> tokenRepository.delete(t));
    }

    @Override
    @WebMethod
    public GetBookResponse getBook(GetBook parameter) {

        GetBookResponse response = objectFactory.createGetBookResponse();

        Optional<BookEntity> optBookEntity = bookRepository.findByIsbn(parameter.getIsbn());
        if(!optBookEntity.isPresent()){
            response.setResult(BAD_REQUEST);
            return response;
        }

        response.setBook((Book) Reflection.EntityToWS(optBookEntity.get()));
        response.setResult(OK);
        return response;
    }

    @Override
    @WebMethod
    public int reserveBook(String token, String isbn) {

        int result = 0;

        Map<String, Object> tokenData = tokenManager.checkToken(token);
        if(tokenData.get(Token.ACTIVE) == Boolean.FALSE){
            result = TOKEN_EXPIRED_INVALID;
            return result;
        }

        Optional<BookEntity> optBookEntity = bookRepository.findByIsbn(isbn);
        if(!optBookEntity.isPresent()){
            result = BAD_REQUEST;
            return result;
        }

        BookEntity bookEntity = optBookEntity.get();
        UserEntity userEntity = (UserEntity) tokenData.get(Token.USER);

        List<ReservedBook> bookList = reservedBookRepository.findAllByUser(userEntity);

        for(ReservedBook reservation : bookList) {
            if (bookEntity.getIsbn().equals(reservation.getBook().getIsbn()) && reservation.isPending()) {
                result = FORBIDDEN;
                return result;
            }
        }

        if (bookEntity.getBookable()) {

            ReservedBook reservedBook = new ReservedBook();
            reservedBook.setUser(userEntity);
            reservedBook.setBook(bookEntity);
            reservedBook.setReserveDate(LocalDateTime.now());
            reservedBook.setPending(true);

            reservedBookRepository.save(reservedBook);

            checkReservationStatus(reservedBook.getBook());

            result = OK;
        }
        else result = BAD_REQUEST;

        return result;
    }

    @Override
    @WebMethod
    public CreateUserResponse createUser(CreateUser parameters) {

        CreateUserResponse response = objectFactory.createCreateUserResponse();

        response.setResult(BAD_REQUEST);

        if(CheckParams.createUser(parameters)){

            Login login = parameters.getLogin();

            UserEntity userEntity = userRepository.findByEmail(login.getLogin());

            if(userEntity != null){

                if(Password.match(login.getPassword(), userEntity.getPassword())){

                    if(userEntity.getLevel().getId() == MANAGER) {

                        userEntity = (UserEntity) Reflection.WStoEntity(parameters.getUser());

                        try {
                            userRepository.save(userEntity);
                            response.setResult(CREATED);
                        }
                        catch (HibernateException e){
                            logger.warn(e.getMessage());
                            response.setResult(INTERNAL_SERVER_ERROR);
                        }
                    }
                    else {
                        logger.warn("Unauthorized action with this login");
                        response.setResult(UNAUTHORIZED);
                    }
                }
            }
        }

        return response;
    }

    @Override
    @WebMethod
    public LoanReturnResponse loanReturn(LoanReturn parameters) {

        LoanReturnResponse response = objectFactory.createLoanReturnResponse();

        Map<String, Object> tokenData = tokenManager.checkToken(parameters.getToken());
        if(tokenData.get(Token.ACTIVE) == Boolean.FALSE){
            response.setResult(TOKEN_EXPIRED_INVALID);
            return response;
        }

        UserEntity userEntity = (UserEntity) tokenData.get(Token.USER);
        if(userEntity.getLevel().getId() >= MANAGER){
            response.setResult(UNAUTHORIZED);
            return response;
        }

        Optional<BookEntity> optBookEntity = bookRepository
                .findByIsbn(parameters.getBookBorrowed().getBook().getIsbn());
        if(!optBookEntity.isPresent()){
            response.setResult(BAD_REQUEST);
            return response;
        }

        BorrowedBook borrowedBook = borrowedBooksRepository
                .findByUserAndBook(userEntity, optBookEntity.get());

        if(!borrowedBook.getReturned()) {
            borrowedBook.setReturned(true);
            borrowedBooksRepository.save(borrowedBook);
            response.setResult(OK);
        }
        else response.setResult(CONFLICT);

        return response;
    }

    @Override
    public int setReservationNotified(String token, int bookid, int userid, java.util.Date reserveDate) {

        int response;

        Map<String, Object> tokenData = tokenManager.checkToken(token);
        if(tokenData.get(Token.ACTIVE) == Boolean.FALSE){
            response = TOKEN_EXPIRED_INVALID;
            return response;
        }

        Optional<BookEntity> optBookEntity = bookRepository.findById(bookid);
        if(!optBookEntity.isPresent()){
            response = BAD_REQUEST;
            return response;
        }

        UserEntity userEntity = (UserEntity) tokenData.get(Token.USER);
        BookEntity bookEntity = optBookEntity.get();

        Optional<ReservedBook> optReservedBook = reservedBookRepository
                .findByUserAndBookAndPendingTrue(userEntity, bookEntity);
        if(!optReservedBook.isPresent()){
            response = BAD_REQUEST;
            return response;
        }

        ReservedBook reservedBook = optReservedBook.get();

        java.util.Date reservationDate = Date.from(reservedBook.getReserveDate()
                .atZone(ZoneId.systemDefault()).toInstant());

        logger.info("Param date = " + reserveDate);
        logger.info("Object date = " + reservationDate);

        if(!reservationDate.toString().equals(reserveDate.toString())){
            response = BAD_REQUEST;
            return response;
        }

        if(!reservedBook.isNotified()) {
            reservedBook.setNotified(true);
            reservedBookRepository.save(reservedBook);
        }

        response = OK;
        return response;
    }

    @Override
    @WebMethod
    public ExtendBorrowingResponse extendBorrowing(ExtendBorrowing parameters) {

        ExtendBorrowingResponse response = objectFactory.createExtendBorrowingResponse();

        Map<String, Object> tokenData = tokenManager.checkToken(parameters.getToken());
        if(tokenData.get(Token.ACTIVE) == Boolean.FALSE){
            response.setResult(TOKEN_EXPIRED_INVALID);
            return response;
        }

        Optional<BookEntity> optBookEntity = bookRepository
                .findByIsbn(parameters.getBookBorrowed().getBook().getIsbn());

        if(!optBookEntity.isPresent()){
            response.setResult(BAD_REQUEST);
            return response;
        }

        BookEntity bookEntity = optBookEntity.get();
        UserEntity userEntity = (UserEntity) tokenData.get(Token.USER);
        BorrowedBook borrowedBook = borrowedBooksRepository.findByUserAndBook(userEntity, bookEntity);

        LocalDate localDate = LocalDate.fromDateFields(borrowedBook.getReturnDate());
        localDate = localDate.plusWeeks(nbWeeksToExtend);
        borrowedBook.setReturnDate(Converter.DateToSQLDate(localDate.toDate()));

        borrowedBook.setExtended(true);

        borrowedBooksRepository.save(borrowedBook);

        if(bookEntity.getBookable()){

            Boolean dateChecked = DateUtils.isDateBefore(borrowedBook.getReturnDate(), bookEntity.getReturnDate());

            if(dateChecked != null && !dateChecked) {
                bookEntity.setReturnDate(borrowedBook.getReturnDate());
                bookRepository.save(bookEntity);
            }
        }

        response.setResult(OK);
        return response;
    }

    @Override
    @WebMethod
    public ReservedBookListResponse reservedBookList(ReservedBookList parameters) {

        ReservedBookListResponse response = objectFactory.createReservedBookListResponse();

        Map<String, Object> tokenData = tokenManager.checkToken(parameters.getToken());
        if(tokenData.get(Token.ACTIVE) == Boolean.FALSE){
            response.setResult(TOKEN_EXPIRED_INVALID);
            return response;
        }

        UserEntity userEntity = (UserEntity) tokenData.get(Token.USER);
        List<ReservedBook> bookList = reservedBookRepository.findAllByUser(userEntity);

        for(ReservedBook reservedBook : bookList){
            BookReserved bookReserved = new BookReserved();
            Book book = (Book) Reflection.EntityToWS(reservedBook.getBook());
            bookReserved.setBook(book);
            Instant instant = reservedBook.getReserveDate().atZone(ZoneId.systemDefault()).toInstant();
            bookReserved.setReserveDate(Date.from(instant));
            bookReserved.setPending(reservedBook.isPending());
            bookReserved.setNotified(reservedBook.isNotified());
            response.getBookReserved().add(bookReserved);

            if(reservedBook.isPending()) {
                List<ReservedBook> reservedBookList = reservedBookRepository
                        .findAllByBookAndPendingTrueOrderByReserveDateAsc(reservedBook.getBook());
                bookReserved.setTotal(reservedBookList.size());

                for(int i = 0; i < reservedBookList.size(); i++){
                    if(reservedBookList.get(i).getUser().getId().equals(userEntity.getId())){
                        bookReserved.setPosition(i + 1);
                        break;
                    }
                }
            }
        }

        response.setResult(OK);
        return response;
    }

    @Override
    @WebMethod
    public UserBookListResponse userBookList(UserBookList parameter) {

        UserBookListResponse response = objectFactory.createUserBookListResponse();

        Map<String, Object> tokenData = tokenManager.checkToken(parameter.getToken());
        if(tokenData.get(Token.ACTIVE) == Boolean.FALSE){
            response.setResult(TOKEN_EXPIRED_INVALID);
            return response;
        }

        UserEntity userEntity = (UserEntity) tokenData.get(Token.USER);
        List<BorrowedBook> bookList = borrowedBooksRepository.findAllByUserOrderByReturnedAsc(userEntity);

        for (BorrowedBook borrowedBook : bookList) {

            BookBorrowed bookBorrowed = new BookBorrowed();
            bookBorrowed.setBook((Book) Reflection.EntityToWS(borrowedBook.getBook()));
            bookBorrowed.setExtended(borrowedBook.getExtended());
            bookBorrowed.setReturnDate(borrowedBook.getReturnDate());
            bookBorrowed.setReturned(borrowedBook.getReturned());

            response.getBookBorrowed().add(bookBorrowed);
        }

        response.setResult(OK);
        return response;
    }

    @Override
    @WebMethod
    public CheckReservedBooksResponse checkReservedBooks(CheckReservedBooks parameter) {

        CheckReservedBooksResponse response = objectFactory.createCheckReservedBooksResponse();

        Map<String, Object> tokenData = tokenManager.checkToken(parameter.getToken());
        if(tokenData.get(Token.ACTIVE) == Boolean.FALSE) {
            response.setResult(TOKEN_EXPIRED_INVALID);
            return response;
        }

        UserEntity userEntity = (UserEntity) tokenData.get(Token.USER);
        if(!userEntity.getLevel().getId().equals(ADMIN)){
            response.setResult(FORBIDDEN);
            return response;
        }

        List<BookEntity> bookList = bookRepository.findAllByBookable(true);

        for(BookEntity bookEntity : bookList){

            if(bookEntity.getAvailable() == 0) continue;

            List<ReservedBook> reservedBookList = reservedBookRepository
                    .findAllByBookAndPendingTrueOrderByReserveDateAsc(bookEntity);

            for(int i = 0; i < bookEntity.getAvailable(); i++){

                Optional<UserEntity> optUser = userRepository
                        .findById(reservedBookList.get(i).getUser().getId());

                if(optUser.isPresent()){

                    BookReserved bookReserved = (BookReserved) Reflection.EntityToWS(reservedBookList.get(i));
                    response.getBookReserved().add(bookReserved);

                    User user = (User) Reflection.EntityToWS(optUser.get());
                    response.getUser().add(user);
                }
            }
        }
        response.setResult(OK);
        return response;
    }

    @Override
    @WebMethod
    public UnreturnedBookListResponse unreturnedBookList(UnreturnedBookList parameters) {

        UnreturnedBookListResponse response = objectFactory.createUnreturnedBookListResponse();

        response.setResult(BAD_REQUEST);

        Map<String, Object> tokenData = tokenManager.checkToken(parameters.getToken());
        if(tokenData.get(Token.ACTIVE) == Boolean.FALSE){
            response.setResult(TOKEN_EXPIRED_INVALID);
            return response;
        }

        UserEntity userEntity = (UserEntity) tokenData.get(Token.USER);
        if(userEntity.getLevel().getId() != ADMIN){
            response.setResult(FORBIDDEN);
            return response;
        }

        Iterable<BorrowedBook> itemList =
                borrowedBooksRepository
                        .findAllByReturnDateBeforeAndReturnedFalse(new Date(System.currentTimeMillis()));

        for(BorrowedBook borrowedBook : itemList){

            UnreturnedBook unreturnedBook = new UnreturnedBook();
            unreturnedBook.setUser((User) Reflection.EntityToWS(borrowedBook.getUser()));

            BookBorrowed bookBorrowed = objectFactory.createBookBorrowed();
            bookBorrowed.setBook((Book) Reflection.EntityToWS(borrowedBook.getBook()));
            bookBorrowed.setExtended(borrowedBook.getExtended());
            bookBorrowed.setReturnDate(borrowedBook.getReturnDate());
            bookBorrowed.setReturned(borrowedBook.getReturned());

            unreturnedBook.setBookBorrowed(bookBorrowed);

            response.getUnreturnedBook().add(unreturnedBook);
        }

        response.setResult(OK);
        return response;
    }

    @Override
    @WebMethod
    public int cancelReservation(String token, String isbn) {

        int response;

        Map<String, Object> tokenData = tokenManager.checkToken(token);
        if(tokenData.get(Token.ACTIVE) == Boolean.FALSE){
            response = TOKEN_EXPIRED_INVALID;
            return response;
        }

        Optional<BookEntity> optBookEntity = bookRepository.findByIsbn(isbn);
        if(!optBookEntity.isPresent()){
            response = BAD_REQUEST;
            return response;
        }

        BookEntity bookEntity = optBookEntity.get();
        UserEntity userEntity = (UserEntity) tokenData.get(Token.USER);
        Optional<ReservedBook> optReservedBook = reservedBookRepository
                .findByUserAndBookAndPendingTrue(userEntity, bookEntity);

        if(!optReservedBook.isPresent()) {
            response = NOT_MODIFIED;
            return response;
        }

        reservedBookRepository.delete(optReservedBook.get());
        response = OK;
        return response;
    }

    @Override
    @WebMethod
    public SearchBookResponse searchBook(SearchBook parameters) {

        Iterable<BookEntity> bookList =
                bookRepository.
                        findAllByTitleContainingOrAuthor_NameContainingOrGenre_NameContainingAllIgnoreCase
                                (parameters.getKeywords(), parameters.getKeywords(), parameters.getKeywords());

        BookList resultList = new BookList();

        for(BookEntity bookEntity : bookList)
            resultList.getBook().add((Book) Reflection.EntityToWS(bookEntity));

        SearchBookResponse searchBookResponse = new SearchBookResponse();
        searchBookResponse.setBookList(resultList);

        return searchBookResponse;
    }

    private void checkReservationStatus(BookEntity bookEntity){

        int nbCopies = bookEntity.getNbCopies();
        int maxReservedBook = nbCopies * multiplierReservation;

        List<ReservedBook> reservedBookList = reservedBookRepository
                .findAllByBookAndPendingTrueOrderByReserveDateAsc(bookEntity);


        if(reservedBookList.size() >= maxReservedBook) {
            bookEntity.setBookable(false);
            bookEntity.setReturnDate(null);
        }
        else {
            bookEntity.setBookable(true);
            bookEntity.setReturnDate(getNextReturnDate(bookEntity));
        }

        bookRepository.save(bookEntity);
    }

    private Date getNextReturnDate(BookEntity bookEntity){

        List<BorrowedBook> borrowedBookList = borrowedBooksRepository
                .findAllByBookAndReturnedFalseOrderByReturnDateAsc(bookEntity);

        // Nearest return date (order by asc)
        return borrowedBookList.get(0).getReturnDate();
    }
}
