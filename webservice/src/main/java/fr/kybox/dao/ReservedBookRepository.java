package fr.kybox.dao;

import fr.kybox.entities.BookEntity;
import fr.kybox.entities.ReservedBook;
import fr.kybox.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservedBookRepository extends JpaRepository<ReservedBook, Integer> {

    List<ReservedBook> findAllByBook(BookEntity bookEntity);
    List<ReservedBook> findAllByUser(UserEntity userEntity);
}
