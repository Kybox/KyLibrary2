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
    private HttpServletRequest request;
    private Map<String, Object> session;
    private List<BookBorrowed> borrowedBooks;
    private List<BookReserved> reservedBooks;

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public String execute() {

        logger.info("Execute method call");

        String result = ActionSupport.SUCCESS;


        /*

        logger.info("TAB = " + getTab());
        logger.info("REQUEST PARAM VALUES = " + request.getParameterMap().toString());
        Map<String, String[]> paramMap = request.getParameterMap();
        for(Map.Entry<String, String[]> entry : paramMap.entrySet()){
            logger.info("KEY = " + entry.getKey());
            logger.info("VALUE = " + Arrays.toString(entry.getValue()));
        }

        switch (getTab()){
            case "reservation":
                logger.info("SWITCH TAB = RESERVATION");
                ReservedBookList bookList = new ReservedBookList();
                bookList.setToken(Token.getToken());
                setReservedBooks(service.reservedBookList(bookList).getReservedBook());
                break;

                default:
                    UserBookList userBookList = new UserBookList();
                    userBookList.setToken(Token.getToken());

                    UserBookListResponse userBookListResponse = service.userBookList(userBookList);

                    if(userBookListResponse.getResult() == ResultCode.UNAUTHORIZED) {
                        this.session.clear();
                        result = ActionSupport.LOGIN;
                    }

                    else if(userBookListResponse.getResult() != ResultCode.OK)
                        result = ActionSupport.ERROR;

                    break;
        }

        */

        return result;

    }

    public String informations(){

        logger.info("TAB = " + getTab());
        logger.info("REQUEST PARAM VALUES = " + request.getParameterMap().toString());
        Map<String, String[]> paramMap = request.getParameterMap();
        for(Map.Entry<String, String[]> entry : paramMap.entrySet()){
            logger.info("KEY = " + entry.getKey());
            logger.info("VALUE = " + Arrays.toString(entry.getValue()));
        }

        return ActionSupport.SUCCESS;
    }

    public String reservations(){

        String actionReturned = null;
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

        if(borrowedBooks == null){
            LibraryService service = ServiceFactory.getLibraryService();

            UserBookList userBookList = new UserBookList();
            userBookList.setToken(Token.getToken());

            borrowedBooks = service.userBookList(userBookList).getBookBorrowed();
        }

        return borrowedBooks;
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
