package fr.kybox.service.utils;

import fr.kybox.gencode.CreateUser;
import fr.kybox.gencode.Login;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Kybox
 * @version 1.0
 */
public class CheckParams {

    private static final Logger logger = LogManager.getLogger(CheckParams.class);

    public static boolean login(Login params){

        if(params != null && params.getLogin() != null && params.getPassword() != null)
            return true;

        else return false;
    }

    public static boolean createUser(CreateUser params){

        if(params != null && params.getLogin() != null) {

            Login login = params.getLogin();

            if (login.getLogin() != null && login.getPassword() != null)
                return true;

            else return false;
        }
        else return false;
    }
}
