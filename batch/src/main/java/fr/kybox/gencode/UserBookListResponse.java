
package fr.kybox.gencode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{dd7b026a-d6a2-4089-adb2-596ab0598c73}bookBorrowed" maxOccurs="unbounded"/&gt;
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
    "bookBorrowed"
})
@XmlRootElement(name = "userBookListResponse")
public class UserBookListResponse
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", required = true)
    protected List<BookBorrowed> bookBorrowed;

    /**
     * Gets the value of the bookBorrowed property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bookBorrowed property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBookBorrowed().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BookBorrowed }
     * 
     * 
     */
    public List<BookBorrowed> getBookBorrowed() {
        if (bookBorrowed == null) {
            bookBorrowed = new ArrayList<BookBorrowed>();
        }
        return this.bookBorrowed;
    }

}
