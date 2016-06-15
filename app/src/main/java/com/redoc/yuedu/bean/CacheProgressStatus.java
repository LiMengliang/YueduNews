package com.redoc.yuedu.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.redoc.yuedu.controller.CacheStatus;
import com.redoc.yuedu.utilities.preference.PreferenceUtilities;

/**
 * Created by limen on 2016/5/21.
 */
public class CacheProgressStatus implements Parcelable {

    private String channelName;
    private CacheType cacheType;
    private int totalCount;
    private int currentIndex;
    private CacheStatus cacheStatus;
    private long lastCacheTime;

    public final static String CacheStatusPreference = "CacheStatusPreference";
    public final static String LastCacheTimeKey = "LastCacheTimeKey";
    public final static String CacheStatusKey = "CacheStatusKey";
    public final static String CurrentCacheChannelKey = "CurrentCacheChannelKey";
    public final static String CacheTypeKey = "CacheTypeKey";
    public final static String CurrentCacheIndexKey = "CurrentCacheIndexKey";
    public final static String TotalCacheCountKey = "TotalCacheCountKey";

    public CacheProgressStatus(String channelName, CacheType cacheType, int totalCount, int currentIndex, CacheStatus cacheStatus, long lastCacheTime) {
        this.channelName = channelName;
        this.totalCount = totalCount;
        this.currentIndex = currentIndex;
        this.cacheStatus = cacheStatus;
        this.cacheType = cacheType;
        this.lastCacheTime = lastCacheTime;
    }

    public static CacheProgressStatus getFromPreferences() {
        return new CacheProgressStatus(
                PreferenceUtilities.getStringValue(CacheStatusPreference, CurrentCacheChannelKey),
                CacheType.values()[PreferenceUtilities.getIntValue(CacheStatusPreference, CacheTypeKey)],
                PreferenceUtilities.getIntValue(CacheStatusPreference, TotalCacheCountKey),
                PreferenceUtilities.getIntValue(CacheStatusPreference, CurrentCacheIndexKey),
                CacheStatus.values()[PreferenceUtilities.getIntValue(CacheStatusPreference, CacheStatusKey)],
                PreferenceUtilities.getLongValue(CacheStatusPreference, LastCacheTimeKey));
    }

    protected CacheProgressStatus(Parcel in) {
        channelName = in.readString();
        cacheType = CacheType.values()[in.readInt()];
        totalCount = in.readInt();
        currentIndex =in.readInt();
        cacheStatus = CacheStatus.values()[in.readInt()];
        lastCacheTime = in.readLong();
    }

    public static final Creator<CacheProgressStatus> CREATOR = new Creator<CacheProgressStatus>() {
        @Override
        public CacheProgressStatus createFromParcel(Parcel source) {
            return new CacheProgressStatus(source);
        }

        @Override
        public CacheProgressStatus[] newArray(int size) {
            return new CacheProgressStatus[0];
        }
    };

    public String getChannelName() {
        return channelName;
    }

    public CacheType getCacheType() {
        return cacheType;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public CacheStatus getCacheStatus() {
        return cacheStatus;
    }

    public long getLastCacheTime() {
        return lastCacheTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(channelName);
        dest.writeInt(cacheType.ordinal());
        dest.writeInt(totalCount);
        dest.writeInt(currentIndex);
        dest.writeInt(cacheStatus.ordinal());
        dest.writeLong(lastCacheTime);
    }

    public void writeToPreferences() {
        PreferenceUtilities.writeToPreference(CacheStatusPreference, CacheStatusKey, cacheStatus.ordinal());
        PreferenceUtilities.writeToPreference(CacheStatusPreference, CurrentCacheChannelKey, channelName);
        PreferenceUtilities.writeToPreference(CacheStatusPreference, CurrentCacheIndexKey, currentIndex);
        PreferenceUtilities.writeToPreference(CacheStatusPreference, TotalCacheCountKey, totalCount);
        PreferenceUtilities.writeToPreference(CacheStatusPreference, CacheTypeKey, cacheType.ordinal());
        PreferenceUtilities.writeToPreference(CacheStatusPreference, LastCacheTimeKey, lastCacheTime);
    }
}
