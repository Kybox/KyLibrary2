package fr.kybox.dao;

import fr.kybox.entities.BorrowedBooks;
import fr.kybox.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowedBooksRepository extends JpaRepository<BorrowedBooks, Integer> {

    List<BorrowedBooks> findAllByUser(User user);
}
