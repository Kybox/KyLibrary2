package fr.kybox.service.impl;

import fr.kybox.entities.*;
import fr.kybox.entities.ReservedBook;
import fr.kybox.entities.UserEntity;
import fr.kybox.gencode.*;

import fr.kybox.security.Password;
import fr.kybox.service.BookService;
import fr.kybox.service.UserService;
import fr.kybox.utils.CheckParams;
import fr.kybox.utils.Converter;
import fr.kybox.utils.DateUtils;
import fr.kybox.utils.Reflection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import static fr.kybox.utils.ValueTypes.*;

/**
 * @author Kybox
 * @version 1.0
 */


public class LibraryServiceImpl extends SpringBeanAutowiringSupport implements LibraryService {

    private final static Logger logger = LogManager.getLogger(LibraryServiceImpl.class);

    @Value("${settings.nbWeeksToExtend}")
    private int nbWeeksToExtend;

    @Value("${settings.multiplierReservation}")
    private int multiplierReservation;

    @Autowired private ObjectFactory objectFactory;
    @Autowired private BookService bookService;
    @Autowired private UserService userService;

    @Override
    @WebMethod
    public LoginResponse login(Login parameters) {

        LoginResponse response = objectFactory.createLoginResponse();

        String login = parameters.getLogin();
        String password = parameters.getPassword();

        if(login == null || login.isEmpty() || password == null || password.isEmpty()) {
            response.setResult(BAD_REQUEST);
            return response;
        }

        Optional<UserEntity> optUser = userService.findUserByEmail(login);
        if(!optUser.isPresent()){
            response.setResult(BAD_REQUEST);
            return response;
        }

        UserEntity user = optUser.get();
        if (Password.match(parameters.getPassword(), user.getPassword())) {
            response.setUser((User) Reflection.EntityToWS(user));

            Optional<TokenStorage> optToken = userService.findTokenByUser(user);
            optToken.ifPresent(t -> userService.deleteToken(t));

            TokenStorage tokenStorage = userService.generateNewTokenForUser(user);
            userService.saveToken(tokenStorage);
            response.setToken(tokenStorage.getToken());

            response.setResult(OK);
        }
        else response.setResult(BAD_REQUEST);

        return response;
    }

    @Override
    @WebMethod
    public int checkToken(String token) {

        Map<String, Object> tokenData = userService.checkTokenData(token);
        if(tokenData.get(TOKEN_ACTIVE) == Boolean.TRUE) return OK;
        else return TOKEN_EXPIRED_INVALID;
    }

    @Override
    @WebMethod
    public void logout(String token) {

        Optional<TokenStorage> optTokenStorage = userService.findTokenByHash(token);
        optTokenStorage.ifPresent(t -> userService.deleteToken(t));
    }

    @Override
    @WebMethod
    public GetBookResponse getBook(GetBook parameter) {

        GetBookResponse response = objectFactory.createGetBookResponse();

        Optional<BookEntity> optBookEntity = bookService.findBookByIsbn(parameter.getIsbn());
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

        Map<String, Object> tokenData = userService.checkTokenData(token);
        if(tokenData.get(TOKEN_ACTIVE) == Boolean.FALSE){
            result = TOKEN_EXPIRED_INVALID;
            return result;
        }

        Optional<BookEntity> optBookEntity = bookService.findBookByIsbn(isbn);
        if(!optBookEntity.isPresent()){
            result = BAD_REQUEST;
            return result;
        }

        BookEntity book = optBookEntity.get();
        UserEntity user = (UserEntity) tokenData.get(USER_FROM_TOKEN);

        List<ReservedBook> bookList = bookService.findAllReservedBooksByUser(user);

        for(ReservedBook reservation : bookList) {
            if (book.getIsbn().equals(reservation.getBook().getIsbn()) && reservation.isPending()) {
                result = FORBIDDEN;
                return result;
            }
        }

        if (book.getBookable()) {

            ReservedBook reservedBook = new ReservedBook();
            reservedBook.setUser(user);
            reservedBook.setBook(book);
            reservedBook.setReserveDate(LocalDateTime.now());
            reservedBook.setPending(true);

            bookService.saveReservedBook(reservedBook);

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

        if(!CheckParams.createUser(parameters)){
            response.setResult(BAD_REQUEST);
            return response;
        }

        String email = parameters.getLogin().getLogin();
        Optional<UserEntity> optUser = userService.findUserByEmail(email);
        if(!optUser.isPresent()){
            response.setResult(BAD_REQUEST);
            return response;
        }

        UserEntity user = optUser.get();
        Login login = parameters.getLogin();
        if(Password.match(login.getPassword(), user.getPassword())){

            if(user.getLevel().getId() == MANAGER) {

                user = (UserEntity) Reflection.WStoEntity(parameters.getUser());
                userService.saveUser(user);
                response.setResult(CREATED);
            }
            else {
                logger.warn("Unauthorized action with this login");
                response.setResult(UNAUTHORIZED);
            }
        }

        return response;
    }

    @Override
    @WebMethod
    public LoanReturnResponse loanReturn(LoanReturn parameters) {

        LoanReturnResponse response = objectFactory.createLoanReturnResponse();

        Map<String, Object> tokenData = userService.checkTokenData(parameters.getToken());
        if(tokenData.get(TOKEN_ACTIVE) == Boolean.FALSE){
            response.setResult(TOKEN_EXPIRED_INVALID);
            return response;
        }

        UserEntity user = (UserEntity) tokenData.get(USER_FROM_TOKEN);
        if(user.getLevel().getId() >= MANAGER){
            response.setResult(UNAUTHORIZED);
            return response;
        }

        String isbn = parameters.getBookBorrowed().getBook().getIsbn();
        Optional<BookEntity> optBookEntity = bookService.findBookByIsbn(isbn);
        if(!optBookEntity.isPresent()){
            response.setResult(BAD_REQUEST);
            return response;
        }

        BookEntity book = optBookEntity.get();
        BorrowedBook borrowedBook = bookService.findBorrowedBookByUserAndBook(user, book);

        if(!borrowedBook.getReturned()) {
            borrowedBook.setReturned(true);
            bookService.saveBorrowedBook(borrowedBook);
            response.setResult(OK);
        }
        else response.setResult(CONFLICT);

        return response;
    }

    @Override
    public int setReservationNotified(String token, int bookid, int userid, java.util.Date reserveDate) {

        int response;

        Map<String, Object> tokenData = userService.checkTokenData(token);
        if(tokenData.get(TOKEN_ACTIVE) == Boolean.FALSE){
            response = TOKEN_EXPIRED_INVALID;
            return response;
        }

        Optional<BookEntity> optBookEntity = bookService.findBookById(bookid);
        if(!optBookEntity.isPresent()){
            response = BAD_REQUEST;
            return response;
        }

        /**
         * Warning userID is not the same as the one in the token
         */
        UserEntity user = (UserEntity) tokenData.get(USER_FROM_TOKEN);
        BookEntity book = optBookEntity.get();

        Optional<ReservedBook> optReservedBook = bookService
                .findReservedBookByUserAndBookAndPendingTrue(user, book);
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
            bookService.saveReservedBook(reservedBook);
        }

        response = OK;
        return response;
    }

    @Override
    @WebMethod
    public ExtendBorrowingResponse extendBorrowing(ExtendBorrowing parameters) {

        ExtendBorrowingResponse response = objectFactory.createExtendBorrowingResponse();

        Map<String, Object> tokenData = userService.checkTokenData(parameters.getToken());
        if(tokenData.get(TOKEN_ACTIVE) == Boolean.FALSE){
            response.setResult(TOKEN_EXPIRED_INVALID);
            return response;
        }

        String isbn = parameters.getBookBorrowed().getBook().getIsbn();
        Optional<BookEntity> optBookEntity = bookService.findBookByIsbn(isbn);

        if(!optBookEntity.isPresent()){
            response.setResult(BAD_REQUEST);
            return response;
        }

        BookEntity book = optBookEntity.get();
        UserEntity user = (UserEntity) tokenData.get(USER_FROM_TOKEN);
        BorrowedBook borrowedBook = bookService.findBorrowedBookByUserAndBook(user, book);

        LocalDate localDate = LocalDate.fromDateFields(borrowedBook.getReturnDate());
        localDate = localDate.plusWeeks(nbWeeksToExtend);
        borrowedBook.setReturnDate(Converter.DateToSQLDate(localDate.toDate()));

        borrowedBook.setExtended(true);

        bookService.saveBorrowedBook(borrowedBook);

        if(book.getBookable()){

            Boolean dateChecked = DateUtils.isDateBefore(borrowedBook.getReturnDate(), book.getReturnDate());

            if(dateChecked != null && !dateChecked) {
                book.setReturnDate(borrowedBook.getReturnDate());
                bookService.saveBook(book);
            }
        }

        response.setResult(OK);
        return response;
    }

    @Override
    @WebMethod
    public ReservedBookListResponse reservedBookList(ReservedBookList parameters) {

        ReservedBookListResponse response = objectFactory.createReservedBookListResponse();

        Map<String, Object> tokenData = userService.checkTokenData(parameters.getToken());
        if(tokenData.get(TOKEN_ACTIVE) == Boolean.FALSE){
            response.setResult(TOKEN_EXPIRED_INVALID);
            return response;
        }

        UserEntity user = (UserEntity) tokenData.get(USER_FROM_TOKEN);
        List<ReservedBook> bookList = bookService.findAllReservedBooksByUser(user);

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
                List<ReservedBook> reservedBookList = bookService
                        .findAllReservedBooksByBookAndPendingTrueOrderByReserveDateAsc(reservedBook.getBook());
                bookReserved.setTotal(reservedBookList.size());

                for(int i = 0; i < reservedBookList.size(); i++){
                    if(reservedBookList.get(i).getUser().getId().equals(user.getId())){
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

        Map<String, Object> tokenData = userService.checkTokenData(parameter.getToken());
        if(tokenData.get(TOKEN_ACTIVE) == Boolean.FALSE){
            response.setResult(TOKEN_EXPIRED_INVALID);
            return response;
        }

        UserEntity user = (UserEntity) tokenData.get(USER_FROM_TOKEN);
        List<BorrowedBook> bookList = bookService.findAllBorrowedBooksByUserOrderByReturnedAsc(user);

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

        Map<String, Object> tokenData = userService.checkTokenData(parameter.getToken());
        if(tokenData.get(TOKEN_ACTIVE) == Boolean.FALSE) {
            response.setResult(TOKEN_EXPIRED_INVALID);
            return response;
        }

        UserEntity userEntity = (UserEntity) tokenData.get(USER_FROM_TOKEN);
        if(!userEntity.getLevel().getId().equals(ADMIN)){
            response.setResult(FORBIDDEN);
            return response;
        }

        List<BookEntity> bookList = bookService.findAllBooksByBookable(true);

        for(BookEntity book : bookList){

            if(book.getAvailable() == 0) continue;

            List<ReservedBook> reservedBookList = bookService
                    .findAllReservedBooksByBookAndPendingTrueOrderByReserveDateAsc(book);

            for(int i = 0; i < book.getAvailable(); i++){

                Optional<UserEntity> optUser = userService
                        .findUserById(reservedBookList.get(i).getUser().getId());

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

        Map<String, Object> tokenData = userService.checkTokenData(parameters.getToken());
        if(tokenData.get(TOKEN_ACTIVE) == Boolean.FALSE){
            response.setResult(TOKEN_EXPIRED_INVALID);
            return response;
        }

        UserEntity user = (UserEntity) tokenData.get(USER_FROM_TOKEN);
        if(user.getLevel().getId() != ADMIN){
            response.setResult(FORBIDDEN);
            return response;
        }

        Date dateBefore = new Date(System.currentTimeMillis());
        List<BorrowedBook> borrowedBookList = bookService
                .findAllBorrowedBooksNotReturnedAndReturnDateBefore(dateBefore);

        for(BorrowedBook borrowedBook : borrowedBookList){

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

        Map<String, Object> tokenData = userService.checkTokenData(token);
        if(tokenData.get(TOKEN_ACTIVE) == Boolean.FALSE){
            response = TOKEN_EXPIRED_INVALID;
            return response;
        }

        Optional<BookEntity> optBookEntity = bookService.findBookByIsbn(isbn);
        if(!optBookEntity.isPresent()){
            response = BAD_REQUEST;
            return response;
        }

        BookEntity book = optBookEntity.get();
        UserEntity user = (UserEntity) tokenData.get(USER_FROM_TOKEN);
        Optional<ReservedBook> optReservedBook = bookService
                .findReservedBookByUserAndBookAndPendingTrue(user, book);

        if(!optReservedBook.isPresent()) {
            response = NOT_MODIFIED;
            return response;
        }

        bookService.deleteReservedBook(optReservedBook.get());
        response = OK;
        return response;
    }

    @Override
    @WebMethod
    public SearchBookResponse searchBook(SearchBook parameters) {

        List<BookEntity> bookList = bookService
                .findAllBooksByTitleOrByAuthorOrByGenreContaining(parameters.getKeywords());

        BookList resultList = new BookList();

        for(BookEntity bookEntity : bookList)
            resultList.getBook().add((Book) Reflection.EntityToWS(bookEntity));

        SearchBookResponse searchBookResponse = new SearchBookResponse();
        searchBookResponse.setBookList(resultList);

        return searchBookResponse;
    }

    private void checkReservationStatus(BookEntity book){

        int nbCopies = book.getNbCopies();
        int maxReservedBook = nbCopies * multiplierReservation;

        List<ReservedBook> reservedBookList = bookService
                .findAllReservedBooksByBookAndPendingTrueOrderByReserveDateAsc(book);

        if(reservedBookList.size() >= maxReservedBook) {
            book.setBookable(false);
            book.setReturnDate(null);
        }
        else {
            book.setBookable(true);
            book.setReturnDate(getNextReturnDate(book));
        }

        bookService.saveBook(book);
    }

    private Date getNextReturnDate(BookEntity book){

        List<BorrowedBook> borrowedBookList = bookService.
                findAllBorrowedBooksByBookAndNotReturnedAndOrderByReturnDateAsc(book);

        // Nearest return date (order by asc)
        return borrowedBookList.get(0).getReturnDate();
    }
}
