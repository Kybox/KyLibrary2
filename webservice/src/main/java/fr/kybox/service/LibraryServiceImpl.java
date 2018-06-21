package fr.kybox.service;

import fr.kybox.batch.email.Email;
import fr.kybox.dao.AuthorRepository;
import fr.kybox.dao.BookRepository;
import fr.kybox.dao.BorrowedBooksRepository;
import fr.kybox.dao.UserRepository;
import fr.kybox.entities.*;
import fr.kybox.entities.UserEntity;
import fr.kybox.gencode.*;

import fr.kybox.security.Password;
import fr.kybox.utils.Converter;
import fr.kybox.utils.Reflection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.*;

/**
 * @author Kybox
 * @version 1.0
 */
@WebService(
        name = "LibraryWebService",
        targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73",
        portName = "LibraryService",
        wsdlLocation = "https://raw.githubusercontent.com/Kybox/KyLibrary/master/webservice/src/main/resources/wsdl/LibraryService.wsdl")
public class LibraryServiceImpl extends SpringBeanAutowiringSupport implements LibraryService {

    private final static Logger logger = LogManager.getLogger(LibraryServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    BorrowedBooksRepository borrowedBooksRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    private UserEntity userEntity;

    @Override
    @WebMethod
    public LoginUserResponse loginUser(LoginUser parameters) {

        LoginUserResponse loginUserResponse = new LoginUserResponse();

        if(parameters != null){

            logger.debug("Parameters defined : [OK]");

            if(parameters.getLogin()!= null && parameters.getPassword() != null) {

                logger.debug("EmailEntity and password defined : [OK]");

                userEntity = userRepository.findByEmail(parameters.getLogin());

                if (userEntity != null) {

                    logger.debug("UserEntity created : [OK]");

                    if (Password.match(parameters.getPassword(), userEntity.getPassword())) {

                        logger.debug("Password comparison : [OK]");

                        loginUserResponse.setUser(new User());
                        loginUserResponse.getUser().setFirstName(userEntity.getFirst_name());
                        loginUserResponse.getUser().setLastName(userEntity.getLast_name());
                        loginUserResponse.getUser().setEmail(userEntity.getEmail());
                        loginUserResponse.getUser().setBirthday(userEntity.getBirthday());
                        loginUserResponse.getUser().setPostalAddress(userEntity.getPostal_address());
                        loginUserResponse.getUser().setTel(userEntity.getTel());
                        loginUserResponse.getUser().setLevel(userEntity.getLevel().getId());
                    }
                    else logger.warn("Password comparison : [NO]");
                }
                else logger.warn("UserEntity created : [NO]");
            }
            else logger.warn("EmailEntity and password defined : [NO]");
        }
        else logger.warn("Parameters defined : [NO]");

        return loginUserResponse;
    }

    @Override
    public CreateUserResponse createUser(CreateUser parameters) {

        CreateUserResponse response = new CreateUserResponse();
        response.setResult(false);

        if(parameters != null){

            if(parameters.getLoginUser() != null){

                LoginUser loginUser = parameters.getLoginUser();

                if(loginUser.getLogin() != null && loginUser.getPassword() != null ){

                    userEntity = userRepository.findByEmail(loginUser.getLogin());

                    if(userEntity != null){

                        if(Password.match(loginUser.getPassword(), userEntity.getPassword())){

                            if(userEntity.getLevel().getId() >= 2 ) {

                                userEntity = Reflection.WSToUserEntity(parameters.getUser());
                                userRepository.save(userEntity);
                                response.setResult(true);
                            }
                            else {
                                logger.warn("Unauthorized action with this login");
                                response.setInfo("Unauthorized action with this login");
                            }
                        }
                        else {
                            logger.warn("Wrong password");
                            response.setInfo("Wrong password");
                        }
                    }
                    else {
                        logger.warn("Login not found");
                        response.setInfo("Login not found");
                    }
                }
                else {
                    logger.warn("Null login or/and password");
                    response.setInfo("Null login or/and password");
                }
            }
            else{
                logger.warn("Null loginUser");
                response.setInfo("Null loginUser");
            }
        }
        else {
            response.setInfo("Null parameters");
            logger.warn("Null parameters");
        }
        return response;
    }

    @Override
    public LoanReturnResponse loanReturn(LoanReturn parameters) {
        return null;
    }

    @Override
    @WebMethod
    public BookBorrowed extendBorrowing(BookBorrowed parameter) {

        BookEntity bookEntity = bookRepository.findByIsbn(parameter.getBook().getIsbn());
        BorrowedBook borrowedBook = borrowedBooksRepository.findByUserAndBookOrderByReturnDateDesc(userEntity, bookEntity);
        borrowedBook.setExtended(true);

        LocalDate localDate = LocalDate.fromDateFields(borrowedBook.getReturnDate());
        localDate = localDate.plusWeeks(4);
        borrowedBook.setReturnDate(Converter.DateToSQLDate(localDate.toDate()));

        borrowedBooksRepository.save(borrowedBook);

        parameter.setReturndate(borrowedBook.getReturnDate());
        parameter.setExtended(true);

        return parameter;
    }

    @Override
    @WebMethod
    public UserBookListResponse userBookList(UserBookList parameter) {

        UserBookListResponse response = new UserBookListResponse();

        if(parameter != null){

            if(parameter.getUser() != null){

                if(userEntity != null){

                    List<BorrowedBook> bookList = borrowedBooksRepository.findAllByUserOrderByReturnedDesc(userEntity);

                    for(BorrowedBook borrowedBook : bookList){

                        BookBorrowed bookBorrowed = new BookBorrowed();
                        bookBorrowed.setBook(Reflection.BookEntityToWS(borrowedBook.getBook()));
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
    @WebMethod
    public SearchBookResponse searchBook(SearchBook parameters) {

        Email email = new Email();
        email.send();

        Iterable<BookEntity> bookList =
                bookRepository.
                        findAllByTitleContainingOrAuthor_NameContainingOrGenre_NameContainingAllIgnoreCase
                                (parameters.getKeywords(), parameters.getKeywords(), parameters.getKeywords());

        BookList resultList = new BookList();

        for(BookEntity bookEntity : bookList)
            resultList.getBook().add(Reflection.BookEntityToWS(bookEntity));

        SearchBookResponse searchBookResponse = new SearchBookResponse();
        searchBookResponse.setBookList(resultList);

        return searchBookResponse;
    }
}
