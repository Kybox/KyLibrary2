package fr.kybox.dao;

import fr.kybox.entities.BookEntity;
import fr.kybox.entities.BorrowedBook;
import fr.kybox.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Integer> {

    List<BorrowedBook> findAllByUserOrderByReturnedAsc(UserEntity user);
    BorrowedBook findByUserAndBook(UserEntity user, BookEntity bookEntity);
    List<BorrowedBook> findAllByReturnDateBeforeAndReturnedFalse(Date date);
    List<BorrowedBook> findAllByBookAndReturnedFalseOrderByReturnDateAsc(BookEntity bookEntity);
    List<BorrowedBook> findAllByBook(BookEntity bookEntity);
    Optional<BorrowedBook> findByBook_IsbnAndUser_EmailAndAndReturnedFalse(String isbn, String email);
    List<BorrowedBook> findAllByReturnedIsFalseAndReturnDateBefore(Date date);
    List<BorrowedBook> findAllByUserAndReturnedFalse(UserEntity user);
}
