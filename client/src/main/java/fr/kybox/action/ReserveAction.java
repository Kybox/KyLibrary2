package fr.kybox.action;

import com.opensymphony.xwork2.ActionSupport;
import fr.kybox.gencode.*;
import fr.kybox.security.Token;
import fr.kybox.utils.ResultCode;
import fr.kybox.utils.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

import static fr.kybox.utils.ValueTypes.LEVEL_CLIENT;
import static fr.kybox.utils.ValueTypes.TOKEN;

public class ReserveAction extends ActionSupport implements SessionAware {

    private Logger logger = LogManager.getLogger(this.getClass());
    private Map<String, Object> session;
    private String confirmed;
    private String isbn;
    private int result;
    private Book book;
    private boolean authorizedReservation;

    @Override
    public void setSession(Map<String, Object> map) {
        session = map;
    }

    public String summary(){

        GetBook getBook = new GetBook();
        getBook.setIsbn(getIsbn());

        LibraryService service = ServiceFactory.getLibraryService();
        GetBookResponse response = service.getBook(getBook);
        result = response.getResult();
        setBook(response.getBook());

        ReservedBookList auth = new ReservedBookList();
        auth.setToken((String) session.get(TOKEN));
        ReservedBookListResponse bookList = service.reservedBookList(auth);

        setAuthorizedReservation(true);

        for(BookReserved bookReserved : bookList.getBookReserved()) {
            if (bookReserved.getBook().getIsbn().equals(response.getBook().getIsbn()) && bookReserved.isPending()) {
                setAuthorizedReservation(false);
                break;
            }
        }

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
        result = service.reserveBook((String) session.get(TOKEN), getIsbn());
        String actionReturn = null;

        if(result == ResultCode.OK)
            actionReturn = ActionSupport.SUCCESS;
        else{
            if(result == ResultCode.BAD_REQUEST) {
                logger.warn("Bad request : "+ result);
                this.addActionError("Erreur : BAD_REQUEST - La requête est invalide !");
            }
            else if(result == ResultCode.FORBIDDEN) {
                logger.warn("Forbidden : "+ result);
                this.addActionError("Erreur : FORBIDDEN - Vous ne pouvez pas effectuer cette action !");
            }
            else {
                logger.warn("Error : "+ result);
                actionReturn = ActionSupport.ERROR;
            }
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

    public boolean isAuthorizedReservation() {
        return authorizedReservation;
    }

    public void setAuthorizedReservation(boolean authorizedReservation) {
        this.authorizedReservation = authorizedReservation;
    }
}
