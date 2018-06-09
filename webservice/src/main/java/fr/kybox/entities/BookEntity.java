package fr.kybox.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

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
@NaturalIdCache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BookEntity extends AbstractEntity {

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowedBook> users = new ArrayList<>();

    @Column
    @NaturalId
    private String isbn;

    @Column
    private String title;

    @OneToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Column
    private Date publisherdate;

    @Column
    private String summary;

    @OneToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Column
    private int available;

    @Column
    private String cover;

    public BookEntity() {}

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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getCover() { return cover; }

    public void setCover(String cover) { this.cover = cover; }

    public List<BorrowedBook> getUsers() {
        return users;
    }

    public void setUsers(List<BorrowedBook> users) {
        this.users = users;
    }
}
