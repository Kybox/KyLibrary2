package fr.kybox.dao;

import fr.kybox.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Kybox
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findAllByLastNameContainingAndFirstNameContainingAllIgnoreCase(String lastname, String firstname);
}
