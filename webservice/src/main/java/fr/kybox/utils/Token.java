package fr.kybox.utils;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class Token {

    public static String generate(String param1, String param2){

        String comb = param1 + param2;
        String e64 = Base64.getEncoder().encodeToString(comb.getBytes());

        return UUID.fromString(e64 + new Date().toString()).toString();
    }
}
