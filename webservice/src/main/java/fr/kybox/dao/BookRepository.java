package fr.kybox.dao;

import fr.kybox.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


/**
 * @author Kybox
 * @version 1.0
 */
public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    Optional<BookEntity> findByIsbn(String isbn);

    List<BookEntity> findAllByTitleContainingOrAuthor_NameContainingOrGenre_NameContainingAllIgnoreCase
            (String title, String author, String genre);

    List<BookEntity> findAllByBookable(boolean bookable);
}
