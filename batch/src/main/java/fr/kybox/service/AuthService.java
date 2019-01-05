package fr.kybox.service;

import java.util.Map;

public interface AuthService {

    Map<String, Object> login(String email, String password);
    boolean connection(String email, String password);
}
