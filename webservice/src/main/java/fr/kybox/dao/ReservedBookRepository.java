package fr.kybox.dao;

import fr.kybox.entities.BookEntity;
import fr.kybox.entities.ReservedBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservedBookRepository extends JpaRepository<ReservedBook, Integer> {

    List<ReservedBook> findAllByBookEntity(BookEntity bookEntity);
}
