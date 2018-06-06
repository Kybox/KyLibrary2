package fr.kybox.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author Kybox
 * @version 1.0
 */

@Entity
@Table(name = "borrowed_books", schema = "public")
public class BorrowedBooks implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "return_date")
    private Date returnDate;

    @Column
    private Boolean extended;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Boolean getExtended() {
        return extended;
    }

    public void setExtended(Boolean extended) {
        this.extended = extended;
    }
}
