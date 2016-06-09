package com.redoc.yuedu.bean;

/**
 * Created by limen on 2016/5/21.
 */
public class CacheTask {
    private CacheType cacheType;
    private int totalCount;
    private CacheableChannel channel;
    private boolean executed;
    private String httpLink;

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

    public CacheableChannel getChannel() {
        return channel;
    }

    public String getHttpLink() {
        return httpLink;
    }

    public void setCurrentChannel(CacheableChannel channel) {
        this.channel = channel;
    }

    public CacheTask(String httpLink, CacheableChannel channel, CacheType cacheType) {
        this.httpLink = httpLink;
        this.cacheType = cacheType;
        this.channel = channel;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }
}
