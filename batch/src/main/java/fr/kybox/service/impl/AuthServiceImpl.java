package fr.kybox.service.impl;

import fr.kybox.gencode.LibraryService;
import fr.kybox.gencode.Login;
import fr.kybox.gencode.LoginResponse;
import fr.kybox.gencode.User;
import fr.kybox.service.AuthService;
import fr.kybox.utils.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static fr.kybox.utils.ValueTypes.*;

@Service
public class AuthServiceImpl implements AuthService {

    private final Logger log = LogManager.getLogger(this.getClass());

    @Override
    public Map<String, Object> login(String email, String password) {

        Map<String, Object> authResult = new HashMap<>();

        Login login = new Login();
        login.setLogin(email);
        login.setPassword(password);

        LibraryService service = ServiceFactory.getLibraryService();

        LoginResponse response = service.login(login);

        if(response.getResult() != HTTP_CODE_OK) {
            authResult.put(AUTHENTICATION, UNAUTHORIZED);
            return authResult;
        }

        User user = response.getUser();

        if(user == null || user.getLevel() != ADMIN_LEVEL) {
            authResult.put(AUTHENTICATION, UNAUTHORIZED);
            return authResult;
        }

        authResult.put(AUTHENTICATION, AUTHORIZED);
        authResult.put(TOKEN, response.getToken());
        return authResult;
    }

    @Override
    public boolean connection(String email, String password) {

        Map<String, Object> authResult = login(email, password);

        return authResult.get(AUTHENTICATION).equals(AUTHORIZED);
    }
}
