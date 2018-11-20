package fr.kybox.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token_storage")
public class TokenStorage {

    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "creation")
    private LocalDateTime creationDate;

    @Column(name = "expire")
    private LocalDateTime expireDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public TokenStorage() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}