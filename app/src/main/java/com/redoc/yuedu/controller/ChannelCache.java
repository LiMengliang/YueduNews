package com.redoc.yuedu.controller;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.redoc.yuedu.bean.CacheProgressStatus;
import com.redoc.yuedu.bean.CacheTask;
import com.redoc.yuedu.bean.CacheType;
import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.setting.view.OfflineCacheActivity;
import com.redoc.yuedu.utilities.cache.ACacheUtilities;
import com.redoc.yuedu.utilities.network.VolleyUtilities;
import com.redoc.yuedu.utilities.preference.PreferenceUtilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by limen on 2016/5/15.
 */

public class ChannelCache {

    private static int maxDigestsToCache = 100;
    private static ChannelCache channelCacheInstance;
    private List<Handler> progressHandlers;
    private List<CacheTask> cacheTasks;
    private int currentExecutingTaskIndex;
    private Context context;
    private CacheStatus status = CacheStatus.NotStarted;
    private long lastCacheTime = PreferenceUtilities.getLongValue(CacheProgressStatus.CacheStatusPreference, CacheProgressStatus.LastCacheTimeKey);

    public final static int ProgressMessage = 0x1200;
    public final static String ProgressMessageKey = "CACHE_PROGRESS";

    public static ChannelCache getInstance() {
        if(channelCacheInstance == null) {
            channelCacheInstance = new ChannelCache();
        }
        return channelCacheInstance;
    }

    public CacheStatus getStatus() {
        return status;
    }

    public ChannelCache() {
        progressHandlers = new ArrayList<>();
    }


    public long getLastCacheTime() {
        return lastCacheTime;
    }

    public void AddHandler(Handler handler) {
        progressHandlers.add(handler);
    }

    public String getChannelCacheKey(Channel channel, int index, boolean userCache) {
        if(userCache) {
            return channel.getHttpLink(index) + "ACache";
        }
        return channel.getChannelId();
    }

    public void startCache(List<Channel> channelsToCache, Context context) {
        this.status = CacheStatus.InProgress;
        this.context = context;
        this.currentExecutingTaskIndex = -1;
        this.cacheTasks = generateDigestCacheTasks(channelsToCache);
        executeNextDigestsTask();
    }

    public void pause() {
        status = CacheStatus.Paused;
    }

    public void resume() {
        status = CacheStatus.InProgress;
        executeNextDigestsTask();
    }

    private List<CacheTask> generateDigestCacheTasks(List<Channel> channels) {
        List<CacheTask> tasks = new ArrayList<>();
        for(Channel channel : channels) {
            int tempIndex = 0;
            while(tempIndex < maxDigestsToCache) {
                tasks.add(new CacheTask(channel, CacheType.Digest, tempIndex, maxDigestsToCache));
                tempIndex += 20;
            }
        }
        return tasks;
    }

    private void executeNextDigestsTask() {
        currentExecutingTaskIndex += 1;
        if(cacheTasks.size() <= currentExecutingTaskIndex) {
            return;
        }
        CacheTask cacheTask = cacheTasks.get(currentExecutingTaskIndex);
        Channel channel = cacheTask.getChannel();
        String httpLink = channel.getHttpLink(cacheTask.getCurrentIndex());

        // Send cache status by handlers
        for(Handler handler : progressHandlers) {
            Message message = new Message();
            message.what = ProgressMessage;
            Bundle bundle = new Bundle();
            bundle.putParcelable(ProgressMessageKey, new CacheProgressStatus(
                    cacheTask.getChannel().getChannelName(), cacheTask.getCacheType(),
                    maxDigestsToCache, cacheTask.getCurrentIndex() + 20, status, new Date().getTime()));
            message.setData(bundle);
            handler.sendMessage(message);
        }
        // Queue volley request
        StringRequest stringRequest = new StringRequest(httpLink,
                new DigestResponseListener(cacheTasks, context),
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        VolleyUtilities.RequestQueue.add(stringRequest);
    }

    class DigestResponseListener implements Response.Listener<String> {
        private Context context;
        private List<CacheTask> tasks;
        public DigestResponseListener(List<CacheTask>tasks, Context context) {
            this.tasks = tasks;
            this.context = context;
        }

        @Override
        public void onResponse(String value) {
            CacheTask task = tasks.get(currentExecutingTaskIndex);
            tasks.get(currentExecutingTaskIndex).setExecuted(true);
            String key = getChannelCacheKey(task.getChannel(), task.getCurrentIndex(), true);
            if(value != null && !value.isEmpty()) {
                ACacheUtilities.setCacheStr(context, key, value);
            }
            if(status == CacheStatus.InProgress) {
                executeNextDigestsTask();
            }
            for(CacheTask tempTask : tasks) {
                if(!tempTask.isExecuted()) {
                    return;
                }
            }
            // Finish all tasks, Send cache status by handlers
            for(Handler handler : progressHandlers) {
                Message message = new Message();
                message.what = ProgressMessage;
                Bundle bundle = new Bundle();
                bundle.putParcelable(ProgressMessageKey, new CacheProgressStatus(
                        "", CacheType.Digest, maxDigestsToCache, 0, CacheStatus.NotStarted, new Date().getTime()));
                message.setData(bundle);
                handler.sendMessage(message);
            }
            status = CacheStatus.NotStarted;
        }
    }
}
