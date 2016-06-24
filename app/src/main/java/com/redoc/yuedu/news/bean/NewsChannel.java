package com.redoc.yuedu.news.bean;

import android.os.Parcel;

import com.redoc.yuedu.bean.CacheTask;
import com.redoc.yuedu.bean.CacheType;
import com.redoc.yuedu.bean.CacheableChannel;
import com.redoc.yuedu.bean.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limen on 2016/4/30.
 */
public class NewsChannel extends CacheableChannel {
    protected NewsChannel(Parcel in) {
        super(in);
    }
    public NewsChannel(String channelName, String channelId, String httpLinkFormat, int weight, boolean selected) {
        super(channelName, channelId, httpLinkFormat, weight, selected);
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
