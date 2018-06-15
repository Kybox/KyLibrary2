package fr.kybox.dao;

import fr.kybox.entities.Author;
import fr.kybox.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * @author Kybox
 * @version 1.0
 */
public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    BookEntity findByIsbn(String isbn);
    Iterable<BookEntity> findAllByTitleContainingOrAuthor_NameContainingAllIgnoreCase(String keyword, String keyword2);
}
