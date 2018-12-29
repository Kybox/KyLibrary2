package fr.kybox.action;

import com.opensymphony.xwork2.ActionSupport;
import fr.kybox.gencode.*;
import fr.kybox.utils.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static fr.kybox.utils.ValueTypes.ERROR_BAD_ISBN;
import static fr.kybox.utils.ValueTypes.HTTP_CODE_OK;

public class AdminAction extends ActionSupport {

    private Logger log = LogManager.getLogger(this.getClass());

    private String isbn;
    private Book book;
    private String errorMsg;

    @Override
    public String execute() {



        return ActionSupport.SUCCESS;
    }

    public String newLoan() {

        return ActionSupport.SUCCESS;
    }

    public String searchBookByIsbn(){

        log.info("ISBN = " + isbn);
        GetBook getBook = new GetBook();
        getBook.setIsbn(getIsbn());

        LibraryService service = ServiceFactory.getLibraryService();
        GetBookResponse response = service.getBook(getBook);

        if(response.getResult() != HTTP_CODE_OK){
            errorMsg = ERROR_BAD_ISBN;
            return ActionSupport.ERROR;
        }

        setBook(response.getBook());
        return ActionSupport.SUCCESS;
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
}
