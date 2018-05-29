package fr.kybox.dao;

import fr.kybox.entities.Book;
import fr.kybox.entities.User;

import java.util.List;

public interface LibraryDao {

    User getUserByEmail(String email);

    List<Book> getUserBookList(User user);

    List<Book> searchBook(String keyword);
}
