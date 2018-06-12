package fr.kybox.service;

import fr.kybox.dao.BookRepository;
import fr.kybox.dao.BorrowedBooksRepository;
import fr.kybox.dao.UserRepository;
import fr.kybox.entities.*;
import fr.kybox.entities.UserEntity;
import fr.kybox.gencode.*;

import fr.kybox.security.Password;
import fr.kybox.utils.Converter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.math.BigInteger;
import java.util.List;

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

    private UserEntity userEntity;

    @Override
    @WebMethod
    public LoginUserResponse loginUser(LoginUser parameters) {

        LoginUserResponse loginUserResponse = new LoginUserResponse();

        if(parameters != null){

            logger.debug("Parameters defined : [OK]");

            if(parameters.getLogin()!= null && parameters.getPassword() != null) {

                logger.debug("Email and password defined : [OK]");

                userEntity = userRepository.findByEmail(parameters.getLogin());

                if (userEntity != null) {

                    logger.debug("UserEntity created : [OK]");

                    if (Password.match(parameters.getPassword(), userEntity.getPassword())) {

                        logger.debug("Password comparison : [OK]");

                        loginUserResponse.setUser(new User());
                        loginUserResponse.getUser().setFirstName(userEntity.getFirst_name());
                        loginUserResponse.getUser().setLastName(userEntity.getLast_name());
                        loginUserResponse.getUser().setEmail(userEntity.getEmail());
                        loginUserResponse.getUser().setBirthday(Converter.SQLDateToXML(userEntity.getBirthday()));
                        loginUserResponse.getUser().setPostalAddress(userEntity.getPostal_address());
                        loginUserResponse.getUser().setTel(userEntity.getTel());
                    }
                    else logger.error("Password comparison : [NO]");
                }
                else logger.error("UserEntity created : [NO]");
            }
            else logger.error("Email and password defined : [NO]");
        }
        else logger.error("Parameters defined : [NO]");

        return loginUserResponse;
    }

    @Override
    @WebMethod
    public BookBorrowed extendBorrowing(BookBorrowed parameter) {

        BookEntity bookEntity = bookRepository.findByIsbn(parameter.getBook().getISBN());
        BorrowedBook borrowedBook = borrowedBooksRepository.findByUserAndBook(userEntity, bookEntity);
        borrowedBook.setExtended(true);

        LocalDate localDate = LocalDate.fromDateFields(borrowedBook.getReturnDate());
        localDate = localDate.plusWeeks(4);
        borrowedBook.setReturnDate(Converter.DateToSQLDate(localDate.toDate()));

        borrowedBooksRepository.save(borrowedBook);

        parameter.setReturndate(Converter.LocalDateToXML(localDate));
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

                    List<BorrowedBook> borrowedBookList = borrowedBooksRepository.findAllByUser(userEntity);

                    for(BorrowedBook borrowedBook : borrowedBookList){

                        Book book = new Book();
                        book.setISBN(borrowedBook.getBook().getIsbn());
                        book.setTitle(borrowedBook.getBook().getTitle());
                        book.setAuthor(borrowedBook.getBook().getAuthor().getName());
                        book.setPublisher(borrowedBook.getBook().getPublisher().getName());
                        book.setPublishDate(Converter.SQLDateToXML(borrowedBook.getBook().getPublisherdate()));
                        book.setSummary(borrowedBook.getBook().getSummary());
                        book.setGenre(borrowedBook.getBook().getGenre().getName());
                        book.setAvailable(BigInteger.valueOf(borrowedBook.getBook().getAvailable()));
                        book.setCover(borrowedBook.getBook().getCover());

                        BookBorrowed bookBorrowed = new BookBorrowed();
                        bookBorrowed.setBook(book);
                        bookBorrowed.setExtended(borrowedBook.getExtended());
                        bookBorrowed.setReturndate(Converter.SQLDateToXML(borrowedBook.getReturnDate()));

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


        SearchBookResponse searchBookResponse = new SearchBookResponse();
        BookList bookList = new BookList();
        List<Book> list = bookList.getBook();

        for(int i = 0; i < 4; i++){

            Book book = new Book();
            book.setTitle("Titre " + i);
            list.add(book);
        }

        searchBookResponse.setBookList(bookList);

        return searchBookResponse;
    }
}
