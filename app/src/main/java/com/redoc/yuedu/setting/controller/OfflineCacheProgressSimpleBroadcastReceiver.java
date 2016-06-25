package com.redoc.yuedu.setting.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.redoc.yuedu.bean.CacheProgressStatus;
import com.redoc.yuedu.offlineCache.service.ChannelLocalCacheWorker;
import com.redoc.yuedu.setting.view.UserSettingCategoryFragment;

public class OfflineCacheProgressSimpleBroadcastReceiver extends BroadcastReceiver {
    private UserSettingCategoryFragment userSettingCategoryFragment;
    public OfflineCacheProgressSimpleBroadcastReceiver(UserSettingCategoryFragment userSettingCategoryFragment) {
        this.userSettingCategoryFragment = userSettingCategoryFragment;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        CacheProgressStatus status = intent.getParcelableExtra(ChannelLocalCacheWorker.ProgressMessageKey);
        userSettingCategoryFragment.updateOfflineCacheProgress(status);
    }
}
