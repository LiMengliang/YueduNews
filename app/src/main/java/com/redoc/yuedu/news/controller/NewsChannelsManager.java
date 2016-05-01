package com.redoc.yuedu.news.controller;

import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.controller.ChannelsManager;
import com.redoc.yuedu.controller.DigestsAdapter;
import com.redoc.yuedu.news.bean.AllNewsChannels;
import com.redoc.yuedu.news.bean.NewsChannel;
import com.redoc.yuedu.view.ChannelFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limen on 2016/4/30.
 */
public class NewsChannelsManager extends ChannelsManager {
    private HashMap<String, NewsChannel> allChannels;
    public HashMap<String, NewsChannel> getAllChannels() {
        return allChannels;
    }

    private Map<Channel, DigestsAdapter> channelAndDigestsAdapterMap = new HashMap<Channel, DigestsAdapter>();

    private List<NewsChannel> userSelectedChannels;
    public NewsChannelsManager() {
        userSelectedChannels = new ArrayList<NewsChannel>() {
            {
                add(AllNewsChannels.headLine);
                add(AllNewsChannels.sociaty);
                add(AllNewsChannels.entertainment);
                add(AllNewsChannels.cellphone);
                add(AllNewsChannels.digital);
                add(AllNewsChannels.automobile);
            }
        };
        addUserSelectedChannel(AllNewsChannels.realestate);
    }

    // TODO: Override this to make different channel have different view.
    @Override
    public ChannelFragment getOrCreateFragmentForChannel(Channel channel) {
        return super.getOrCreateFragmentForChannel(channel);
    }

    @Override
    public List<NewsChannel> getUserSelectedChannels() {
        return userSelectedChannels;
    }

    @Override
    public void addUserSelectedChannel(Channel channel) {
        if(channel.getClass() == NewsChannel.class) {
            userSelectedChannels.add((NewsChannel)channel);
        }
    }

    @Override
    public Channel getUserSelectedChannelByPosition(int position) {
        return userSelectedChannels.get(position);
    }

    @Override
    protected DigestsAdapter getSupportDigestsAdapter(Channel channel) {
        // TODO: Different channel may have different digest adapter
        if(!channelAndDigestsAdapterMap.containsKey(channel) && channel.getClass() == NewsChannel.class) {
            DigestsAdapter digestsManager = new NewsDigestsAdapter((NewsChannel)channel);
            channelAndDigestsAdapterMap.put(channel,digestsManager);
        }
        return channelAndDigestsAdapterMap.get(channel);
    }
}
