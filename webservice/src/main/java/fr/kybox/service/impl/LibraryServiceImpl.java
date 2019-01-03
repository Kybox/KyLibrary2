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
            logger.info("Empty credentials");
            response.setResult(BAD_REQUEST);
            return response;
        }

        Optional<UserEntity> optUser = userService.findUserByEmail(login);
        if(!optUser.isPresent()){
            logger.info("Bad credentials");
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
        else {
            logger.info("Bad password");
            response.setResult(BAD_REQUEST);
        }

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
    public SearchBorrowersByIsbnResponse searchBorrowersByIsbn(SearchBorrowersByIsbn parameters) {

        SearchBorrowersByIsbnResponse response = objectFactory.createSearchBorrowersByIsbnResponse();

        String token = parameters.getToken();
        Map<String, Object> tokenData = userService.checkTokenData(token);
        if(tokenData.get(TOKEN_ACTIVE) == Boolean.FALSE){
            response.setResult(TOKEN_EXPIRED_INVALID);
            return response;
        }

        UserEntity user = (UserEntity) tokenData.get(USER_FROM_TOKEN);
        if(user.getLevel().getId() > MANAGER){
            response.setResult(FORBIDDEN);
            return response;
        }

        String isbn = parameters.getIsbn();
        Optional<BookEntity> optBook = bookService.findBookByIsbn(isbn);
        if(!optBook.isPresent()){
            response.setResult(BAD_REQUEST);
            return response;
        }

        Boolean returned = parameters.isReturned();
        List<BorrowedBook> bookList = bookService.findAllBorrowedBooksByBook(optBook.get());

        for(BorrowedBook borrowedBook : bookList){

            if(returned != null && borrowedBook.getReturned() != returned) continue;

            Borrower borrower = objectFactory.createBorrower();
            Optional<UserEntity> optUser = userService.findUserById(borrowedBook.getUser().getId());
            if(!optUser.isPresent()) continue;
            borrower.setUser((User) Reflection.EntityToWS(optUser.get()));

            BookBorrowed bookBorrowed = new BookBorrowed();
            bookBorrowed.setBook((Book) Reflection.EntityToWS(borrowedBook.getBook()));
            bookBorrowed.setReturnDate(borrowedBook.getReturnDate());
            bookBorrowed.setExtended(borrowedBook.getExtended());
            bookBorrowed.setReturned(borrowedBook.getReturned());
            bookBorrowed.setBorrowingDate(borrowedBook.getBorrowingDate());

            borrower.setBookBorrowed(bookBorrowed);
            response.getBorrower().add(borrower);
        }

        response.setResult(OK);
        return response;
    }

    @Override
    @WebMethod
    public GetBookResponse getBook(GetBook parameter) {

        GetBookResponse response = objectFactory.createGetBookResponse();

        String isbn = parameter.getIsbn();
        if(isbn == null || isbn.isEmpty()){
            response.setResult(BAD_REQUEST);
            return response;
        }

        Optional<BookEntity> optBookEntity = bookService.findBookByIsbn(parameter.getIsbn());
        if(!optBookEntity.isPresent()){
            response.setResult(NOT_FOUND);
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
    public int loanReturn(String token, String isbn, String email) {

        int response;

        Map<String, Object> tokenData = userService.checkTokenData(token);
        if(tokenData.get(TOKEN_ACTIVE) == Boolean.FALSE){
            response = TOKEN_EXPIRED_INVALID;
            return response;
        }

        UserEntity userEntity = (UserEntity) tokenData.get(USER_FROM_TOKEN);
        if(userEntity.getLevel().getId() > MANAGER){
            response = FORBIDDEN;
            return response;
        }

        if(isbn == null || isbn.isEmpty() || email == null || email.isEmpty()){
            response = BAD_REQUEST;
            return response;
        }

        Optional<BookEntity> optBook = bookService.findBookByIsbn(isbn);
        if(!optBook.isPresent()){
            response = NOT_FOUND;
            return response;
        }

        Optional<BorrowedBook> optBorrowedBook = bookService.findBorrowedBookByIsbnAndUserEmail(isbn, email);
        if(!optBorrowedBook.isPresent()){
            response = NOT_FOUND;
            return response;
        }

        BorrowedBook borrowedBook = optBorrowedBook.get();
        borrowedBook.setReturned(true);
        borrowedBook.setReturnDate(new Date(Calendar.getInstance().getTime().getTime()));

        bookService.saveBorrowedBook(borrowedBook);

        response = OK;
        return response;
    }

    @Override
    public int setReservationNotified(String token, int bookid, int userid, LocalDateTime reserveDate) {

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
            bookReserved.setReserveDate(LocalDateTime.now());
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
    public RegisterNewLoanResponse registerNewLoan(RegisterNewLoan parameters) {

        RegisterNewLoanResponse response = objectFactory.createRegisterNewLoanResponse();

        String token = parameters.getToken();
        Map<String, Object> tokenData = userService.checkTokenData(token);
        if(tokenData.get(TOKEN_ACTIVE) == Boolean.FALSE){
            response.setResult(TOKEN_EXPIRED_INVALID);
            logger.info("Error : " + response.getResult());
            return response;
        }

        User paramUser = parameters.getUser();
        if(paramUser == null || paramUser.getEmail() == null || paramUser.getEmail().isEmpty()){
            response.setResult(BAD_REQUEST);
            logger.info("Error : " + response.getResult());
            return response;
        }

        Book paramBook = parameters.getBook();
        if(paramBook == null || paramBook.getIsbn() == null || paramBook.getIsbn().isEmpty()){
            response.setResult(BAD_REQUEST);
            logger.info("Error : " + response.getResult());
            return response;
        }

        Optional<UserEntity> optUser = userService.findUserByEmail(paramUser.getEmail());
        if(!optUser.isPresent()){
            response.setResult(BAD_REQUEST);
            logger.info("Error : " + response.getResult());
            return response;
        }

        Optional<BookEntity> optBook = bookService.findBookByIsbn(parameters.getBook().getIsbn());
        if(!optBook.isPresent()){
            response.setResult(BAD_REQUEST);
            logger.info("Error : " + response.getResult());
            return response;
        }

        BookEntity book = optBook.get();
        if(book.getAvailable() == ZERO){
            response.setResult(CONFLICT);
            logger.info("Error : " + response.getResult());
        }

        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setUser(optUser.get());
        borrowedBook.setBook(book);
        borrowedBook.setExtended(false);
        borrowedBook.setReturned(false);

        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusWeeks(nbWeeksToExtend);
        borrowedBook.setReturnDate(Converter.DateToSQLDate(localDate.toDate()));
        borrowedBook.setBorrowingDate(LocalDateTime.now());

        bookService.saveBorrowedBook(borrowedBook);

        book.setAvailable(book.getAvailable() - ONE);
        if(book.getAvailable() == ZERO) book.setBookable(true);
        bookService.saveBook(book);

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
    public SearchUserResponse searchUser(SearchUser parameters) {

        SearchUserResponse response = objectFactory.createSearchUserResponse();

        Map<String, Object> tokenData = userService.checkTokenData(parameters.getToken());
        if(tokenData.get(TOKEN_ACTIVE) == Boolean.FALSE){
            response.setResult(TOKEN_EXPIRED_INVALID);
            logger.warn(response.getResult());
            return response;
        }

        UserEntity user = (UserEntity) tokenData.get(USER_FROM_TOKEN);
        if(user.getLevel().getId() > MANAGER){
            response.setResult(FORBIDDEN);
            logger.warn(response.getResult());
            return response;
        }

        logger.info("KEYWORDS = " + parameters.getKeywords());
        String[] names = parameters.getKeywords().split("&");
        logger.info("NAME = " + Arrays.toString(names));
        logger.info("FIRSTNAME = " + names[0]);
        logger.info("LASTNAME = " + names[1]);
        List<UserEntity> userList = userService.findAllUserByLastNameAndFirstName(names[0], names[1]);
        if(userList.isEmpty()){
            response.setResult(NOT_FOUND);
            logger.warn(response.getResult());
            return response;
        }

        for(UserEntity userEntity : userList){
            response.getUser().add((User) Reflection.EntityToWS(userEntity));
        }
        response.setResult(OK);
        logger.info(response.getUser().size() + "user(s) found");
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
