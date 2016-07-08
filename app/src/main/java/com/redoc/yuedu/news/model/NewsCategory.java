package com.redoc.yuedu.news.model;

import com.redoc.yuedu.model.CacheableCategory;
import com.redoc.yuedu.model.Channel;
import com.redoc.yuedu.model.MultiChannelCategory;
import com.redoc.yuedu.news.presenter.NewsChannelsProviderUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limen on 2016/5/13.
 */
public class NewsCategory extends MultiChannelCategory implements CacheableCategory {
    public static String NewsCategoryId = "NEWSCATEGORY";

    public NewsCategory(String categoryName, int categoryIconResoruceId) {
        super(categoryName, categoryIconResoruceId);
    }

    @Override
    public List<String> getChannelsCreationSQL() {
        return new ArrayList<String>(){
            {
                add("insert into channels (Id, ChannelId, ChannelName, CategoryId, LinkFormat, " +
                        "Selected, CanCache, Weight, NeedCache) values (1, \"T1348647909107\", \"头条\", \"NEWSCATEGORY\"," +
                        " \"http://c.m.163.com/nc/article/headline/T1348647909107/%d-20.html\", 1, 1, 1, 0)");
                add("insert into channels (Id, ChannelId, ChannelName, CategoryId, LinkFormat, " +
                        "Selected, CanCache, Weight, NeedCache) values (2, \"T1348648517839\", \"娱乐\", \"NEWSCATEGORY\"," +
                        " \"http://c.m.163.com/nc/article/list/T1348648517839/%d-20.html\", 1, 1, 2, 0)");
                add("insert into channels (Id, ChannelId, ChannelName, CategoryId, LinkFormat, " +
                        "Selected, CanCache, Weight, NeedCache) values (3, \"T1349837698345\", \"社会\", \"NEWSCATEGORY\"," +
                        " \"http://c.m.163.com/nc/article/list/T1349837698345/%d-20.html\", 1, 1, 3, 0)");
                add("insert into channels (Id, ChannelId, ChannelName, CategoryId, LinkFormat, " +
                        "Selected, CanCache, Weight, NeedCache) values (4, \"T1348648756099\", \"财经\", \"NEWSCATEGORY\"," +
                        " \"http://c.m.163.com/nc/article/list/T1348648756099/%d-20.html\", 1, 0, 4, 0)");
            }
        };
    }

    // @Override
    // public void initializeDefaultChannelsInfo() {
    //     int index = 0;
    //     for(NewsChannel channel : AllNewsChannels.getAllChannels()) {
    //         ContentValues contentValues = new ContentValues();
    //         contentValues.put(DatabaseUtils.ID, index);
    //         contentValues.put(DatabaseUtils.CHANNEL_ID, channel.getChannelId());
    //         contentValues.put(DatabaseUtils.CHANNEL_NAME, channel.getChannelName());
    //         contentValues.put(DatabaseUtils.CATEGORY_ID, NewsCategory.NewsCategoryId);
    //         contentValues.put(DatabaseUtils.LINK_FORMAT, channel.getHttpLinkFormat());
    //         contentValues.put(DatabaseUtils.SELECTED, channel.isSelected());
    //         contentValues.put(DatabaseUtils.CAN_CACHE, 1);
    //         contentValues.put(DatabaseUtils.WEIGHT, index);
    //         index++;
    //         ChannelProviderUtils.insertChannel(contentValues);
    //     }
    // }

    @Override
    public ArrayList<Channel> getChannelCacheInfo() {
        return NewsChannelsProviderUtils.getNewsCacheableChannels();
    }
}
