package com.redoc.yuedu.setting.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcelable;
import android.widget.Toast;

import com.redoc.yuedu.R;
import com.redoc.yuedu.YueduApplication;
import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.controller.CacheStatus;
import com.redoc.yuedu.controller.ChannelLocalCacheWorker;
import com.redoc.yuedu.setting.utilities.OfflineCacheUtils;

import java.util.ArrayList;
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
        if(intent == null) {
            return -1;
        }
        Toast toast = Toast.makeText(this, "Offline Cache Service", Toast.LENGTH_LONG);
        toast.show();

        ArrayList<Channel> cacheableChannels =  YueduApplication.getCategoriesManager().getChacheableChannels();
        ChannelLocalCacheWorker.getInstance().startCache(cacheableChannels, YueduApplication.Context);
        int action = intent.getIntExtra(OfflineCacheUtils.CACHE_ACTION, OfflineCacheUtils.START_CACHE);
        switch (action) {
            case OfflineCacheUtils.START_CACHE:
                ChannelLocalCacheWorker.getInstance().startCache(cacheableChannels,
                        YueduApplication.Context);
                break;
            case OfflineCacheUtils.RESUME_CACHE:
                ChannelLocalCacheWorker.getInstance().resume();
                break;
            case OfflineCacheUtils.PAUSE_CACHE:
                ChannelLocalCacheWorker.getInstance().pause();
                break;
        }
        return 0;
    }
}
