package fr.kybox.entities;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author Kybox
 * @version 1.0
 */

@Entity
@Table(name = "user_account", schema = "public")
public class UserEntity extends AbstractEntity {

    @Column
    private String email;

    @Column
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private Date birthday;

    @Column(name = "postal_address")
    private String postalAddress;

    @Column
    private String tel;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @Column(name = "alert_sender")
    private Boolean alertSender;

    public UserEntity() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getTel() { return tel; }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Level getLevel() { return level; }

    public void setLevel(Level level) { this.level = level; }

    public Boolean getAlertSender() {
        return alertSender;
    }

    public void setAlertSender(Boolean alertSender) {
        this.alertSender = alertSender;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", postalAddress='" + postalAddress + '\'' +
                ", tel='" + tel + '\'' +
                ", level=" + level +
                ", alertSender=" + alertSender +
                '}';
    }
}
