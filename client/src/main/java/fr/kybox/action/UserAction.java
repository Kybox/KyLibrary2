package fr.kybox.action;

import com.opensymphony.xwork2.ActionSupport;
import fr.kybox.gencode.*;
import fr.kybox.security.Token;
import fr.kybox.utils.ResultCode;
import fr.kybox.utils.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Kybox
 * @version 1.0
 */
public class UserAction extends ActionSupport implements SessionAware {

    private String tab;
    private String isbn;
    private Map<String, Object> session;
    private List<BookBorrowed> borrowedBooks;

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public String execute() {

        logger.info("Execute method call");

        String result = ActionSupport.SUCCESS;

        LibraryService service = ServiceFactory.getLibraryService();

        UserBookList userBookList = new UserBookList();
        userBookList.setToken(Token.getToken());

        UserBookListResponse userBookListResponse = service.userBookList(userBookList);

        if(userBookListResponse.getResult() == ResultCode.UNAUTHORIZED) {
            this.session.clear();
            result = ActionSupport.LOGIN;
        }

        else if(userBookListResponse.getResult() != ResultCode.OK)
            result = ActionSupport.ERROR;

        return result;

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

    public String getTab() { return tab; }
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

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
