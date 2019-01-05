package fr.kybox.service.impl;

import fr.kybox.gencode.LibraryService;
import fr.kybox.gencode.Login;
import fr.kybox.gencode.LoginResponse;
import fr.kybox.gencode.User;
import fr.kybox.service.AuthService;
import fr.kybox.utils.ServiceFactory;
import org.springframework.stereotype.Service;

import static fr.kybox.utils.ValueTypes.*;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public boolean login(String email, String password) {

        Login login = new Login();
        login.setLogin(email);
        login.setPassword(password);

        LibraryService service = ServiceFactory.getLibraryService();

        LoginResponse response = service.login(login);

        if(response.getResult() != HTTP_CODE_OK)
            return UNAUTHORIZED;

        User user = response.getUser();
        if(user == null || user.getLevel() != ADMIN_LEVEL)
            return UNAUTHORIZED;

        return AUTHORIZED;
    }
}
