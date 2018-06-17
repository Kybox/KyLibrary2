package fr.kybox.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;

/**
 * @author Kybox
 * @version 1.0
 */
public class Converter {

    private final static Logger logger = LogManager.getLogger(Converter.class);

    public static Date DateToSQLDate(java.util.Date date){
        return new Date(date.getTime());
    }
}
