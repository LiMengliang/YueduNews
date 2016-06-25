package com.redoc.yuedu.setting.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.redoc.yuedu.bean.CacheProgressStatus;
import com.redoc.yuedu.controller.ChannelLocalCacheWorker;
import com.redoc.yuedu.setting.view.OfflineCacheActivity;

public class OfflineCacheProgressBroadcastReceiver extends BroadcastReceiver {
    private OfflineCacheActivity offlineCacheActivity;
    public OfflineCacheProgressBroadcastReceiver(OfflineCacheActivity offlineCacheActivity) {
        this.offlineCacheActivity = offlineCacheActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        CacheProgressStatus status = intent.getParcelableExtra(ChannelLocalCacheWorker.ProgressMessageKey);
        offlineCacheActivity.updateCacheProgressToView(status);
        Toast toast = Toast.makeText(context, "Update progress", Toast.LENGTH_LONG);
        toast.show();
    }
}
