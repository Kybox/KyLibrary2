package fr.kybox.service.impl;

import fr.kybox.dao.BookRepository;
import fr.kybox.dao.BorrowedBookRepository;
import fr.kybox.dao.ReservedBookRepository;
import fr.kybox.entities.BookEntity;
import fr.kybox.entities.BorrowedBook;
import fr.kybox.entities.ReservedBook;
import fr.kybox.entities.UserEntity;
import fr.kybox.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookDao;
    private final BorrowedBookRepository borrowingDao;
    private final ReservedBookRepository reservationDao;

    @Autowired
    public BookServiceImpl(BookRepository bookDao, ReservedBookRepository reservationDao,
                           BorrowedBookRepository borrowingDao) {
        this.bookDao = bookDao;
        this.reservationDao = reservationDao;
        this.borrowingDao = borrowingDao;
    }

    @Override
    public Optional<BookEntity> findBookById(int id) {
        return bookDao.findById(id);
    }

    @Override
    public Optional<BookEntity> findBookByIsbn(String isbn) {
        return bookDao.findByIsbn(isbn);
    }

    @Override
    public List<BookEntity> findAllBooksByBookable(boolean bookable) {
        return bookDao.findAllByBookable(bookable);
    }

    @Override
    public List<BookEntity> findAllBooksByTitleOrByAuthorOrByGenreContaining(String keywords) {
        return bookDao
                .findAllByTitleContainingOrAuthor_NameContainingOrGenre_NameContainingAllIgnoreCase(
                        keywords, keywords, keywords);
    }

    @Override
    public void saveBook(BookEntity book) {
        bookDao.save(book);
    }

    @Override
    public List<ReservedBook> findAllReservedBooksByUser(UserEntity userEntity) {
        return reservationDao.findAllByUser(userEntity);
    }

    @Override
    public Optional<ReservedBook> findReservedBookByUserAndBookAndPendingTrue(UserEntity user, BookEntity book) {
        return reservationDao.findByUserAndBookAndPendingTrue(user, book);
    }

    @Override
    public List<ReservedBook> findAllReservedBooksByBookAndPendingTrueOrderByReserveDateAsc(BookEntity book) {
        return reservationDao.findAllByBookAndPendingTrueOrderByReserveDateAsc(book);
    }

    @Override
    public void saveReservedBook(ReservedBook book) {
        reservationDao.save(book);
    }

    @Override
    public void deleteReservedBook(ReservedBook book) {
        reservationDao.delete(book);
    }

    @Override
    public BorrowedBook findBorrowedBookByUserAndBook(UserEntity user, BookEntity book) {
        return borrowingDao.findByUserAndBook(user, book);
    }

    @Override
    public List<BorrowedBook> findAllBorrowedBooksByUserOrderByReturnedAsc(UserEntity user) {
        return borrowingDao.findAllByUserOrderByReturnedAsc(user);
    }

    @Override
    public List<BorrowedBook> findAllBorrowedBooksNotReturnedAndReturnDateBefore(Date date) {
        return borrowingDao.findAllByReturnDateBeforeAndReturnedFalse(date);
    }

    @Override
    public List<BorrowedBook> findAllBorrowedBooksByBookAndNotReturnedAndOrderByReturnDateAsc(BookEntity book) {
        return borrowingDao.findAllByBookAndReturnedFalseOrderByReturnDateAsc(book);
    }

    @Override
    public void saveBorrowedBook(BorrowedBook book) {
        borrowingDao.save(book);
    }
}
