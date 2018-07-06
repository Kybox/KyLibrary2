package fr.kybox.batch.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Kybox
 * @version 1.0
 */
public class BatchResult {

    public static Map<String, Object> map = new LinkedHashMap<>();

    public static void put(String key, Object value){

        map.put(key, value);
    }
}
