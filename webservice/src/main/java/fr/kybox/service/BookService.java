package fr.kybox.service;

import fr.kybox.entities.BookEntity;
import fr.kybox.entities.BorrowedBook;
import fr.kybox.entities.ReservedBook;
import fr.kybox.entities.UserEntity;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface BookService {

    /**
     * BookEntity
     */
    Optional<BookEntity> findBookById(int id);
    Optional<BookEntity> findBookByIsbn(String isbn);
    List<BookEntity> findAllBooksByBookable(boolean bookable);
    List<BookEntity> findAllBooksByTitleOrByAuthorOrByGenreContaining(String keywords);
    void saveBook(BookEntity book);

    /**
     * ReservedBook
     */
    List<ReservedBook> findAllReservedBooksByUser(UserEntity user);
    Optional<ReservedBook> findReservedBookByUserAndBookAndPendingTrue(UserEntity user, BookEntity book);
    List<ReservedBook> findAllReservedBooksByBookAndPendingTrueOrderByReserveDateAsc(BookEntity book);
    Optional<ReservedBook> findFirstReservedBooksByBookAndPendingTrueOrderByReserveDateAsc(BookEntity book);
    boolean areThereAnyReservationForBook(BookEntity book);
    void saveReservedBook(ReservedBook book);
    void deleteReservedBook(ReservedBook book);

    /**
     * BorrowedBook
     */
    BorrowedBook findBorrowedBookByUserAndBook(UserEntity user, BookEntity book);
    List<BorrowedBook> findAllBorrowedBooksByUserOrderByReturnedAsc(UserEntity user);
    List<BorrowedBook> findAllBorrowedBooksNotReturnedAndReturnDateBefore(Date date);
    List<BorrowedBook> findAllBorrowedBooksByBookAndNotReturnedAndOrderByReturnDateAsc(BookEntity book);
    List<BorrowedBook> findAllBorrowedBooksByBook(BookEntity book);
    Optional<BorrowedBook> findBorrowedBookByIsbnAndUserEmail(String isbn, String email);
    List<BorrowedBook> findAllBorrowedBooksUnreturnedAndReturnDateBefore(Date date);
    void saveBorrowedBook(BorrowedBook book);
}
