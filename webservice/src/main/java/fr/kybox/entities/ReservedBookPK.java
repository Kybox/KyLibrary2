package fr.kybox.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ReservedBookPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "book_id")
    private int bookId;

    public ReservedBookPK() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
