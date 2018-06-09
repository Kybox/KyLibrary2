package fr.kybox.entities;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author Kybox
 * @version 1.0
 */

@Entity
@Table(name = "borrowed_book", schema = "public")
public class BorrowedBook {

    @EmbeddedId
    private BorrowedBookPK borrowedBookPK = new BorrowedBookPK();

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("bookId")
    private BookEntity book;

    @Column(name = "return_date")
    private Date returnDate;

    @Column
    private Boolean extended;

    @Column
    private Boolean returned;

    public BorrowedBook() {}

    public BorrowedBookPK getBorrowedBookPK() {
        return borrowedBookPK;
    }

    public void setBorrowedBookPK(BorrowedBookPK borrowedBookPK) {
        this.borrowedBookPK = borrowedBookPK;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
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

    public Boolean getReturned() { return returned; }

    public void setReturned(Boolean returned) { this.returned = returned; }
}
