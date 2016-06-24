package com.redoc.yuedu.setting.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.redoc.yuedu.YueduApplication;
import com.redoc.yuedu.setting.utilities.OfflineCacheUtils;

import java.util.Calendar;

public class SystemStartupBroardcastReceiver extends BroadcastReceiver {

    public SystemStartupBroardcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast toast = Toast.makeText(context, "Start up", Toast.LENGTH_LONG);
        toast.show();
        if(OfflineCacheUtils.getAutoCacheEnabledFromPreference()) {
            // setCacheAlarmAction();
            Toast toast2 = Toast.makeText(context, "Set alarm", Toast.LENGTH_LONG);
            toast2.show();
            OfflineCacheUtils.setOfflineCacheSchedule(OfflineCacheUtils.getOfflineCacheScheduleFromPreference());
        }
    }
}
