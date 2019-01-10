package fr.kybox.action;

import com.opensymphony.xwork2.ActionSupport;
import fr.kybox.gencode.*;
import fr.kybox.security.Token;
import fr.kybox.utils.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

import static fr.kybox.utils.ValueTypes.*;

/**
 * @author Kybox
 * @version 1.0
 */
public class AuthAction extends ActionSupport implements SessionAware {

    private Logger log = LogManager.getLogger(this.getClass());

    private String login;
    private String password;
    private Map<String, Object> session;

    public String login(){

        String result = ActionSupport.INPUT;

        if(login == null || password == null)
            return result;

        if(login.isEmpty() || password.isEmpty()){
            this.addActionError(ERROR_LOGIN_EMPTY_FIELD);
            result = ActionSupport.ERROR;
            return result;
        }

        LibraryService service = ServiceFactory.getLibraryService();

        Login login = new Login();
        login.setLogin(this.login);
        login.setPassword(password);

        LoginResponse loginResponse = service.login(login);
        User user = loginResponse.getUser();

        if(user == null){
            this.addActionError(ERROR_LOGIN_BAD_CREDIENTIALS);
            result = ActionSupport.ERROR;
            return result;
        }

        switch (user.getLevel()){
            case 1:
                this.session.put(LEVEL_ADMIN, user);
                log.info(LEVEL_ADMIN + " user logged");
                break;
            case 2:
                this.session.put(LEVEL_MANAGER, user);
                log.info(LEVEL_MANAGER + " user logged");
                break;
            case 3:
                this.session.put(LEVEL_CLIENT, user);
                log.info(LEVEL_CLIENT + " user logged");
                break;
        }

        Token.setToken(loginResponse.getToken());
        session.put(TOKEN, loginResponse.getToken());

        if(user.getLevel() < 3) result = LEVEL_ADMIN;
        else result = ActionSupport.SUCCESS;

        return result;

    }

    public String logout(){

        LibraryService service = ServiceFactory.getLibraryService();
        service.logout(Token.getToken());

        this.session.clear();

        return ActionSupport.SUCCESS;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
