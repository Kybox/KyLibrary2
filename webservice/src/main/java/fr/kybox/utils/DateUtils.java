package fr.kybox.utils;

import java.util.Date;

public class DateUtils {

    public static Boolean isDateBefore(Object dateBefore, Object dateAfter){

        Boolean result = false;

        if(dateBefore instanceof java.util.Date)
            dateBefore = Converter.DateToSQLDate((Date) dateBefore);

        if(dateAfter instanceof java.util.Date)
            dateAfter = Converter.DateToSQLDate((Date) dateAfter);

        int yearBefore = Integer.parseInt(dateBefore.toString().substring(0,4));
        int yearAfter = Integer.parseInt(dateAfter.toString().substring(0,4));

        if(yearBefore == yearAfter){

            int monthBefore = Integer.parseInt(dateBefore.toString().substring(5,7));
            int monthAfter = Integer.parseInt(dateAfter.toString().substring(5,7));

            if(monthBefore == monthAfter){

                int dayBefore = Integer.parseInt(dateBefore.toString().substring(8,10));
                int dayAfter = Integer.parseInt(dateAfter.toString().substring(8,10));

                if(dayBefore < dayAfter) result = true;
                else if(dayBefore > dayAfter) result = false;
                else result = null;

            }
            else if(monthBefore < monthAfter) result = true;
            else result = false;
        }
        else if(yearBefore < yearAfter) result = true;
        else result = false;

        return result;
    }
}
