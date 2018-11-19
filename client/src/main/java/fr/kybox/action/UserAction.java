package fr.kybox.action;

import com.opensymphony.xwork2.ActionSupport;
import fr.kybox.gencode.*;
import fr.kybox.security.Token;
import fr.kybox.utils.ResultCode;
import fr.kybox.utils.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Kybox
 * @version 1.0
 */
public class UserAction extends ActionSupport implements SessionAware, ServletRequestAware {

    private String tab;
    private String isbn;
    private String actionReturned;
    private HttpServletRequest request;
    private Map<String, Object> session;
    private List<BookBorrowed> borrowedBooks;
    private List<BookReserved> reservedBooks;

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public String execute() {

        logger.info("Execute method call");

        String result = ActionSupport.SUCCESS;



        return result;

    }

    public String informations(){

        // Add some notifications ?

        return ActionSupport.SUCCESS;
    }

    public String reservations(){

        LibraryService service = ServiceFactory.getLibraryService();
        ReservedBookList bookList = new ReservedBookList();
        bookList.setToken(Token.getToken());
        ReservedBookListResponse response = service.reservedBookList(bookList);

        if(response.getResult() != ResultCode.OK){
            if(response.getResult() == ResultCode.INTERNAL_SERVER_ERROR){
                this.addActionError("Erreur : Le service a recontré une erreur...");
                actionReturned = ActionSupport.ERROR;
            }
            else if(response.getResult() == ResultCode.UNAUTHORIZED){
                session.clear();
                actionReturned = ActionSupport.LOGIN;
            }
        }
        else{
            setReservedBooks(response.getBookReserved());
            actionReturned = ActionSupport.SUCCESS;
        }
        return actionReturned;
    }

    public String borrowing(){

        LibraryService service = ServiceFactory.getLibraryService();
        UserBookList userBookList = new UserBookList();
        userBookList.setToken(Token.getToken());
        UserBookListResponse response = service.userBookList(userBookList);

        if(response.getResult() != ResultCode.OK){
            if(response.getResult() == ResultCode.INTERNAL_SERVER_ERROR){
                this.addActionError("Erreur : Le service a rencontré une erreur...");
                actionReturned = ActionSupport.ERROR;
            }
            else if(response.getResult() == ResultCode.UNAUTHORIZED){
                session.clear();
                actionReturned = ActionSupport.LOGIN;
            }
        }
        else{
            setBorrowedBooks(response.getBookBorrowed());
            actionReturned = ActionSupport.SUCCESS;
        }

        return actionReturned;
    }

    public String extendBorrowing(){

        List<BookBorrowed> bookList = getBorrowedBooks();
        LibraryService service = ServiceFactory.getLibraryService();
        ExtendBorrowing extendBorrowing = new ExtendBorrowing();
        extendBorrowing.setToken(Token.getToken());

        for(BookBorrowed bookBorrowed : bookList){
            if(bookBorrowed.getBook().getIsbn().equals(getIsbn())){
                extendBorrowing.setBookBorrowed(bookBorrowed);
                break;
            }
        }

        if(service.extendBorrowing(extendBorrowing).getResult() == ResultCode.OK)
            return ActionSupport.SUCCESS;

        else return ActionSupport.ERROR;
    }

    public String history(){

        if(borrowing().equals(ActionSupport.SUCCESS)){
            return reservations();
        }
        else return actionReturned;
    }

    public User getUser() {
        return (User) session.get("user");
    }

    public String getTab() {
        return request.getParameter("tab");
    }
    public void setTab(String tab) { this.tab = tab; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public List<BookBorrowed> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BookBorrowed> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public List<BookReserved> getReservedBooks() {
        return reservedBooks;
    }

    public void setReservedBooks(List<BookReserved> reservedBooks) {
        this.reservedBooks = reservedBooks;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.request = httpServletRequest;
    }
}
