
package fr.kybox.gencode;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


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

    private final static QName _User_QNAME = new QName("dd7b026a-d6a2-4089-adb2-596ab0598c73", "user");

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
     * Create an instance of {@link Book }
     * 
     */
    public Book createBook() {
        return new Book();
    }

    /**
     * Create an instance of {@link BookList }
     * 
     */
    public BookList createBookList() {
        return new BookList();
    }

    /**
     * Create an instance of {@link BookBorrowed }
     * 
     */
    public BookBorrowed createBookBorrowed() {
        return new BookBorrowed();
    }

    /**
     * Create an instance of {@link UserBookList }
     * 
     */
    public UserBookList createUserBookList() {
        return new UserBookList();
    }

    /**
     * Create an instance of {@link UserBookListResponse }
     * 
     */
    public UserBookListResponse createUserBookListResponse() {
        return new UserBookListResponse();
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

    /**
     * Create an instance of {@link CreateUser }
     * 
     */
    public CreateUser createCreateUser() {
        return new CreateUser();
    }

    /**
     * Create an instance of {@link CreateUserResponse }
     * 
     */
    public CreateUserResponse createCreateUserResponse() {
        return new CreateUserResponse();
    }

    /**
     * Create an instance of {@link LoanReturn }
     * 
     */
    public LoanReturn createLoanReturn() {
        return new LoanReturn();
    }

    /**
     * Create an instance of {@link LoanReturnResponse }
     * 
     */
    public LoanReturnResponse createLoanReturnResponse() {
        return new LoanReturnResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link User }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", name = "user")
    public JAXBElement<User> createUser(User value) {
        return new JAXBElement<User>(_User_QNAME, User.class, null, value);
    }

}
