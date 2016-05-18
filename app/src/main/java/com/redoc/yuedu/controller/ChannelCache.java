package com.redoc.yuedu.controller;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.bean.Digest;
import com.redoc.yuedu.setting.view.OfflineCacheActivity;
import com.redoc.yuedu.utilities.cache.ACacheUtilities;
import com.redoc.yuedu.utilities.network.VolleyUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limen on 2016/5/15.
 */
public class ChannelCache {
    private static int maxDigestsToCache = 40;
    private static ChannelCache channelCacheInstance;
    private Map<String, List<DigestsAndSectionIndex>> cacheAndChannelIdMap = new HashMap<String, List<DigestsAndSectionIndex>>();
    private int cachedChannels = 0;

    public static ChannelCache getInstance() {
        if(channelCacheInstance == null) {
            channelCacheInstance = new ChannelCache();
        }
        return channelCacheInstance;
    }


    public String getChannelCacheKey(Channel channel, boolean userCache) {
        if(userCache) {
            return channel.getChannelId() + "ACache";
        }
        return channel.getChannelId();
    }

    public void CacheTopDigests(List<Channel> channelsToCache, Context context) {
        cachedChannels = 0;
        cacheAndChannelIdMap.clear();
        int tempIndex = 0;
        for(Channel channel : channelsToCache) {
            tempIndex = 0;
            while(tempIndex < maxDigestsToCache) {
                String httpLink = channel.getHttpLink(tempIndex);
                StringRequest stringRequest = new StringRequest(httpLink,
                        new DigestResponseListener(channel, context, tempIndex, tempIndex >= maxDigestsToCache - 20, channelsToCache.size()),
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                VolleyUtilities.RequestQueue.add(stringRequest);
                tempIndex += 20;
            }
        }
    }

    class DigestResponseListener implements Response.Listener<String> {
        private Context context;
        private Channel channel;
        private int index;
        private boolean endOfChannel;
        private int channelsNeedToCache;
        public DigestResponseListener(Channel channel, Context context, int index, boolean endOfChannel, int channelsNeedToCache) {
            this.channel = channel;
            this.context = context;
            this.index = index;
            this.endOfChannel = endOfChannel;
            this.channelsNeedToCache = channelsNeedToCache;
        }

        @Override
        public void onResponse(String value) {
            String key = getChannelCacheKey(channel, true);
            if(value != null && !value.isEmpty()) {
                if(cacheAndChannelIdMap.containsKey(key)) {
                    List<DigestsAndSectionIndex> cache = cacheAndChannelIdMap.get(key);
                    try {
                        cache.add(new DigestsAndSectionIndex(new JSONObject(value), index));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    List<DigestsAndSectionIndex> cacheList = new ArrayList<>();
                    try {
                        cacheList.add(new DigestsAndSectionIndex(new JSONObject(value), index));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cacheAndChannelIdMap.put(key, cacheList);
                }
            }
            // TODO: need better way to decide if we got all the news required
            if(cacheAndChannelIdMap.get(key).size() == maxDigestsToCache/20) {
                Collections.sort(cacheAndChannelIdMap.get(key), new Comparator<DigestsAndSectionIndex>() {
                    @Override
                    public int compare(DigestsAndSectionIndex lhs, DigestsAndSectionIndex rhs) {
                        return lhs.index - rhs.index;
                    }
                });
                List<JSONObject> sortedJSONObject = new ArrayList<>();
                for(DigestsAndSectionIndex item : cacheAndChannelIdMap.get(key)) {
                    sortedJSONObject.add(item.digestJSONObject);
                }
                ACacheUtilities.setCacheStr(context, key, sortedJSONObject.toString());
                cachedChannels++;
                ((OfflineCacheActivity)context).onCachedOneChannel(Integer.toString(cachedChannels));
                if(cachedChannels == channelsNeedToCache) {
                    ((OfflineCacheActivity)context).onCacheFinished();
                }
            }
        }
    }

    class DigestsAndSectionIndex{
        private JSONObject digestJSONObject;
        private int index;
        public DigestsAndSectionIndex(JSONObject jsonObject, int index) {
            digestJSONObject = jsonObject;
            this.index = index;
        }
    }
}
