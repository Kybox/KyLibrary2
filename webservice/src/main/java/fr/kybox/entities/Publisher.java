package fr.kybox.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Kybox
 * @version 1.0
 */

@Entity
@Table(name = "publisher", schema = "public")
public class Publisher extends AbstractEntity{

    @Column
    private String name;

    public Publisher() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
