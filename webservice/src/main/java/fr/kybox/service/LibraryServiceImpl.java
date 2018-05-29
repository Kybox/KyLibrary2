package fr.kybox.service;

import fr.kybox.dao.LibraryDao;
import fr.kybox.entities.User;
import fr.kybox.gencode.LibraryService;
import fr.kybox.gencode.LoginUser;
import fr.kybox.gencode.LoginUserResponse;
import fr.kybox.gencode.UserBookListResponse;
import fr.kybox.gencode.UserBookList;
import fr.kybox.gencode.SearchBook;
import fr.kybox.gencode.SearchBookResponse;
import fr.kybox.gencode.BookList;
import fr.kybox.gencode.Book;

import fr.kybox.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.jws.WebMethod;
import javax.jws.WebService;
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

    @Autowired
    LibraryDao libraryDao;

    @Override
    @WebMethod
    public LoginUserResponse loginUser(LoginUser parameters) {

        User user = libraryDao.getUserByLogin(parameters.getLogin(), parameters.getPassword());

        LoginUserResponse loginUserResponse = new LoginUserResponse();
        loginUserResponse.getUser().setFirstName(user.getFirst_name());
        loginUserResponse.getUser().setLastName(user.getLast_name());
        loginUserResponse.getUser().setEmail(user.getEmail());
        loginUserResponse.getUser().setBirthday(Converter.SQLDateToXML(user.getBirthday()));
        loginUserResponse.getUser().setPostalAddress(user.getPostal_address());
        loginUserResponse.getUser().setTel(user.getTel());

        return loginUserResponse;
    }

    @Override
    @WebMethod
    public UserBookListResponse userBookList(UserBookList parameter) {
        return null;
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
