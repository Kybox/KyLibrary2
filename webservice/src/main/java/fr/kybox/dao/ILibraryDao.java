package fr.kybox.dao;

import fr.kybox.entities.Book;
import fr.kybox.entities.User;

import java.util.List;

public interface ILibraryDao {

    User getUser(Integer id);

    List<Book> getUserBookList(User user);

    List<Book> searchBook(String keyword);
}
