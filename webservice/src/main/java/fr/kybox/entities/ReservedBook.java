package fr.kybox.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reserved_book", schema = "public")
public class ReservedBook {

    @EmbeddedId
    private ReservedBookPK reservedBookPK = new ReservedBookPK();

    @ManyToOne
    @MapsId("user_id")
    private UserEntity userEntity;

    @ManyToOne
    @MapsId("book_id")
    private BookEntity bookEntity;

    @Column(name = "reserve_date")
    private LocalDateTime reserveDate;

    @Column(name = "pending")
    private boolean pending;

    public ReservedBook() {}

    public ReservedBookPK getReservedBookPK() {
        return reservedBookPK;
    }

    public void setReservedBookPK(ReservedBookPK reservedBookPK) {
        this.reservedBookPK = reservedBookPK;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public BookEntity getBookEntity() {
        return bookEntity;
    }

    public void setBookEntity(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
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
}
