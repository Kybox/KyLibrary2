package fr.kybox.service;

import fr.kybox.entities.TokenStorage;
import fr.kybox.entities.UserEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    /**
     * User
     */
    Optional<UserEntity> findUserByEmail(String email);
    Optional<UserEntity> findUserById(int id);
    List<UserEntity> findAllUserByLastNameAndFirstName(String firstName, String lastName);
    void saveUser(UserEntity user);

    /**
     * Token
     */
    TokenStorage generateNewTokenForUser(UserEntity user);
    Map<String, Object> checkTokenData(String token);
    Optional<TokenStorage> findTokenByHash(String token);
    Optional<TokenStorage> findTokenByUser(UserEntity user);
    void deleteToken(TokenStorage token);
    void saveToken(TokenStorage token);
}
