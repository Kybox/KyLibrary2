package fr.kybox.dao;

import fr.kybox.entities.Book;
import fr.kybox.entities.User;
import fr.kybox.security.Password;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 */
@Repository("libraryDao")
public class LibraryDaoImpl implements LibraryDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(value = "txManager")
    public User getUserByID(Integer id) {

        User user = null;

        if(id != null){
            user = (User) sessionFactory.getCurrentSession()
                    .createNamedQuery(User.GET_USER_BY_ID)
                    .setParameter("id", id)
                    .getSingleResult();
        }
        return user;
    }

    @Override
    @Transactional(value = "txManager")
    public User getUserByLogin(String email, String password) {

        User user = null;

        String hashPass = Password.encode(password);

        if(email != null && !email.isEmpty() && password != null && !password.isEmpty()){

            user = (User) sessionFactory.getCurrentSession().createNamedQuery(User.GET_USER_BY_LOGIN)
                    .setParameter("email", email).setParameter("password", hashPass).getSingleResult();
        }

        return user;
    }

    @Override
    public List<Book> getUserBookList(User user) {
        return null;
    }

    @Override
    public List<Book> searchBook(String keyword) {
        return null;
    }
}
