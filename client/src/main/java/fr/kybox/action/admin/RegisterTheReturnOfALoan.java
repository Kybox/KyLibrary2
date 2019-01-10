package fr.kybox.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import fr.kybox.gencode.LibraryService;
import fr.kybox.utils.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

import static fr.kybox.utils.ValueTypes.*;

public class RegisterTheReturnOfALoan extends ActionSupport implements SessionAware {

    private Logger log = LogManager.getLogger(this.getClass());

    private String isbn;
    private String email;
    private int response;
    private String actionReturned;
    private Map<String, Object> session;

    public String registerAReturn() {

        String token = (String) session.get(TOKEN);

        LibraryService service = ServiceFactory.getLibraryService();
        response = service.loanReturn(token, getIsbn(), getEmail());

        switch (response){
            case HTTP_CODE_OK:
                actionReturned = ActionSupport.SUCCESS;
                break;
            case HTTP_CODE_NOT_FOUND:
                actionReturned = ERROR_MSG_NOT_FOUND;
                break;
            case HTTP_CODE_BAD_REQUEST:
                actionReturned = ERROR_MSG_BAD_REQUEST;
                break;
            case HTTP_CODE_TOKEN_EXPIRED_INVALID:
                actionReturned = ERROR_MSG_TOKEN_EXPIRED;
                session.clear();
                break;
            case HTTP_CODE_FORBIDDEN:
                actionReturned = ERROR_MSG_FORBIDDEN;
                break;
        }

        return actionReturned;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getResponse() {
        return response;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        session = map;
    }
}
