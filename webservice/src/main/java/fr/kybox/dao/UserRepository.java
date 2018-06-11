package fr.kybox.dao;

import fr.kybox.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Kybox
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByEmail(String email);
}
