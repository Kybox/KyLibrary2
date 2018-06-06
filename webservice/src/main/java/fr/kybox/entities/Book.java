package fr.kybox.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 */

@Entity
@Table(name = "book", schema = "public")
@NamedQuery(name = Book.GET_USER_BOOKLIST, query = "SELECT b FROM BorrowedBooks b WHERE User.id = :userId")
public class Book extends AbstractEntity {

    public static final String GET_USER_BOOKLIST = "Book.getUserBookList";

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowedBooks> borrowedBooksList = new ArrayList<>();

    @Column
    private String isbn;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String publisher;

    @Column
    private Date publisherdate;

    @Column
    private String summary;

    @Column
    private String genre;

    @Column
    private int available;

    @Column
    private boolean extended;

    @Column
    private Date returndate;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublisherdate() {
        return publisherdate;
    }

    public void setPublisherdate(Date publisherdate) {
        this.publisherdate = publisherdate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public Date getReturndate() {
        return returndate;
    }

    public void setReturndate(Date returndate) {
        this.returndate = returndate;
    }

    public List<BorrowedBooks> getBorrowedBooksList() {
        return borrowedBooksList;
    }

    public void setBorrowedBooksList(List<BorrowedBooks> borrowedBooksList) {
        this.borrowedBooksList = borrowedBooksList;
    }
}
