package fr.kybox.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Kybox
 * @version 1.0
 */
public class Password {

    public static String hash(String password){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //return encoder.encode(password);
        return password;
    }
}
