package fr.kybox.action;

import com.opensymphony.xwork2.ActionSupport;
import fr.kybox.gencode.*;
import fr.kybox.security.Token;
import fr.kybox.utils.ServiceFactory;
import org.apache.struts2.interceptor.SessionAware;

import java.math.BigInteger;
import java.util.Map;

/**
 * @author Kybox
 * @version 1.0
 */
public class AuthAction extends ActionSupport implements SessionAware {

    private String login;
    private String password;
    private Map<String, Object> session;

    public String login(){

        String result = ActionSupport.INPUT;

        if(login != null && password != null){

            if(!login.isEmpty() && !password.isEmpty()){

                LibraryService service = ServiceFactory.getLibraryService();

                Login login = new Login();
                login.setLogin(this.login);
                login.setPassword(password);

                LoginResponse loginResponse = service.login(login);
                User user = loginResponse.getUser();

                if(user != null){

                    if(user.getLevel() == 3) this.session.put("user", user);
                    else if(user.getLevel() == 2) this.session.put("manager", user);
                    else if(user.getLevel() == 1) this.session.put("admin", user);

                    Token.setToken(loginResponse.getToken());

                    result = ActionSupport.SUCCESS;

                }
                else{
                    this.addActionError("Identifiants d'authentification incorrects !");
                    result = ActionSupport.ERROR;
                }
            }
            else {
                this.addActionError("Aucun des champs ne doit être vide !");
                result = ActionSupport.ERROR;
            }
        }

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
