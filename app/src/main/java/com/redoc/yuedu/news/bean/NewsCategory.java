package com.redoc.yuedu.news.bean;

import com.redoc.yuedu.bean.CacheableCategory;
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
    public ArrayList<Channel> getChannelCacheInfo() {
        ArrayList<Channel> channels = new ArrayList<Channel>();
        channels.addAll(AllNewsChannels.getAllChannels());
        return channels;
    }
}
