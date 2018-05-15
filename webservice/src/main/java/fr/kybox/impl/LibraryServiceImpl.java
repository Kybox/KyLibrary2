package fr.kybox.impl;

import fr.kybox.gencode.*;

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
public class LibraryServiceImpl implements LibraryService {

    @Override
    @WebMethod
    public LoginUserResponse loginUser(LoginUser parameters) {

        User user = new User();
        user.setFirstName("Nicolas");
        user.setLastName("Hulot");

        LoginUserResponse loginUserResponse = new LoginUserResponse();
        loginUserResponse.setUser(user);

        return loginUserResponse;
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
