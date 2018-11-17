package fr.kybox.action;

import com.opensymphony.xwork2.ActionSupport;
import fr.kybox.gencode.Book;
import fr.kybox.gencode.GetBook;
import fr.kybox.gencode.GetBookResponse;
import fr.kybox.gencode.LibraryService;
import fr.kybox.security.Token;
import fr.kybox.utils.ResultCode;
import fr.kybox.utils.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class ReserveAction extends ActionSupport implements SessionAware {

    private Logger logger = LogManager.getLogger(this.getClass());
    private Map<String, Object> session;
    private String confirmed;
    private String isbn;
    private int result;
    private Book book;

    @Override
    public void setSession(Map<String, Object> map) {
        session = session;
    }

    public String summary(){

        GetBook getBook = new GetBook();
        getBook.setIsbn(getIsbn());

        LibraryService service = ServiceFactory.getLibraryService();
        GetBookResponse response = service.getBook(getBook);
        result = response.getResult();
        setBook(response.getBook());

        if(response.getResult() == ResultCode.OK && getBook().isBookable())
            return ActionSupport.SUCCESS;
        else {
            if(response.getResult() == ResultCode.BAD_REQUEST)
                this.addActionError("Erreur : BAD_REQUEST - Le livre n'existe pas !");
            return ActionSupport.ERROR;
        }
    }

    public String bookReserved() {

        LibraryService service = ServiceFactory.getLibraryService();
        result = service.reserveBook(Token.getToken(), getIsbn());
        String actionReturn = null;

        if(result == ResultCode.OK)
            actionReturn = ActionSupport.SUCCESS;
        else{
            if(result == ResultCode.BAD_REQUEST)
                this.addActionError("Erreur : BAD_REQUEST - La requête est invalide !");

            actionReturn = ActionSupport.ERROR;
        }

        return actionReturn;
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

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }
}
