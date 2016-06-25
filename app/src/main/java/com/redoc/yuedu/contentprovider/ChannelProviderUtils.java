package com.redoc.yuedu.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.redoc.yuedu.YueduApplication;
import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.contentprovider.ado.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limen on 2016/6/19.
 */
public class ChannelProviderUtils {
    /**Channels table:
     * Id integer primary key AutoIncrement"
     * ChannelId varchar(20),
     * ChannelName varchar(20),
     * CategoryId varchar(20),
     * LinkFormat varchar(200),
     * Selected integer,
     * CanCache integer,
     * Weight integer,
     * NeedCache integer
     */
    public static List<Channel> getAllChannels() {
        Cursor cursor = YueduApplication.Context.getContentResolver().query(ChannelsProvider.ChannelsUri,
                null, null, null, "ASC");
        List<Channel> channels = new ArrayList<>();
        while(cursor.moveToNext()) {
            String channelId = cursor.getString(1);
            String channelName = cursor.getString(2);
            String linkFormat = cursor.getString(4);
            boolean selected = cursor.getInt(5) == 1;
            int canCache = cursor.getInt(6);
            int weight = cursor.getInt(7);
            boolean needCache = cursor.getInt(8) == 1;
            channels.add(new Channel(channelName, channelId, linkFormat, weight, selected, needCache));
        }
        cursor.close();
        return channels;
    }

    public static int updateChannel(ContentValues contentValues, String channelId) {
        int count = YueduApplication.Context.getContentResolver().update(ChannelsProvider.ChannelsUri,
                contentValues, DatabaseUtils.CHANNEL_ID + " = ?", new String[]{channelId});
        return count;
    }

    public static Uri insertChannel(ContentValues contentValues) {
        return YueduApplication.Context.getContentResolver().insert(ChannelsProvider.ChannelsUri, contentValues);
    }
}
