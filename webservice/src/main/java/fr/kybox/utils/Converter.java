package fr.kybox.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.util.GregorianCalendar;

/**
 * @author Kybox
 * @version 1.0
 */
public class Converter {

    public static XMLGregorianCalendar SQLDateToXML(Date SQLDate){

        XMLGregorianCalendar xmlDate = null;

        if(SQLDate != null) {

            try {
                GregorianCalendar gregorianCalendar = new GregorianCalendar();

                gregorianCalendar.setTime(SQLDate);
                xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);

            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }
        }

        return xmlDate;
    }
}
