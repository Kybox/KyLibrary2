package fr.kybox.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.sql.Date;

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

    @Column(name = "nb_copies")
    private int nbCopies;

    @Column
    private Boolean bookable;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "available_for_booking")
    private Integer availableForBooking;

    @Column(name = "reservations")
    private Integer nbReservations;

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

    public int getNbCopies() {
        return nbCopies;
    }

    public void setNbCopies(int nbCopies) {
        this.nbCopies = nbCopies;
    }

    public Boolean getBookable() {
        return bookable;
    }

    public void setBookable(Boolean bookable) {
        this.bookable = bookable;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getAvailableForBooking() {
        return availableForBooking;
    }

    public void setAvailableForBooking(Integer availableForBooking) {
        this.availableForBooking = availableForBooking;
    }

    public int getNbReservations() {
        return nbReservations;
    }

    public void setNbReservations(Integer nbReservations) {
        this.nbReservations = nbReservations;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author.getName() +
                ", publisher=" + publisher.getName() +
                ", publishDate=" + publishDate +
                ", summary='" + summary + '\'' +
                ", genre=" + genre.getName() +
                ", available=" + available +
                ", cover='" + cover + '\'' +
                ", nbCopies=" + nbCopies +
                ", bookable=" + bookable +
                ", returnDate=" + returnDate +
                ", availableForBooking=" + availableForBooking +
                ", nbReservations=" + nbReservations +
                '}';
    }
}
