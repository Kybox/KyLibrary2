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

    // USER LEVEL
    private final int ADMIN = 1;
    private final int MANAGER = 2;

    @Autowired
    BorrowedBooksRepository borrowedBooksRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    ReservedBookRepository reservedBookRepository;

    @Value("${settings.nbWeeksToExtend}")
    private int nbWeeksToExtend;

    @Value("${settings.multiplierReservation}")
    private int multiplierReservation;

    @Autowired
    private Token token;

    @Autowired
    private ObjectFactory objectFactory;

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

                    token.generateNew(userEntity);
                    tokenRepository.save(token.getTokenStorage());
                    loginResponse.setToken(token.getTokenStorage().getToken());
                }

                else logger.warn("Password comparison : [NO]");
            }
            else logger.warn("UserEntity created : [NO]");
        }

        return loginResponse;
    }

    @Override
    public void logout(String token) {

        TokenStorage tokenStorage = tokenRepository.findByToken(token);

        if(tokenStorage != null) tokenRepository.delete(tokenStorage);

    }

    @Override
    public GetBookResponse getBook(GetBook parameter) {

        GetBookResponse response = objectFactory.createGetBookResponse();

        BookEntity bookEntity = bookRepository.findByIsbn(parameter.getIsbn());
        if(bookEntity != null){
            response.setBook((Book) Reflection.EntityToWS(bookEntity));
            response.setResult(OK);
        }
        else response.setResult(BAD_REQUEST);

        return response ;
    }

    @Override
    public int reserveBook(String token, String isbn) {

        int result = 0;

        if(isValidToken(token)){

            UserEntity userEntity = tokenRepository.findByToken(token).getUserEntity();
            BookEntity bookEntity = bookRepository.findByIsbn(isbn);

            List<ReservedBook> bookList = reservedBookRepository.findAllByUser(userEntity);

            for(ReservedBook reservation : bookList) {
                if (bookEntity.getIsbn().equals(reservation.getBook().getIsbn()) && reservation.isPending()) {
                    result = FORBIDDEN;
                    break;
                }
            }

            if(result != FORBIDDEN) {
                if (bookEntity != null && bookEntity.getBookable()) {

                    ReservedBook reservedBook = new ReservedBook();
                    reservedBook.setUser(userEntity);
                    reservedBook.setBook(bookEntity);
                    reservedBook.setReserveDate(LocalDateTime.now());
                    reservedBook.setPending(true);

                    reservedBookRepository.save(reservedBook);

                    checkReservationStatus(reservedBook);

                    result = OK;

                } else result = BAD_REQUEST;
            }
        }
        else result = UNAUTHORIZED;

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

        response.setResult(BAD_REQUEST);

        if(CheckParams.login(parameters.getLogin())){
            Login login = parameters.getLogin();
            UserEntity userEntity = userRepository.findByEmail(login.getLogin());

            if(userEntity != null && Password.match(login.getPassword(), userEntity.getPassword())){
                if(userEntity.getLevel().getId() == MANAGER) {
                    if(CheckParams.loanReturn(parameters)){
                        try {

                            UserEntity user =
                                    userRepository.findByEmail(parameters.getUser().getEmail());
                            BookEntity book =
                                    bookRepository.findByIsbn(parameters.getBookBorrowed().getBook().getIsbn());

                            BorrowedBook borrowedBook = borrowedBooksRepository.findByUserAndBook(user, book);

                            if(!borrowedBook.getReturned()) {
                                borrowedBook.setReturned(true);
                                borrowedBooksRepository.save(borrowedBook);
                                response.setResult(OK);
                            }
                            else response.setResult(CONFLICT);
                        }
                        catch (HibernateException e){
                            logger.warn(e.getMessage());
                            response.setResult(INTERNAL_SERVER_ERROR);
                        }
                    }
                }
                else response.setResult(UNAUTHORIZED);
            }
        }

        return response;
    }

    @Override
    public ExtendBorrowingResponse extendBorrowing(ExtendBorrowing parameters) {

        ExtendBorrowingResponse response = objectFactory.createExtendBorrowingResponse();

        if(isValidToken(parameters.getToken())){

            UserEntity userEntity = tokenRepository.findByToken(parameters.getToken()).getUserEntity();
            BookEntity bookEntity = bookRepository.findByIsbn(parameters.getBookBorrowed().getBook().getIsbn());
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

        }
        else response.setResult(UNAUTHORIZED);

        return response;
    }

    @Override
    public ReservedBookListResponse reservedBookList(ReservedBookList parameters) {

        ReservedBookListResponse response = objectFactory.createReservedBookListResponse();

        if(isValidToken(parameters.getToken())){
            UserEntity userEntity = tokenRepository.findByToken(parameters.getToken()).getUserEntity();
            if(userEntity != null){
                List<ReservedBook> bookList = reservedBookRepository.findAllByUser(userEntity);
                for(ReservedBook reservedBook : bookList){
                    BookReserved bookReserved = new BookReserved();
                    Book book = (Book) Reflection.EntityToWS(reservedBook.getBook());
                    bookReserved.setBook(book);
                    Instant instant = reservedBook.getReserveDate().atZone(ZoneId.systemDefault()).toInstant();
                    bookReserved.setReserveDate(Date.from(instant));
                    bookReserved.setPending(reservedBook.isPending());
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
            }
            else response.setResult(INTERNAL_SERVER_ERROR);
        }
        else response.setResult(UNAUTHORIZED);

        return response;
    }

    @Override
    @WebMethod
    public UserBookListResponse userBookList(UserBookList parameter) {

        UserBookListResponse response = objectFactory.createUserBookListResponse();

        if(isValidToken(parameter.getToken())){

            UserEntity userEntity = tokenRepository.findByToken(parameter.getToken()).getUserEntity();

            if (userEntity != null) {

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

            } else response.setResult(INTERNAL_SERVER_ERROR);
        }
        else response.setResult(UNAUTHORIZED);

        return response;
    }

    @Override
    public UnreturnedBookListResponse unreturnedBookList(UnreturnedBookList parameters) {

        UnreturnedBookListResponse response = objectFactory.createUnreturnedBookListResponse();

        response.setResult(BAD_REQUEST);

        if(isValidToken(parameters.getToken())){

            UserEntity userEntity = tokenRepository.findByToken(parameters.getToken()).getUserEntity();

            if(userEntity != null && userEntity.getLevel().getId() == ADMIN) {

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

            }
            else response.setResult(UNAUTHORIZED);
        }

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

    private boolean isValidToken(String token){

        boolean result = false;

        if(token != null && !token.isEmpty()){

            TokenStorage tokenStorage = tokenRepository.findByToken(token);

            if(tokenStorage != null){

                if(tokenStorage.getExpireDate().isAfter(LocalDateTime.now())) result = true;
                else tokenRepository.delete(tokenStorage);
            }
        }

        return result;
    }

    private void checkReservationStatus(ReservedBook reservedBook){

        BookEntity bookEntity = reservedBook.getBook();

        int nbCopies = bookEntity.getNbCopies();
        int maxReservedBook = nbCopies * multiplierReservation;

        List<ReservedBook> reservedBookList = reservedBookRepository.findAllByBook(bookEntity);

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
