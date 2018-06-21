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
    private Date publishDate;

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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
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

    @Override
    public String toString() {
        return "BookEntity{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author.getName() +
                ", publisher=" + publisher.getName() +
                ", publishDate=" + publishDate +
                ", summary='" + summary + '\'' +
                ", genre=" + genre +
                ", available=" + available +
                ", cover='" + cover + '\'' +
                '}';
    }
}
