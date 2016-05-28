package com.redoc.yuedu.utilities.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.redoc.yuedu.YueduApplication;

/**
 * Created by limen on 2016/5/22.
 */
public class PreferenceUtilities {
    public static boolean writeToPreference(String preferenceFileName, String key, int value) {
        SharedPreferences preferences = YueduApplication.Context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static boolean writeToPreference(String preferenceFileName, String key, long value) {
        SharedPreferences preferences = YueduApplication.Context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static boolean writeToPreference(String preferenceFileName, String key, String value) {
        SharedPreferences preferences = YueduApplication.Context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static boolean writeToPreference(String preferenceFileName, String key, boolean value) {
        SharedPreferences preferences = YueduApplication.Context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean writeToPreference(String preferenceFileName, String key, float value) {
        SharedPreferences preferences = YueduApplication.Context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static int getIntValue(String preferenceFileName, String key) {
        SharedPreferences preferences = YueduApplication.Context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

    public static long getLongValue(String preferenceFileName, String key) {
        SharedPreferences preferences = YueduApplication.Context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
        return preferences.getLong(key, 0);
    }

    public static float getFloatValue(String preferenceFileName, String key) {
        SharedPreferences preferences = YueduApplication.Context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
        return preferences.getFloat(key, 0);
    }

    public static String getStringValue(String preferenceFileName, String key) {
        SharedPreferences preferences = YueduApplication.Context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static boolean getBooleanValue(String preferenceFileName, String key) {
        SharedPreferences preferences = YueduApplication.Context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static boolean containsKey(String preferenceFileName, String key) {
        SharedPreferences preferences = YueduApplication.Context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
        return preferences.contains(key);
    }
}
