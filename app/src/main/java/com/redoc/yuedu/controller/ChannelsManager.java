package com.redoc.yuedu.controller;

import android.support.v4.app.Fragment;

import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.view.ChannelFragment;
import com.redoc.yuedu.view.DigestsChannelFragment;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limen on 2016/4/30.
 */
public abstract class ChannelsManager {
    private Map<Channel, ChannelFragment> channelFragmentMap = new HashMap<Channel, ChannelFragment>();
    public ChannelFragment getOrCreateFragmentForChannel(Channel channel) {
        if(channel == null) {
            return null;
        }
        if(channelFragmentMap.containsKey(channel)) {
            return channelFragmentMap.get(channel);
        }
        else {
            ChannelFragment fragment = DigestsChannelFragment.newInstance(getSupportDigestsAdapter(channel));
            channelFragmentMap.put(channel, fragment);
            return fragment;
        }
    }

    protected abstract List<? extends Channel> getAllChannels();
    protected abstract DigestsAdapter getSupportDigestsAdapter(Channel channel);
    protected abstract List<? extends Channel> getUserSelectedChannels();

    public List<? extends Channel> getSortedUserSelectedChannels() {
        List<? extends Channel> userSelectedChannels = getUserSelectedChannels();
        Collections.sort(userSelectedChannels);
        return userSelectedChannels;
    }

    public List<? extends Channel> getSortedAllChannels() {
        List<? extends Channel> allChannels = getAllChannels();
        Collections.sort(allChannels);
        return allChannels;
    }

    public abstract void addUserSelectedChannel(Channel channel);

    public abstract void removeUserSelectedChannel(Channel channel);

    public abstract Channel getUserSelectedChannelByPosition(int position);
}
