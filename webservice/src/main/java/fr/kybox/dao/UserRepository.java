package fr.kybox.dao;

import fr.kybox.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Kybox
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}
