package com.redoc.yuedu.bean;

import android.os.Parcel;
import android.os.Parcelable;

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

    private int weight;
    public int getWeight() {
        return weight;
    }

    public Channel(String channelName, String channelId, String httpLinkFormat, int weight) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.httpLinkFormat = httpLinkFormat;
        this.selected = false;
        this.weight = weight;
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
        dest.writeByte((byte) (selected ? 1 : 0));
        dest.writeInt(weight);
    }
}
