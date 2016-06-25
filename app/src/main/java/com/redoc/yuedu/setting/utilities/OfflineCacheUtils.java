package com.redoc.yuedu.setting.utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.redoc.yuedu.YueduApplication;
import com.redoc.yuedu.setting.service.OfflineCacheService;
import com.redoc.yuedu.utilities.preference.PreferenceUtilities;

import java.util.Calendar;

/**
 * Created by limen on 2016/6/23.
 */
public class OfflineCacheUtils {
    private static String PreferenceFileName = "OfflineCacheSchedule";
    private final static String Hour = "HOUR";
    private final static String Min = "MIN";
    private final static String Second = "SECOND";
    private final static String AutoCacheEnabled = "AUTO_CACHE_ENABLED";
    public static final int TIME_INTERVAL=1000*60*60*24;
    public static final String CACHE_ACTION = "CacheAction";
    public static final int START_CACHE = 1;
    public static final int RESUME_CACHE = 2;
    public static final int PAUSE_CACHE = 3;

    public static void writeOfflineCacheScheduleToPreference(Calendar calendar) {
        PreferenceUtilities.writeToPreference(PreferenceFileName, Hour, calendar.get(Calendar.HOUR_OF_DAY));
        PreferenceUtilities.writeToPreference(PreferenceFileName, Min, calendar.get(Calendar.MINUTE));
        PreferenceUtilities.writeToPreference(PreferenceFileName, Second, calendar.get(Calendar.SECOND));
    }

    public static void writeAutoCacheEnabledToPreference(boolean enabled) {
        PreferenceUtilities.writeToPreference(PreferenceFileName, AutoCacheEnabled, enabled);
    }

    public static Calendar getOfflineCacheScheduleFromPreference() {
        int hour = PreferenceUtilities.getIntValue(PreferenceFileName, Hour);
        int min = PreferenceUtilities.getIntValue(PreferenceFileName, Min);
        int second = PreferenceUtilities.getIntValue(PreferenceFileName, Second);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, second);
        return calendar;
    }

    public static boolean getAutoCacheEnabledFromPreference() {
        return PreferenceUtilities.getBooleanValue(PreferenceFileName, AutoCacheEnabled);
    }

    public static void setOfflineCacheSchedule(Calendar calendar) {
        Intent intent = new Intent(YueduApplication.Context, OfflineCacheService.class);
        intent.putExtra(OfflineCacheUtils.CACHE_ACTION, OfflineCacheUtils.START_CACHE);
        PendingIntent pendingIntent = PendingIntent.getService(YueduApplication.Context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager =(AlarmManager)YueduApplication.Context.getSystemService(
                Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                OfflineCacheUtils.TIME_INTERVAL, pendingIntent);
    }

    public static String calendarToHourAndMin(Calendar calendar) {
        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String min = minute < 10 ? "0" + Integer.toString(minute) :
                Integer.toString(minute);
        String hourOfDay = hour < 10 ? "0" + Integer.toString(hour) :
                Integer.toString(hour);
        return hourOfDay + ":" + min;
    }
}
