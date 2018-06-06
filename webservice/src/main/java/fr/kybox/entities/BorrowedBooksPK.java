package fr.kybox.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * @author Kybox
 * @version 1.0
 */

@Embeddable
public class BorrowedBooksPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "book_id")
    private int bookId;

    public BorrowedBooksPK() {}

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }
}
