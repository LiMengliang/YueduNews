package com.redoc.yuedu.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.redoc.yuedu.R;
import com.redoc.yuedu.YueduApplication;

/**
 * Created by limen on 2016/5/21.
 */
public class CacheProgressStatus implements Parcelable {

    private String channelName;
    private String cacheType;
    private int totalCount;
    private int currentIndex;
    private boolean finished;

    public CacheProgressStatus(String channelName, CacheType cacheType, int totalCount, int currentIndex, boolean finished) {
        this.channelName = channelName;
        this.totalCount = totalCount;
        this.currentIndex = currentIndex;
        this.finished = finished;
        switch (cacheType) {
            case Detail:
                this.cacheType = YueduApplication.Context.getString(R.string.cache_type_detail);
                break;
            case Digest:
                this.cacheType = YueduApplication.Context.getString(R.string.cache_type_digest);
                break;
            case Image:
                this.cacheType = YueduApplication.Context.getString(R.string.cache_type_image);
                break;
        }
    }

    protected CacheProgressStatus(Parcel in) {
        channelName = in.readString();
        cacheType = in.readString();
        totalCount = in.readInt();
        currentIndex =in.readInt();
        finished = in.readByte() == 1;
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

    public String getCacheType() {
        return cacheType;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public boolean getFinished() {
        return finished;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(channelName);
        dest.writeString(cacheType);
        dest.writeInt(totalCount);
        dest.writeInt(currentIndex);
        dest.writeByte((byte)(finished ? 1 : 0));
    }
}
