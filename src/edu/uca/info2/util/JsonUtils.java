/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.info2.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.uca.info2.components.Area;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Toshiba
 */
public class JsonUtils {
    public static <T> List<T> deserializeList(String zones, Class<T> clazz) {
        
        Gson gson = new Gson();
        //Type collectionType = new TypeToken<ArrayList<clazz.class>>(){}.getType();

        List<T> objectList = (List<T>)gson.fromJson(zones, clazz);

        System.out.println("De-serialized object list: " + objectList);

        return objectList;

    }
}
