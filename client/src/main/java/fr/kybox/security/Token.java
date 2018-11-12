package fr.kybox.security;

public class Token {

    public static String token;

    public static void setToken(String validToken){
        token = validToken;
    }

    public static String getToken() {
        return token;
    }
}
