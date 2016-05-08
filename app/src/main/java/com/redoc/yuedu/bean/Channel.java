package com.redoc.yuedu.bean;

/**
 * Created by limen on 2016/4/30.
 */
public class Channel implements Comparable {
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
}
