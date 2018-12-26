package fr.kybox.utils;

import fr.kybox.dao.TokenRepository;
import fr.kybox.entities.TokenStorage;
import fr.kybox.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class Token {

    public final static String ACTIVE = "active";
    public final static String USER = "user";

    @Value("${settings.tokenExpire}")
    private Integer expInterval;
    private TokenStorage tokenStorage;
    private final TokenRepository tokenRepository;

    @Autowired
    public Token(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

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

    public Map<String, Object> checkToken(String token){

        Map<String, Object> data = new HashMap<>();

        if(token != null && !token.isEmpty()){

            Optional<TokenStorage> tokenStorage = tokenRepository.findByToken(token);

            if(tokenStorage.isPresent()){

                if(tokenStorage.get().getExpireDate().isAfter(LocalDateTime.now())){

                    UserEntity userEntity = tokenStorage.get().getUserEntity();
                    data.put(ACTIVE, Boolean.TRUE);
                    data.put(USER, userEntity);
                }
                else {
                    tokenRepository.delete(tokenStorage.get());
                    data.put(ACTIVE, Boolean.FALSE);
                }
            }
            else data.put(ACTIVE, Boolean.FALSE);
        }
        else data.put(ACTIVE, Boolean.FALSE);

        return data;
    }
}
