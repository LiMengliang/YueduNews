package com.redoc.yuedu.bean;

/**
 * Created by limen on 2016/5/21.
 */
public class CacheTask {
    private CacheType cacheType;
    private int totalCount;
    private int currentIndex;
    private Channel channel;
    private boolean executed;

    public CacheType getCacheType() {
        return cacheType;
    }

    public void setCacheType(CacheType cacheType) {
        this.cacheType = cacheType;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setCurrentChannel(Channel channel) {
        this.channel = channel;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public CacheTask(Channel channel, CacheType cacheType, int currentIndex, int totalCount) {
        this.cacheType = cacheType;
        this.channel = channel;
        this.currentIndex = currentIndex;
        this.totalCount = totalCount;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }
}
