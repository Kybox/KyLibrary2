package fr.kybox.utils;

import fr.kybox.entities.TokenStorage;
import fr.kybox.entities.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class Token {

    @Value("${settings.tokenExpire}")
    private Integer expInterval;

    private TokenStorage tokenStorage;

    public void generateNew(UserEntity entity){

        tokenStorage = new TokenStorage();
        tokenStorage.setCreationDate(LocalDateTime.now());
        tokenStorage.setExpireDate(getExpirationDate());
        tokenStorage.setToken(generate());
        tokenStorage.setUserEntity(entity);
    }

    private String generate(){

        return String.valueOf(UUID.randomUUID());
    }

    private LocalDateTime getExpirationDate(){

        System.out.println("ExpInterval = " + expInterval);
        return LocalDateTime.now().plusMinutes(expInterval);
    }

    public TokenStorage getTokenStorage(){

        return tokenStorage;
    }
}
