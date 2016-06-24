package com.redoc.yuedu.setting.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Calendar;

public class OfflineCacheService extends Service {
    public OfflineCacheService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Toast toast = Toast.makeText(this, "Offline Cache Service", Toast.LENGTH_LONG);
        toast.show();
        return 0;
    }
}
