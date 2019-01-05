package fr.kybox.batch.result;

import java.util.LinkedHashMap;
import java.util.Map;

public class BatchResult {

    private static Map<String, Object> map = new LinkedHashMap<>();

    public static void put(String key, Object value){
        map.put(key, value);
    }

    public static void clear(){
        map.clear();
    }

    public static Map<String, Object> getMap(){
        return map;
    }
}
