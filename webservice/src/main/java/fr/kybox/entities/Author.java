package fr.kybox.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Kybox
 * @version 1.0
 */

@Entity
@Table(name = "author", schema = "public")
public class Author extends AbstractEntity{

    @Column
    private String name;

    public Author() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
