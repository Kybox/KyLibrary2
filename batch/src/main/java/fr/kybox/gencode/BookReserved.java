
package fr.kybox.gencode;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element ref="{dd7b026a-d6a2-4089-adb2-596ab0598c73}book"/&gt;
 *         &lt;element name="reserveDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="pending" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
    "book",
    "reserveDate",
    "pending",
    "position",
    "total"
})
@XmlRootElement(name = "bookReserved")
public class BookReserved
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", required = true)
    protected Book book;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date reserveDate;
    protected boolean pending;
    protected int position;
    protected int total;

    /**
     * Obtient la valeur de la propriété book.
     * 
     * @return
     *     possible object is
     *     {@link Book }
     *     
     */
    public Book getBook() {
        return book;
    }

    /**
     * Définit la valeur de la propriété book.
     * 
     * @param value
     *     allowed object is
     *     {@link Book }
     *     
     */
    public void setBook(Book value) {
        this.book = value;
    }

    /**
     * Obtient la valeur de la propriété reserveDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getReserveDate() {
        return reserveDate;
    }

    /**
     * Définit la valeur de la propriété reserveDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReserveDate(Date value) {
        this.reserveDate = value;
    }

    /**
     * Obtient la valeur de la propriété pending.
     * 
     */
    public boolean isPending() {
        return pending;
    }

    /**
     * Définit la valeur de la propriété pending.
     * 
     */
    public void setPending(boolean value) {
        this.pending = value;
    }

    /**
     * Obtient la valeur de la propriété position.
     * 
     */
    public int getPosition() {
        return position;
    }

    /**
     * Définit la valeur de la propriété position.
     * 
     */
    public void setPosition(int value) {
        this.position = value;
    }

    /**
     * Obtient la valeur de la propriété total.
     * 
     */
    public int getTotal() {
        return total;
    }

    /**
     * Définit la valeur de la propriété total.
     * 
     */
    public void setTotal(int value) {
        this.total = value;
    }

}
