package fr.kybox.dao;

import fr.kybox.entities.BookEntity;
import fr.kybox.entities.BorrowedBook;
import fr.kybox.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BorrowedBooksRepository extends JpaRepository<BorrowedBook, Integer> {

    List<BorrowedBook> findAllByUserOrderByReturnedDesc(UserEntity user);
    BorrowedBook findByUserAndBook(UserEntity user, BookEntity bookEntity);
    Iterable<BorrowedBook> findAllByReturnDateAfterAndReturnedFalse(Date date);
}
