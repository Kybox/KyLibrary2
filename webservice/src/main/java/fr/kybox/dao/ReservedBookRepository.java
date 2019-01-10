package fr.kybox.dao;

import fr.kybox.entities.BookEntity;
import fr.kybox.entities.ReservedBook;
import fr.kybox.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservedBookRepository extends JpaRepository<ReservedBook, Integer> {

    List<ReservedBook> findAllByBook(BookEntity bookEntity);
    List<ReservedBook> findAllByUser(UserEntity userEntity);
    Optional<ReservedBook> findFirstByBookAndPendingIsTrue(BookEntity book);
    List<ReservedBook> findAllByBookAndPendingTrueOrderByReserveDateAsc(BookEntity bookEntity);
    Optional<ReservedBook> findFirstByBookAndPendingTrueOrderByReserveDateAsc(BookEntity bookEntity);
    Optional<ReservedBook> findByUserAndBookAndPendingTrue(UserEntity userEntity, BookEntity bookEntity);
}
