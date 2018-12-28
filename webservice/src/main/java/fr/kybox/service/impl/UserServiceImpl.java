package fr.kybox.service.impl;

import fr.kybox.dao.TokenRepository;
import fr.kybox.dao.UserRepository;
import fr.kybox.entities.TokenStorage;
import fr.kybox.entities.UserEntity;
import fr.kybox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static fr.kybox.utils.ValueTypes.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Value("${settings.tokenExpire}")
    private Integer expInterval;

    private final UserRepository userDao;
    private final TokenRepository tokenDao;

    @Autowired
    public UserServiceImpl(UserRepository userDao, TokenRepository tokenDao) {
        this.userDao = userDao;
        this.tokenDao = tokenDao;
    }

    @Override
    public Optional<UserEntity> findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findUserById(int id) {
        return userDao.findById(id);
    }

    @Override
    public void saveUser(UserEntity user) {
        userDao.save(user);
    }

    @Override
    public TokenStorage generateNewTokenForUser(UserEntity user) {

        TokenStorage tokenStorage = new TokenStorage();
        tokenStorage.setCreationDate(LocalDateTime.now());
        tokenStorage.setExpireDate(LocalDateTime.now().plusMinutes(expInterval));
        tokenStorage.setToken(String.valueOf(UUID.randomUUID()));
        tokenStorage.setUserEntity(user);
        return tokenStorage;
    }

    @Override
    public Map<String, Object> checkTokenData(String token) {

        Map<String, Object> data = new HashMap<>();

        if(token == null || token.isEmpty()){
            data.put(TOKEN_ACTIVE, Boolean.FALSE);
            return data;
        }

        Optional<TokenStorage> optToken = tokenDao.findByToken(token);
        if(!optToken.isPresent()){
            data.put(TOKEN_ACTIVE, Boolean.FALSE);
            return data;
        }

        TokenStorage tokenStorage = optToken.get();
        if(!tokenStorage.getExpireDate().isAfter(LocalDateTime.now())){
            data.put(TOKEN_ACTIVE, Boolean.FALSE);
            return data;
        }

        UserEntity user = tokenStorage.getUserEntity();
        data.put(TOKEN_ACTIVE, Boolean.TRUE);
        data.put(USER_FROM_TOKEN, user);
        return data;
    }

    @Override
    public Optional<TokenStorage> findTokenByHash(String token) {
        return tokenDao.findByToken(token);
    }

    @Override
    public Optional<TokenStorage> findTokenByUser(UserEntity user) {
        return tokenDao.findByUserEntity(user);
    }

    @Override
    public void deleteToken(TokenStorage token) {
        tokenDao.delete(token);
    }

    @Override
    public void saveToken(TokenStorage token) {
        tokenDao.save(token);
    }
}
