package io.jenkins.plugins.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonNull;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class Util {
	public static Object transformToJavaLangStructures(Object object) {
        if (isNull(object)) {
            return null;
        } else if (object instanceof JSONArray) {
            return transformToArrayList((JSONArray) object);
        } else if (object instanceof JSONObject) {
            return transformToLinkedHashMap((JSONObject) object);
        }
        return object;
    }

	public static List<Object> transformToArrayList(JSONArray array) {
        List<Object> result = new ArrayList<>(array.size());
        for (Object arrayItem : array) {
            result.add(transformToJavaLangStructures(arrayItem));
        }
        return result;
    }

	public static Map<String, Object> transformToLinkedHashMap(JSONObject object) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> objectEntry : (Set<Map.Entry<String, Object>>) object.entrySet()) {
            result.put(objectEntry.getKey(), transformToJavaLangStructures(objectEntry.getValue()));
        }
        return result;
    }

	public static boolean isNull(Object value) {
        if (value instanceof JsonNull) {
            return true;
        }
        if (value instanceof JSONObject) {
            try {
                ((Map) value).get((Object) "somekey");
            } catch (JSONException e) {
                // JSONException is returned by verifyIsNull method in JSONObject when accessing one of its properties
                return true;
            }
        }
        return false;
    }
}
