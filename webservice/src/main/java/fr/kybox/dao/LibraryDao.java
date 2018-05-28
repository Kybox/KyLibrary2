package fr.kybox.dao;

import fr.kybox.entities.Book;
import fr.kybox.entities.User;

import java.util.List;

public interface LibraryDao {

    User getUserByID(Integer id);

    User getUserByLogin(String email, String password);

    List<Book> getUserBookList(User user);

    List<Book> searchBook(String keyword);
}
