package com.redoc.yuedu.bean;

/**
 * Created by limen on 2016/4/30.
 */
public class Channel {
    private String channelId;
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

    public Channel(String channelName, String channelId, String httpLinkFormat) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.httpLinkFormat = httpLinkFormat;
    }
}
