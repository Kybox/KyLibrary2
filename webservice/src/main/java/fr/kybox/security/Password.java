package fr.kybox.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Kybox
 * @version 1.0
 */
public class Password {

    public static Boolean match(String password, String dbPassword){

        //BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        //System.out.println(b.encode(password));
        return BCrypt.checkpw(password, dbPassword);
    }
}
