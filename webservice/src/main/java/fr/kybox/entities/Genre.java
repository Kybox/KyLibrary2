package fr.kybox.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Kybox
 * @version 1.0
 */

@Entity
@Table(name = "genre", schema = "public")
public class Genre extends AbstractEntity {

    @Column
    private String name;

    public Genre() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
