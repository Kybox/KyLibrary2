
package fr.kybox.gencode;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="reservedate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="pending" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
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
    "reservedate",
    "pending"
})
@XmlRootElement(name = "reservedBook")
public class ReservedBook
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", required = true)
    protected Book book;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar reservedate;
    protected boolean pending;

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
     * Obtient la valeur de la propriété reservedate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReservedate() {
        return reservedate;
    }

    /**
     * Définit la valeur de la propriété reservedate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReservedate(XMLGregorianCalendar value) {
        this.reservedate = value;
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

}
