package com.redoc.yuedu.utilities.json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by limen on 2016/4/30.
 */
public class JsonUtilities {
    /**
     * @param key json key
     * @param jsonObject json object
     * @return string value
     * @throws JSONException
     */
    public static String getString(String key, JSONObject jsonObject) throws JSONException {
        String res = "";
        if (jsonObject.has(key)) {
            if (key == null) {
                return "";
            }
            res = jsonObject.getString(key);
        }
        return res;
    }

    /**
     * @param key json key
     * @param jsonObject json object
     * @return int value
     * @throws JSONException
     */
    public static int getInt(String key, JSONObject jsonObject) throws JSONException {
        int res = -1;
        if (jsonObject.has(key)) {
            res = jsonObject.getInt(key);
        }
        return res;
    }

    /**
     * @param key json key
     * @param jsonObject json object
     * @return double value
     * @throws JSONException
     */
    public static double getDouble(String key, JSONObject jsonObject) throws JSONException {
        double res = 0l;
        if (jsonObject.has(key)) {
            res = jsonObject.getDouble(key);
        }
        return res;
    }
}
