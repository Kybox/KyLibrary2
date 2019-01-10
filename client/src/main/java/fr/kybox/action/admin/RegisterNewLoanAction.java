package fr.kybox.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import fr.kybox.gencode.*;
import fr.kybox.security.Token;
import fr.kybox.utils.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import java.util.List;
import java.util.Map;

import static fr.kybox.utils.ValueTypes.*;

public class RegisterNewLoanAction extends ActionSupport implements SessionAware {

    private Logger log = LogManager.getLogger(this.getClass());

    private String isbn;
    private Book book;
    private String errorMsg;
    private String email;
    private String firstName;
    private String lastName;
    private List<User> userList;
    private Map<String, Object> session;

    @Override
    public String execute() {

        return ActionSupport.SUCCESS;
    }

    public String searchUserByName() {

        SearchUser searchUser = new SearchUser();
        searchUser.setKeywords(getLastName() + "&" + getFirstName());
        searchUser.setToken(Token.getToken());

        LibraryService service = ServiceFactory.getLibraryService();
        SearchUserResponse response = service.searchUser(searchUser);

        if(response.getResult() == HTTP_CODE_TOKEN_EXPIRED_INVALID){
            session.clear();
            return ActionSupport.LOGIN;
        }

        if(response.getResult() != HTTP_CODE_OK){
            errorMsg = ERROR_USER_NOT_FOUND + " \nCode " + response.getResult();
            addActionError(errorMsg);
            return ActionSupport.ERROR;
        }

        userList = response.getUser();
        return ActionSupport.SUCCESS;
    }

    public String registerNewLoan(){

        if(getIsbn() == null || getIsbn().isEmpty()) {
            log.info("Error : isbn is null or empty");
            return ActionSupport.ERROR;
        }

        if(getEmail() == null || getEmail().isEmpty()) {
            log.info("Error : email is null or empty");
            return ActionSupport.ERROR;
        }

        Book book = new Book();
        book.setIsbn(getIsbn());

        User user = new User();
        user.setLastName(getLastName());
        user.setEmail(getEmail());

        RegisterNewLoan request = new RegisterNewLoan();
        request.setBook(book);
        request.setUser(user);
        request.setToken(Token.getToken());

        LibraryService service = ServiceFactory.getLibraryService();
        RegisterNewLoanResponse response = service.registerNewLoan(request);

        if(response.getResult() == HTTP_CODE_OK) return ActionSupport.SUCCESS;
        else return ActionSupport.ERROR;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}
