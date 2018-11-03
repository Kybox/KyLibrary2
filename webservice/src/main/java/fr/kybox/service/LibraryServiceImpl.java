package fr.kybox.service;

import fr.kybox.dao.AuthorRepository;
import fr.kybox.dao.BookRepository;
import fr.kybox.dao.BorrowedBooksRepository;
import fr.kybox.dao.UserRepository;
import fr.kybox.entities.*;
import fr.kybox.entities.UserEntity;
import fr.kybox.gencode.*;

import fr.kybox.security.Password;
import fr.kybox.service.utils.CheckParams;
import fr.kybox.utils.Converter;
import fr.kybox.utils.Reflection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.sql.Date;
import java.util.*;

/**
 * @author Kybox
 * @version 1.0
 */
@WebService(
        name = "LibraryWebService",
        targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73",
        portName = "LibraryService",
        wsdlLocation = "https://raw.githubusercontent.com/Kybox/KyLibrary2/master/webservice/src/main/resources/wsdl/LibraryService.wsdl")
public class LibraryServiceImpl extends SpringBeanAutowiringSupport implements LibraryService {

    private final static Logger logger = LogManager.getLogger(LibraryServiceImpl.class);

    // ERROR CODE
    private final int INTERNAL_SERVER_ERROR = 500;
    private final int UNAUTHORIZED = 401;
    private final int BAD_REQUEST = 400;
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

    @Value("${settings.nbWeeksToExtend}")
    private int nbWeeksToExtend;

    private UserEntity userEntity;

    @Autowired
    private ObjectFactory objectFactory;

    @Override
    @WebMethod
    public LoginResponse login(Login parameters) {

        LoginResponse loginResponse = objectFactory.createLoginResponse();

        if(CheckParams.login(parameters)){

            userEntity = userRepository.findByEmail(parameters.getLogin());

            if (userEntity != null) {

                if (Password.match(parameters.getPassword(), userEntity.getPassword()))
                    loginResponse.setUser((User) Reflection.EntityToWS(userEntity));

                else logger.warn("Password comparison : [NO]");
            }
            else logger.warn("UserEntity created : [NO]");
        }

        return loginResponse;
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
    @WebMethod
    public BookBorrowed extendBorrowing(BookBorrowed parameter) {

        BookEntity bookEntity = bookRepository.findByIsbn(parameter.getBook().getIsbn());
        BorrowedBook borrowedBook = borrowedBooksRepository.findByUserAndBook(userEntity, bookEntity);
        borrowedBook.setExtended(true);

        LocalDate localDate = LocalDate.fromDateFields(borrowedBook.getReturnDate());
        localDate = localDate.plusWeeks(nbWeeksToExtend);
        borrowedBook.setReturnDate(Converter.DateToSQLDate(localDate.toDate()));

        borrowedBooksRepository.save(borrowedBook);

        parameter.setReturndate(borrowedBook.getReturnDate());
        parameter.setExtended(true);

        return parameter;
    }

    @Override
    @WebMethod
    public UserBookListResponse userBookList(UserBookList parameter) {

        UserBookListResponse response = objectFactory.createUserBookListResponse();

        if(parameter != null){

            if(parameter.getUser() != null){

                if(userEntity != null){

                    List<BorrowedBook> bookList = borrowedBooksRepository.findAllByUserOrderByReturnedDesc(userEntity);

                    for(BorrowedBook borrowedBook : bookList){

                        BookBorrowed bookBorrowed = new BookBorrowed();
                        bookBorrowed.setBook((Book) Reflection.EntityToWS(borrowedBook.getBook()));
                        bookBorrowed.setExtended(borrowedBook.getExtended());
                        bookBorrowed.setReturndate(borrowedBook.getReturnDate());
                        bookBorrowed.setReturned(borrowedBook.getReturned());

                        response.getBookBorrowed().add(bookBorrowed);

                    }
                }
                else logger.error("No userEntity found from UserBookList parameter");
            }
            else logger.error("UserEntity object is null (from UserBookList parameter");
        }
        else logger.error("Parameter no defined");

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
}
