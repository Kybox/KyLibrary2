package fr.kybox.action;

import com.opensymphony.xwork2.ActionSupport;
import fr.kybox.gencode.*;
import fr.kybox.utils.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static fr.kybox.utils.ValueTypes.*;
/**
 * @author Kybox
 * @version 1.0
 */
public class SearchAction extends ActionSupport implements SessionAware {

    private String isbn;
    private Book book;
    private String keyword;
    private Boolean returned;
    private List<Book> bookList;
    private Map<String, Object> session;
    private List<Borrower> borrowerList;
    private String actionResult;

    private Logger log = LogManager.getLogger(this.getClass());

    @Override
    public String execute() {

        if(keyword!=null && !keyword.isEmpty()) {

            LibraryService service = ServiceFactory.getLibraryService();

            SearchBook searchBook = new SearchBook();
            searchBook.setKeywords(getKeyword());

            SearchBookResponse response = service.searchBook(searchBook);

            bookList = response.getBookList().getBook();
        }

        return ActionSupport.SUCCESS;
    }

    public String searchBookByIsbn(){

        GetBook getBook = new GetBook();
        getBook.setIsbn(getIsbn());

        LibraryService service = ServiceFactory.getLibraryService();
        GetBookResponse response = service.getBook(getBook);

        if(response.getResult() == HTTP_CODE_TOKEN_EXPIRED_INVALID) {
            session.clear();
            return ActionSupport.LOGIN;
        }

        if(response.getResult() != HTTP_CODE_OK){
            return ActionSupport.ERROR;
        }

        setBook(response.getBook());
        return ActionSupport.SUCCESS;
    }

    public String searchBorrowersByIsbn(){

        SearchBorrowersByIsbn request = new SearchBorrowersByIsbn();
        request.setIsbn(getIsbn());
        request.setToken((String) session.get(TOKEN));
        request.setReturned(getReturned());

        LibraryService service = ServiceFactory.getLibraryService();
        SearchBorrowersByIsbnResponse response = service.searchBorrowersByIsbn(request);

        HttpServletResponse servletResponse = ServletActionContext.getResponse();
        servletResponse.setStatus(response.getResult());

        if(response.getResult() == HTTP_CODE_TOKEN_EXPIRED_INVALID){
            session.clear();
            actionResult = ActionSupport.ERROR;
            return actionResult;
        }

        if(response.getResult() != HTTP_CODE_OK){
            log.info("Error : " + response.getResult());
            actionResult = ActionSupport.ERROR;
            return actionResult;
        }

        setBorrowerList(response.getBorrower());
        actionResult = ActionSupport.SUCCESS;
        return actionResult;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
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

    public List<Borrower> getBorrowerList() {
        return borrowerList;
    }

    public void setBorrowerList(List<Borrower> borrowerList) {
        this.borrowerList = borrowerList;
    }

    public String getActionResult() {
        return actionResult;
    }

    public void setActionResult(String actionResult) {
        this.actionResult = actionResult;
    }

    public String getKeyword() { return keyword; }

    public void setKeyword(String keyword) { this.keyword = keyword; }

    public List<Book> getBookList() { return bookList; }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }
}
