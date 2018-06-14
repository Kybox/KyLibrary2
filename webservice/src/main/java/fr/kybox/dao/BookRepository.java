package fr.kybox.dao;

import fr.kybox.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 */
public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    BookEntity findByIsbn(String isbn);
    List<BookEntity> findAllByTitleIgnoreCaseContaining(String keyword);
}
