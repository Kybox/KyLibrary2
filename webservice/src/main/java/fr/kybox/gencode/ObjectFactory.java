
package fr.kybox.gencode;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the fr.kybox.gencode package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: fr.kybox.gencode
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link BookList }
     * 
     */
    public BookList createBookList() {
        return new BookList();
    }

    /**
     * Create an instance of {@link Book }
     * 
     */
    public Book createBook() {
        return new Book();
    }

    /**
     * Create an instance of {@link UserList }
     * 
     */
    public UserList createUserList() {
        return new UserList();
    }

    /**
     * Create an instance of {@link LoginUser }
     * 
     */
    public LoginUser createLoginUser() {
        return new LoginUser();
    }

    /**
     * Create an instance of {@link LoginUserResponse }
     * 
     */
    public LoginUserResponse createLoginUserResponse() {
        return new LoginUserResponse();
    }

    /**
     * Create an instance of {@link SearchBook }
     * 
     */
    public SearchBook createSearchBook() {
        return new SearchBook();
    }

    /**
     * Create an instance of {@link SearchBookResponse }
     * 
     */
    public SearchBookResponse createSearchBookResponse() {
        return new SearchBookResponse();
    }

}
