package fr.kybox.action;

import com.opensymphony.xwork2.ActionSupport;
import fr.kybox.gencode.*;
import fr.kybox.utils.ServiceFactory;
import org.apache.struts2.interceptor.SessionAware;

import java.util.List;
import java.util.Map;

/**
 * @author Kybox
 * @version 1.0
 */
public class UserAction extends ActionSupport implements SessionAware {

    private String tab;
    private Map<String, Object> session;

    private List<BookBorrowed> getUserBookList(){

        LibraryService service = ServiceFactory.getLibraryService();

        UserBookList userBookList = new UserBookList();
        userBookList.setUser(getUser());

        UserBookListResponse userBookListResponse = service.userBookList(userBookList);
        return userBookListResponse.getBookBorrowed();
    }

    public User getUser() {
        return (User) session.get("user");
    }

    public String getTab() { return tab; }
    public void setTab(String tab) { this.tab = tab; }

    public List<BookBorrowed> getBorrowedBooks() {
        return getUserBookList();
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
