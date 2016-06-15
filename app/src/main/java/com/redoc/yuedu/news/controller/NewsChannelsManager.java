package com.redoc.yuedu.news.controller;

import android.content.Context;

import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.controller.ChannelsManager;
import com.redoc.yuedu.controller.DigestsAdapter;
import com.redoc.yuedu.news.bean.AllNewsChannels;
import com.redoc.yuedu.news.bean.NewsChannel;
import com.redoc.yuedu.utilities.preference.PreferenceUtilities;
import com.redoc.yuedu.view.ChannelFragment;

import java.util.ArrayList;
import java.util.Collection;
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

    public static String SelectedChannelsPreferenceKey = "SelectedChannelsKey";
    public static String SelectedChannelsPreferenceFileName = "SelectedChannelsPreferenceFile";

    private Map<Channel, DigestsAdapter> channelAndDigestsAdapterMap = new HashMap<>();

    private List<NewsChannel> userSelectedChannels;
    public NewsChannelsManager(Context context) {
        this.context = context;
        allChannels = new ArrayList<NewsChannel>() {
            {
                add(AllNewsChannels.headLine);
                add(AllNewsChannels.entertainment);
                add(AllNewsChannels.finance);
                add(AllNewsChannels.tech);
                add(AllNewsChannels.cba);
                add(AllNewsChannels.joke);
                add(AllNewsChannels.automobile);
                add(AllNewsChannels.fasion);
                add(AllNewsChannels.beijin);
                add(AllNewsChannels.war);
                add(AllNewsChannels.realestate);
                add(AllNewsChannels.eGame);
                add(AllNewsChannels.pickOut);
                add(AllNewsChannels.emotion);
                add(AllNewsChannels.movie);
                add(AllNewsChannels.nba);
                add(AllNewsChannels.digital);
                add(AllNewsChannels.mobile);
                add(AllNewsChannels.education);
                add(AllNewsChannels.bbs);
                add(AllNewsChannels.tourism);
                add(AllNewsChannels.cellphone);
                add(AllNewsChannels.sociaty);
            }
        };
        userSelectedChannels = new ArrayList<>();
        if(PreferenceUtilities.containsKey(SelectedChannelsPreferenceFileName, SelectedChannelsPreferenceKey)) {
            Set<String> selectedChannelsId = PreferenceUtilities.getStringSetValue(SelectedChannelsPreferenceFileName, SelectedChannelsPreferenceKey);
            for(NewsChannel channel : allChannels) {
                for(String id : selectedChannelsId) {
                    if(id.equals(channel.getChannelId())) {
                        addUserSelectedChannel(channel);
                        break;
                    }
                }
            }
        }
        else {
            addUserSelectedChannel(AllNewsChannels.headLine);
            addUserSelectedChannel(AllNewsChannels.sociaty);
            addUserSelectedChannel(AllNewsChannels.entertainment);
            addUserSelectedChannel(AllNewsChannels.cellphone);
            addUserSelectedChannel(AllNewsChannels.digital);
            addUserSelectedChannel(AllNewsChannels.automobile);
            addUserSelectedChannel(AllNewsChannels.realestate);
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
        PreferenceUtilities.writeToPreference(SelectedChannelsPreferenceFileName, SelectedChannelsPreferenceKey, getSelectedChannelsId());
    }

    @Override
    public void removeUserSelectedChannel(Channel channel) {
        if(channel.getClass() == NewsChannel.class) {
            userSelectedChannels.remove(channel);
            channel.setSelected(false);
        }
        PreferenceUtilities.writeToPreference(SelectedChannelsPreferenceFileName, SelectedChannelsPreferenceKey, getSelectedChannelsId());
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
