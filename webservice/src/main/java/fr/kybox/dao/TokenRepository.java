package fr.kybox.dao;

import fr.kybox.entities.TokenStorage;
import fr.kybox.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenStorage, String> {

    TokenStorage findByUserEntity(UserEntity userEntity);

    Optional<TokenStorage> findByToken(String token);
}
