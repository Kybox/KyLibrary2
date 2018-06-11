package fr.kybox.dao;

import fr.kybox.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Kybox
 * @version 1.0
 */
public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    BookEntity findByIsbn(String isbn);
}
