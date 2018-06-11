package fr.kybox.utils;

import org.joda.time.LocalDate;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.sql.SQLData;
import java.time.ZoneId;
import java.util.GregorianCalendar;

import static java.time.ZoneId.systemDefault;

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

    public static Date DateToSQLDate(java.util.Date date){

        return new Date(date.getTime());
    }

    public static XMLGregorianCalendar LocalDateToXML(LocalDate localDate){

        java.util.Date date = localDate.toDate();

        Date sqlDate = new Date(date.getTime());

        return SQLDateToXML(sqlDate);
    }
}
