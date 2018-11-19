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
public class SearchAction extends ActionSupport implements SessionAware {

    private String keyword;
    private List<Book> bookList;
    private Map<String, Object> session;

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }

    public List<Book> getBookList() { return bookList; }

    @Override
    public String execute() throws Exception {

        if(keyword!=null && !keyword.isEmpty()) {

            LibraryService service = ServiceFactory.getLibraryService();

            SearchBook searchBook = new SearchBook();
            searchBook.setKeywords(getKeyword());

            SearchBookResponse response = service.searchBook(searchBook);

            bookList = response.getBookList().getBook();


        }

        return ActionSupport.SUCCESS;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
