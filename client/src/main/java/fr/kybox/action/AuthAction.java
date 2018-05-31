package fr.kybox.action;

import com.opensymphony.xwork2.ActionSupport;
import fr.kybox.gencode.*;
import org.apache.struts2.interceptor.SessionAware;

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

                LibraryWebService webService = new LibraryWebService();
                LibraryService service = webService.getLibraryServicePort();

                LoginUser loginUser = new LoginUser();
                loginUser.setLogin(login);
                loginUser.setPassword(password);

                LoginUserResponse loginUserResponse = service.loginUser(loginUser);
                User user = loginUserResponse.getUser();

                if(user != null){

                    System.out.println("USER :");
                    System.out.println("FirstName = " + user.getFirstName());
                    System.out.println("LastName = " + user.getLastName());
                    System.out.println("Birthday = " + user.getBirthday());
                    System.out.println("Address = " + user.getPostalAddress());

                    this.session.put("user", user);

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

        this.session.remove("user");

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
