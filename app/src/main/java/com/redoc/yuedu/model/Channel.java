package com.redoc.yuedu.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limen on 2016/4/30.
 */
public class Channel implements Comparable, Parcelable {
    private String channelId;

    protected Channel(Parcel in) {
        channelId = in.readString();
        channelName = in.readString();
        httpLinkFormat = in.readString();
        selected = in.readByte() != 0;
        weight = in.readInt();
        needCache = in.readByte() != 0;
    }

    public static final Creator<Channel> CREATOR = new Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel in) {
            return new Channel(in);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };

    public String getChannelId() {
        return channelId;
    }

    private String channelName;
    public String getChannelName() {
        return channelName;
    }

    private String httpLinkFormat;
    public String getHttpLinkFormat() {
        return httpLinkFormat;
    }
    public String getHttpLink(int index) {
        return String.format(httpLinkFormat, index);
    }

    private boolean selected;
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private boolean needCache;
    public boolean isNeedCache() {
        return needCache;
    }
    public void setNeedCache(boolean needCache) {
        this.needCache = needCache;
    }

    private int weight;

    public Channel(String channelName, String channelId, String httpLinkFormat, int weight,
                   boolean selected, boolean needCache) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.httpLinkFormat = httpLinkFormat;
        this.selected = selected;
        this.weight = weight;
        this.needCache = needCache;
    }
    public List<CacheTask> detectMoreCacheTaskFromDigest(JSONObject value, CacheTask currentTask) {
        return new ArrayList<>();
    }

    @Override
    public int compareTo(Object another) {
        return weight - ((Channel) another).weight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(channelId);
        dest.writeString(channelName);
        dest.writeString(httpLinkFormat);
        dest.writeByte((byte)(selected ? 1 : 0));
        dest.writeInt(weight);
        dest.writeByte((byte)(needCache ? 1 : 0));
    }
}
