package fr.kybox.utils;

import java.sql.Date;
import java.util.Calendar;

/**
 * @author Kybox
 * @version 1.0
 */
public class Converter {

    public static Calendar SQLDateToCalendar(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date DateToSQLDate(java.util.Date date){

        return new Date(date.getTime());
    }
}
