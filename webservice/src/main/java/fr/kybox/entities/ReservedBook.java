package fr.kybox.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reserved_book", schema = "public")
public class ReservedBook {

    @EmbeddedId
    private ReservedBookPK reservedBookPK = new ReservedBookPK();

    @ManyToOne
    @MapsId("userId")
    private UserEntity user;

    @ManyToOne
    @MapsId("bookId")
    private BookEntity book;

    @Column(name = "reserve_date")
    private LocalDateTime reserveDate;

    @Column(name = "pending")
    private boolean pending;

    @Column(name = "notified")
    private boolean notified;

    @Column(name = "notification_date")
    private LocalDateTime notificationDate;

    public ReservedBook() {}

    public ReservedBookPK getReservedBookPK() {
        return reservedBookPK;
    }

    public void setReservedBookPK(ReservedBookPK reservedBookPK) {
        this.reservedBookPK = reservedBookPK;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public LocalDateTime getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(LocalDateTime reserveDate) {
        this.reserveDate = reserveDate;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = notificationDate;
    }
}
