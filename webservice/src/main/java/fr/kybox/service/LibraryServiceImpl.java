package fr.kybox.service;

import fr.kybox.dao.*;
import fr.kybox.entities.*;
import fr.kybox.entities.UserEntity;
import fr.kybox.gencode.*;

import fr.kybox.security.Password;
import fr.kybox.service.utils.CheckParams;
import fr.kybox.utils.Converter;
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
import java.time.LocalDateTime;
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
    private final int CONFLICT = 409;
    private final int CREATED = 201;
    private final int OK = 200;
    private final int FORBIDDEN = 403;

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

    @Value("${settings.nbWeeksToExtend}")
    private int nbWeeksToExtend;

    private UserEntity userEntity;

    @Autowired
    private Token token;

    @Autowired
    private ObjectFactory objectFactory;

    @Override
    @WebMethod
    public LoginResponse login(Login parameters) {

        LoginResponse loginResponse = objectFactory.createLoginResponse();

        if(CheckParams.login(parameters)){

            userEntity = userRepository.findByEmail(parameters.getLogin());

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
    @WebMethod
    public CreateUserResponse createUser(CreateUser parameters) {

        CreateUserResponse response = objectFactory.createCreateUserResponse();

        response.setResult(BAD_REQUEST);

        if(CheckParams.createUser(parameters)){

            Login login = parameters.getLogin();

            userEntity = userRepository.findByEmail(login.getLogin());

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
            userEntity = userRepository.findByEmail(login.getLogin());

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

            userEntity = tokenRepository.findByToken(parameters.getToken()).getUserEntity();
            BookEntity bookEntity = bookRepository.findByIsbn(parameters.getBookBorrowed().getBook().getIsbn());
            BorrowedBook borrowedBook = borrowedBooksRepository.findByUserAndBook(userEntity, bookEntity);

            Date now = new Date(Calendar.getInstance().getTime().getTime());
            if(borrowedBook.getReturnDate().before(now)) {

                LocalDate localDate = LocalDate.fromDateFields(borrowedBook.getReturnDate());
                localDate = localDate.plusWeeks(nbWeeksToExtend);
                borrowedBook.setReturnDate(Converter.DateToSQLDate(localDate.toDate()));

                borrowedBook.setExtended(true);

                borrowedBooksRepository.save(borrowedBook);

                response.setResult(OK);
            }
            else response.setResult(FORBIDDEN);

        }
        else response.setResult(UNAUTHORIZED);

        return response;
    }

    @Override
    public ReservedBookListResponse reservedBookList(ReservedBookList parameters) {


        return null;
    }

    @Override
    @WebMethod
    public UserBookListResponse userBookList(UserBookList parameter) {

        UserBookListResponse response = objectFactory.createUserBookListResponse();

        if(isValidToken(parameter.getToken())){

            userEntity = tokenRepository.findByToken(parameter.getToken()).getUserEntity();

            if (userEntity != null) {

                List<BorrowedBook> bookList = borrowedBooksRepository.findAllByUserOrderByReturnedDesc(userEntity);

                for (BorrowedBook borrowedBook : bookList) {

                    BookBorrowed bookBorrowed = new BookBorrowed();
                    bookBorrowed.setBook((Book) Reflection.EntityToWS(borrowedBook.getBook()));
                    bookBorrowed.setExtended(borrowedBook.getExtended());
                    bookBorrowed.setReturndate(borrowedBook.getReturnDate());
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

        if(CheckParams.unreturnedBookList(parameters)){

            Login login = parameters.getLogin();
            userEntity = userRepository.findByEmail(login.getLogin());

            if(userEntity != null && Password.match(login.getPassword(), userEntity.getPassword())){
                if(userEntity.getLevel().getId() == ADMIN) {

                    Iterable<BorrowedBook> itemList =
                            borrowedBooksRepository
                                    .findAllByReturnDateBeforeAndReturnedFalse(new Date(System.currentTimeMillis()));

                    for(BorrowedBook borrowedBook : itemList){

                        UnreturnedBook unreturnedBook = new UnreturnedBook();
                        unreturnedBook.setUser((User) Reflection.EntityToWS(borrowedBook.getUser()));

                        BookBorrowed bookBorrowed = objectFactory.createBookBorrowed();
                        bookBorrowed.setBook((Book) Reflection.EntityToWS(borrowedBook.getBook()));
                        bookBorrowed.setExtended(borrowedBook.getExtended());
                        bookBorrowed.setReturndate(borrowedBook.getReturnDate());
                        bookBorrowed.setReturned(borrowedBook.getReturned());

                        unreturnedBook.setBookBorrowed(bookBorrowed);

                        response.getUnreturnedBook().add(unreturnedBook);
                    }

                    response.setResult(OK);

                }
                else response.setResult(UNAUTHORIZED);
            }
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
}
