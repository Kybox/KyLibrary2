
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
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element ref="{dd7b026a-d6a2-4089-adb2-596ab0598c73}reservedBook" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "result",
    "reservedBook"
})
@XmlRootElement(name = "reservedBookListResponse")
public class ReservedBookListResponse
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected int result;
    @XmlElement(namespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
    protected List<ReservedBook> reservedBook;

    /**
     * Obtient la valeur de la propriété result.
     * 
     */
    public int getResult() {
        return result;
    }

    /**
     * Définit la valeur de la propriété result.
     * 
     */
    public void setResult(int value) {
        this.result = value;
    }

    /**
     * Gets the value of the reservedBook property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reservedBook property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReservedBook().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReservedBook }
     * 
     * 
     */
    public List<ReservedBook> getReservedBook() {
        if (reservedBook == null) {
            reservedBook = new ArrayList<ReservedBook>();
        }
        return this.reservedBook;
    }

}
