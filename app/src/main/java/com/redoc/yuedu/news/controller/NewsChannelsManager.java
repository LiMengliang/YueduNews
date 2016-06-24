package com.redoc.yuedu.news.controller;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;

import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.contentprovider.ChannelProviderUtils;
import com.redoc.yuedu.contentprovider.ado.DatabaseUtils;
import com.redoc.yuedu.controller.ChannelsManager;
import com.redoc.yuedu.controller.DigestsAdapter;
import com.redoc.yuedu.news.bean.NewsChannel;
import com.redoc.yuedu.utilities.preference.PreferenceUtilities;
import com.redoc.yuedu.view.ChannelFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by limen on 2016/4/30.
 */
public class NewsChannelsManager extends ChannelsManager {
    private Context context;
    private List<NewsChannel> allChannels;
    protected List<NewsChannel> getAllChannels() {
        return allChannels;
    }
    private Map<Channel, DigestsAdapter> channelAndDigestsAdapterMap = new HashMap<>();
    private List<NewsChannel> userSelectedChannels;

    public NewsChannelsManager(Context context) {
        this.context = context;
        allChannels = NewsChannelsProviderUtils.getNewsChannels();
        userSelectedChannels = new ArrayList<>();
        for(NewsChannel channel : allChannels) {
            if(channel.isSelected()) {
                userSelectedChannels.add(channel);
            }
        }
    }

    // TODO: Override this to make different channel have different view.
    @Override
    public ChannelFragment getOrCreateFragmentForChannel(Channel channel) {
        return super.getOrCreateFragmentForChannel(channel);
    }

    @Override
    protected List<NewsChannel> getUserSelectedChannels() {
        return userSelectedChannels;
    }

    @Override
    public void addUserSelectedChannel(Channel channel) {
        if(channel.getClass() == NewsChannel.class) {
            userSelectedChannels.add((NewsChannel)channel);
            channel.setSelected(true);
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseUtils.SELECTED, 1);
        ChannelProviderUtils.updateChannel(contentValues, channel.getChannelId());
    }

    @Override
    public void removeUserSelectedChannel(Channel channel) {
        if(channel.getClass() == NewsChannel.class) {
            userSelectedChannels.remove(channel);
            channel.setSelected(false);
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseUtils.SELECTED, 0);
        ChannelProviderUtils.updateChannel(contentValues, channel.getChannelId());
    }

    @Override
    public Channel getUserSelectedChannelByPosition(int position) {
        List<? extends Channel> sortedChannels = getSortedUserSelectedChannels();
        if(sortedChannels == null || sortedChannels.size() == 0) {
            return null;
        }
        return sortedChannels.get(position);
    }

    @Override
    protected DigestsAdapter getSupportDigestsAdapter(Channel channel) {
        if(channel == null) {
            return null;
        }
        // TODO: Different channel may have different digest adapter
        if(!channelAndDigestsAdapterMap.containsKey(channel) && channel.getClass() == NewsChannel.class) {
            DigestsAdapter digestsAdapter = new NewsDigestsAdapter(context, (NewsChannel)channel);
            channelAndDigestsAdapterMap.put(channel,digestsAdapter);
        }
        return channelAndDigestsAdapterMap.get(channel);
    }

    private Set<String> getSelectedChannelsId() {
        Set<String> ids = new HashSet<>();
        for(Channel channel : userSelectedChannels) {
            ids.add(channel.getChannelId());
        }
        return ids;
    }
}
