package com.redoc.yuedu.news.controller;

import android.database.Cursor;

import com.redoc.yuedu.YueduApplication;
import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.contentprovider.ChannelsProvider;
import com.redoc.yuedu.contentprovider.ado.DatabaseUtils;
import com.redoc.yuedu.news.bean.NewsCategory;
import com.redoc.yuedu.news.bean.NewsChannel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limen on 2016/6/19.
 */
public class NewsChannelsProviderUtils {
    /**Channels table:
     * Id integer primary key AutoIncrement"
     * ChannelId varchar(20),
     * ChannelName varchar(20),
     * CategoryId varchar(20),
     * LinkFormat varchar(200),
     * Selected integer,
     * CanCache integer,
     * Weight integer
     */

    public static List<NewsChannel> getNewsChannels() {
        Cursor cursor = YueduApplication.Context.getContentResolver().query(ChannelsProvider.ChannelsUri,
                null, DatabaseUtils.CATEGORY_ID + "=?", new String[]{
                        NewsCategory.NewsCategoryId
                }, "ASC");
        List<NewsChannel> channels = new ArrayList<>();
        while(cursor.moveToNext()) {
            String channelId = cursor.getString(1);
            String channelName = cursor.getString(2);
            String linkFormat = cursor.getString(4);
            boolean selected = cursor.getInt(5) == 1;
            int canCache = cursor.getInt(6);
            int weight = cursor.getInt(7);
            boolean needCache = cursor.getInt(8) == 1;
            channels.add(new NewsChannel(channelName, channelId, linkFormat, weight, selected, needCache));
        }
        cursor.close();
        return channels;
    }

    public static ArrayList<Channel> getNewsCacheableChannels() {
        Cursor cursor = YueduApplication.Context.getContentResolver().query(ChannelsProvider.CacheableChannelsUri,
                null, DatabaseUtils.CATEGORY_ID + "=?",
                new String[] { NewsCategory.NewsCategoryId }, "ASC");
        ArrayList<Channel> channels = new ArrayList<>();
        while(cursor.moveToNext()) {
            String channelId = cursor.getString(1);
            String channelName = cursor.getString(2);
            String linkFormat = cursor.getString(4);
            boolean selected = cursor.getInt(5) == 1;
            int canCache = cursor.getInt(6);
            int weight = cursor.getInt(7);
            boolean needCache = cursor.getInt(8) == 1;
            channels.add(new NewsChannel(channelName, channelId, linkFormat, weight, selected, needCache));
        }
        return channels;
    }

    public static List<Channel> getSelectedChannels() {
        List<Channel> userSelectedChannels = new ArrayList<>();
        for(Channel channel : getNewsChannels()) {
            if(channel.isSelected()) {
                userSelectedChannels.add(channel);
            }
        }
        return userSelectedChannels;
    }
}
