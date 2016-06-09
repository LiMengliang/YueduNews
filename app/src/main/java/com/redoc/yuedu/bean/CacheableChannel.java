package com.redoc.yuedu.bean;

import android.os.Parcel;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by limen on 2016/6/4.
 */
public abstract class CacheableChannel extends Channel {

    protected CacheableChannel(Parcel in) {
        super(in);
    }
    protected CacheableChannel(String channelName, String channelId, String httpLinkFormat, int weight) {
        super(channelName, channelId, httpLinkFormat, weight);
    }

    public abstract List<CacheTask> detectMoreCacheTaskFromDigest(JSONObject value, CacheTask currentTask);
}
