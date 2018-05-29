package fr.kybox.security;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @author Kybox
 * @version 1.0
 */
public class Password {

    public static Boolean match(String password, String dbPassword){

        return BCrypt.checkpw(password, dbPassword);
    }
}
