package com.redoc.yuedu.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.redoc.yuedu.YueduApplication;
import com.redoc.yuedu.bean.CacheProgressStatus;
import com.redoc.yuedu.bean.CacheTask;
import com.redoc.yuedu.bean.CacheType;
import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.utilities.cache.ACacheUtilities;
import com.redoc.yuedu.utilities.network.LoadImageUtilities;
import com.redoc.yuedu.utilities.network.VolleyUtilities;
import com.redoc.yuedu.utilities.preference.PreferenceUtilities;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by limen on 2016/5/15.
 */

// TODO: Seperate into background worker and progress notifier
public class ChannelLocalCacheWorker {
    private static int maxDigestsToCache = 100;
    private static ChannelLocalCacheWorker channelCacheInstance;
    private List<Handler> progressHandlers;
    private List<CacheTask> cacheTasks;
    private int currentExecutingTaskIndex;
    private Context context;
    private CacheStatus status = CacheStatus.NotStarted;
    private long lastCacheTime = PreferenceUtilities.getLongValue(CacheProgressStatus.CacheStatusPreference, CacheProgressStatus.LastCacheTimeKey);
    private CacheProgressStatus cacheProgressStatus = null;

    public final static int ProgressMessage = 0x1200;
    public final static String ProgressMessageKey = "CACHE_PROGRESS";

    public static ChannelLocalCacheWorker getInstance() {
        if(channelCacheInstance == null) {
            channelCacheInstance = new ChannelLocalCacheWorker();
        }
        return channelCacheInstance;
    }

    public CacheStatus getStatus() {
        return status;
    }

    public ChannelLocalCacheWorker() {
        cacheProgressStatus = CacheProgressStatus.getFromPreferences();
        progressHandlers = new ArrayList<>();
    }

    public CacheProgressStatus getCacheProgressStatus() {
        return cacheProgressStatus;
    }

    public long getLastCacheTime() {
        return lastCacheTime;
    }

    public static String getChannelCacheKey(String httpLink, boolean userCache) {
        if(userCache) {
            return httpLink + "ACache";
        }
        return httpLink;
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
        cacheProgressStatus.writeToPreferences();
    }

    public void resume() {
        status = CacheStatus.InProgress;
        executeNextDigestsTask();
    }

    private List<CacheTask> generateDigestCacheTasks(List<Channel> channels) {
        List<CacheTask> tasks = new ArrayList<>();
        for(Channel channel : channels) {
            if(channel.isNeedCache()) {
                int tempIndex = 0;
                while(tempIndex < maxDigestsToCache) {
                    String httpLink = channel.getHttpLink(tempIndex);
                    tasks.add(new CacheTask(httpLink, channel, CacheType.Digest));
                    tempIndex += 20;
                }
            }
        }
        return tasks;
    }

    private void notifyIfAllTasksAreExecuted() {
        for(CacheTask tempTask : cacheTasks) {
            if(!tempTask.isExecuted()) {
                return;
            }
        }
        cacheProgressStatus = new CacheProgressStatus("", CacheType.Digest, maxDigestsToCache, 0,
                CacheStatus.NotStarted, new Date().getTime());
        cacheProgressStatus.writeToPreferences();
        // Finish all tasks, Send cache status by handlers
        for(Handler handler : progressHandlers) {
            Message message = new Message();
            message.what = ProgressMessage;
            Bundle bundle = new Bundle();
            bundle.putParcelable(ProgressMessageKey, cacheProgressStatus);
            message.setData(bundle);
            handler.sendMessage(message);
        }
        status = CacheStatus.NotStarted;
    }

    private IndexAndAllCounts getIndexOfDigestImageTask(CacheTask cacheTask) {
        List<CacheTask> tasks = new ArrayList<CacheTask>();
        for(CacheTask task : cacheTasks) {
            if(task.getCacheType() == cacheTask.getCacheType() && task.getChannel() == cacheTask.getChannel()) {
                tasks.add(task);
            }
        }
        return new IndexAndAllCounts(tasks.indexOf(cacheTask), tasks.size());
    }

    private void executeNextDigestsTask() {
        currentExecutingTaskIndex += 1;
        int totalCount = 0;
        int index = 0;
        if(cacheTasks.size() <= currentExecutingTaskIndex) {
            return;
        }
        CacheTask cacheTask = cacheTasks.get(currentExecutingTaskIndex);
        switch (cacheTask.getCacheType())
        {
            case Digest:
                // Queue volley request
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(cacheTask.getHttpLink(), null,
                        new DigestResponseListener(cacheTasks, context),
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                VolleyUtilities.RequestQueue.add(jsonObjectRequest);
                break;
            case Image:
                LoadImageUtilities.loadBitmapFromUriAsync(cacheTask.getHttpLink(), new CacheImageLoadListener(cacheTask, context));
                IndexAndAllCounts indexAndAllCounts = getIndexOfDigestImageTask(cacheTask);
                totalCount = indexAndAllCounts.allCount;
                index = indexAndAllCounts.index;
                break;
            case Detail:
                break;
        }
        cacheProgressStatus = new CacheProgressStatus(cacheTask.getChannel().getChannelName(), cacheTask.getCacheType(),
                totalCount, index, status, new Date().getTime());
        cacheProgressStatus.writeToPreferences();

        Intent intent = new Intent();
        intent.setAction("com.redoc.yuedu.CACHE_PROGRESS_UPDATED");
        intent.putExtra(ProgressMessageKey, cacheProgressStatus);
        YueduApplication.Context.sendBroadcast(intent);
    }

    class DigestResponseListener implements Response.Listener<JSONObject> {
        private Context context;
        private List<CacheTask> tasks;
        public DigestResponseListener(List<CacheTask>tasks, Context context) {
            this.tasks = tasks;
            this.context = context;
        }

        @Override
        public void onResponse(JSONObject value) {
            CacheTask task = tasks.get(currentExecutingTaskIndex);
            String key = getChannelCacheKey(task.getHttpLink(), true);
            String stringValue = value.toString();
            if(stringValue != null && !stringValue.isEmpty()) {
                ACacheUtilities.setCacheStr(context, key, stringValue);
            }
            task.setExecuted(true);
            // Add more task to tasks
            Channel channel = task.getChannel();
            tasks.addAll(channel.detectMoreCacheTaskFromDigest(value, task));

            if(status == CacheStatus.InProgress) {
                executeNextDigestsTask();
            }
            notifyIfAllTasksAreExecuted();
        }
    }

    class CacheImageLoadListener implements ImageLoadingListener {
        private CacheTask task;
        private Context context;
        public CacheImageLoadListener(CacheTask task, Context context) {
            this.task = task;
            this.context = context;
        }
        @Override
        public void onLoadingStarted(String s, View view) {

        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {
        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            ACacheUtilities.setCacheImage(context, getChannelCacheKey(task.getHttpLink(), true), bitmap);
            task.setExecuted(true);
            if(status == CacheStatus.InProgress) {
                executeNextDigestsTask();
            }
            notifyIfAllTasksAreExecuted();
        }

        @Override
        public void onLoadingCancelled(String s, View view) {

        }
    }

    class IndexAndAllCounts {
        public int index;
        public int allCount;
        public IndexAndAllCounts(int index, int allCount) {
            this.index = index;
            this.allCount = allCount;
        }
    }
}
