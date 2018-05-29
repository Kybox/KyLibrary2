package fr.kybox.dao;

import fr.kybox.entities.Book;
import fr.kybox.entities.User;
import fr.kybox.security.Password;
import fr.kybox.service.LibraryServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 */
@Repository("libraryDao")
public class LibraryDaoImpl implements LibraryDao {

    private static final Logger logger = LogManager.getLogger(LibraryServiceImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(value = "txManager")
    public User getUserByEmail(String email) {

        User user = null;

        if(email != null && !email.isEmpty()){

            try {
                user = (User) sessionFactory.getCurrentSession().createNamedQuery(User.GET_USER_BY_EMAIL)
                        .setParameter("email", email)
                        .getSingleResult();
            }
            catch (NoResultException e){
                logger.error("No result exception : No user found with this email address");
            }
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
