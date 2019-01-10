
package fr.kybox.gencode;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{dd7b026a-d6a2-4089-adb2-596ab0598c73}user"/&gt;
 *         &lt;element ref="{dd7b026a-d6a2-4089-adb2-596ab0598c73}bookBorrowed"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "user",
    "bookBorrowed"
})
@XmlRootElement(name = "borrower")
public class Borrower
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", required = true)
    protected User user;
    @XmlElement(namespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", required = true)
    protected BookBorrowed bookBorrowed;

    /**
     * Obtient la valeur de la propriété user.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getUser() {
        return user;
    }

    /**
     * Définit la valeur de la propriété user.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setUser(User value) {
        this.user = value;
    }

    /**
     * Obtient la valeur de la propriété bookBorrowed.
     * 
     * @return
     *     possible object is
     *     {@link BookBorrowed }
     *     
     */
    public BookBorrowed getBookBorrowed() {
        return bookBorrowed;
    }

    /**
     * Définit la valeur de la propriété bookBorrowed.
     * 
     * @param value
     *     allowed object is
     *     {@link BookBorrowed }
     *     
     */
    public void setBookBorrowed(BookBorrowed value) {
        this.bookBorrowed = value;
    }

}
