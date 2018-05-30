
package fr.kybox.gencode;

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
 *         &lt;element ref="{dd7b026a-d6a2-4089-adb2-596ab0598c73}bookList"/&gt;
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
    "bookList"
})
@XmlRootElement(name = "searchBookResponse")
public class SearchBookResponse {

    @XmlElement(namespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", required = true)
    protected BookList bookList;

    /**
     * Obtient la valeur de la propriété bookList.
     * 
     * @return
     *     possible object is
     *     {@link BookList }
     *     
     */
    public BookList getBookList() {
        return bookList;
    }

    /**
     * Définit la valeur de la propriété bookList.
     * 
     * @param value
     *     allowed object is
     *     {@link BookList }
     *     
     */
    public void setBookList(BookList value) {
        this.bookList = value;
    }

}
