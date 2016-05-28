package com.redoc.yuedu.controller;

import android.content.Context;

import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.utilities.cache.ACacheUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by limen on 2016/5/15.
 */
public class CacheDigestProvider implements DigestsProvider {

    private DigestCacheLatestResponseListener digestCacheLatestResponseListener;
    private DigestCacheMoreResponseListener digestCacheMoreResponseListener;

    public CacheDigestProvider(DigestCacheLatestResponseListener digestCacheLatestResponseListener, DigestCacheMoreResponseListener digestCacheMoreResponseListener) {
        this.digestCacheLatestResponseListener = digestCacheLatestResponseListener;
        this.digestCacheMoreResponseListener = digestCacheMoreResponseListener;
    }

    @Override
    public void fetchLatest(Channel channel, Context context, DigestsAdapter digestsAdapter) {
        String cacheKey = ChannelCache.getInstance().getChannelCacheKey(channel, 0, true);
        String jsonValue = ACacheUtilities.getCacheStr(context, cacheKey);
        if(jsonValue != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonValue);
                digestCacheLatestResponseListener.onResponse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void fetchMore(Channel channel, int index, Context context, DigestsAdapter digestsAdapter) {
        String cacheKey = ChannelCache.getInstance().getChannelCacheKey(channel, index, true);
        String jsonValue = ACacheUtilities.getCacheStr(context, cacheKey);
        JSONTokener jsonParser = new JSONTokener(jsonValue);
        try {
            JSONObject jsonObject = (JSONObject)jsonParser.nextValue();
            digestCacheMoreResponseListener.onResponse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
