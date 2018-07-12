package fr.kybox.action;

import com.opensymphony.xwork2.ActionSupport;
import fr.kybox.gencode.*;
import fr.kybox.utils.ServiceFactory;
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

    public String extendBorrowing(){

        System.out.println("ISBN = " + getIsbn());

        boolean bookUpdated = false;

        List<BookBorrowed> bookList = getBorrowedBooks();

        for(BookBorrowed bookBorrowed : bookList){
            if(bookBorrowed.getBook().getIsbn().equals(getIsbn())){
                LibraryService service = ServiceFactory.getLibraryService();
                service.extendBorrowing(bookBorrowed);
                bookUpdated = true;
            }
        }

        if(bookUpdated) return ActionSupport.SUCCESS;
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

        LibraryService service = ServiceFactory.getLibraryService();

        UserBookList userBookList = new UserBookList();
        userBookList.setUser(getUser());

        UserBookListResponse userBookListResponse = service.userBookList(userBookList);
        return userBookListResponse.getBookBorrowed();
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
