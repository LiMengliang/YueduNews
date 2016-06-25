package com.redoc.yuedu.bean;

/**
 * Created by limen on 2016/5/21.
 */
public class CacheTask {
    private CacheType cacheType;
    private Channel channel;
    private boolean executed;
    private String httpLink;

    public CacheTask(String httpLink, Channel channel, CacheType cacheType) {
        this.httpLink = httpLink;
        this.cacheType = cacheType;
        this.channel = channel;
    }

    public CacheType getCacheType() {
        return cacheType;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getHttpLink() {
        return httpLink;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }
}
