package fr.kybox.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeAdapter {

    private static Logger log = LogManager.getLogger(LocalDateTimeAdapter.class);

    public static LocalDateTime unmarshal(String xmlGregorianCalendar){
        try{
            LocalDateTime result = LocalDateTime.parse(xmlGregorianCalendar, DateTimeFormatter.ISO_DATE_TIME);
            return result;
        }
        catch (DateTimeParseException e){
            log.info("Could not parse date " + xmlGregorianCalendar, e);
            return null;
        }
    }

    public static String marshal(LocalDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
