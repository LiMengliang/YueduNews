package com.redoc.yuedu.news.model;

import android.os.Parcel;

import com.redoc.yuedu.model.CacheTask;
import com.redoc.yuedu.model.CacheType;
import com.redoc.yuedu.model.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limen on 2016/4/30.
 */
public class NewsChannel extends Channel {
    protected NewsChannel(Parcel in) {
        super(in);
    }
    public NewsChannel(String channelName, String channelId, String httpLinkFormat, int weight,
                       boolean selected, boolean needCache) {
        super(channelName, channelId, httpLinkFormat, weight, selected, needCache);
    }

    public static final Creator<NewsChannel> CREATOR = new Creator<NewsChannel>() {
        @Override
        public NewsChannel createFromParcel(Parcel in) {
            return new NewsChannel(in);
        }

        @Override
        public NewsChannel[] newArray(int size) {
            return new NewsChannel[size];
        }
    };

    @Override
    public List<CacheTask> detectMoreCacheTaskFromDigest(JSONObject value, CacheTask currentTask) {
        List<CacheTask> moreTasks = new ArrayList<>();
        try {
            List<NewsDigest> newsDigests = NewsDigestsJsonParser.instance.parseJsonToNewsDigestModels(value, (NewsChannel)currentTask.getChannel());
            for(NewsDigest newsDigest : newsDigests) {
                for(String imagePath : newsDigest.getDigestImages()) {
                    CacheTask cacheTask = new CacheTask(imagePath, currentTask.getChannel(), CacheType.Image);
                    moreTasks.add(cacheTask);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return moreTasks;
    }
}
