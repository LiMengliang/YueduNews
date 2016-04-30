package com.redoc.yuedu.news.bean;

import com.redoc.yuedu.bean.Channel;

/**
 * Created by limen on 2016/4/30.
 */
public class NewsChannel extends Channel {
    public NewsChannel(String channelName, String channelId, String httpLinkFormat) {
        super(channelName, channelId, httpLinkFormat);
    }
}
