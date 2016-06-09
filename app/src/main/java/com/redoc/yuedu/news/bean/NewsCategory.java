package com.redoc.yuedu.news.bean;

import com.redoc.yuedu.bean.CacheableCategory;
import com.redoc.yuedu.bean.CacheableChannel;
import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.bean.MultiChannelCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limen on 2016/5/13.
 */
public class NewsCategory extends MultiChannelCategory implements CacheableCategory {
    public NewsCategory(String categoryName, int categoryIconResoruceId) {
        super(categoryName, categoryIconResoruceId);
    }

    @Override
    public ArrayList<CacheableChannel> getChannelCacheInfo() {
        ArrayList<CacheableChannel> channels = new ArrayList<>();
        for(Channel channel : AllNewsChannels.getAllChannels()) {
            if(CacheableChannel.class.isInstance(channel)) {
                channels.add((CacheableChannel)channel);
            }
        }
        return channels;
    }
}
