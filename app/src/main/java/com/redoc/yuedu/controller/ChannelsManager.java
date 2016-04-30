package com.redoc.yuedu.controller;

import android.support.v4.app.Fragment;

import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.view.ChannelFragment;
import com.redoc.yuedu.view.DigestsChannelFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limen on 2016/4/30.
 */
public abstract class ChannelsManager {
    private Map<Channel, ChannelFragment> channelFragmentMap = new HashMap<Channel, ChannelFragment>();
    public ChannelFragment getOrCreateFragmentForChannel(Channel channel) {
        if(channelFragmentMap.containsKey(channel)) {
            return channelFragmentMap.get(channel);
        }
        else {
            ChannelFragment fragment = DigestsChannelFragment.newInstance();
            channelFragmentMap.put(channel, fragment);
            return fragment;
        }
    }

    public abstract List<? extends Channel> getUserSelectedChannels();

    public abstract void addUserSelectedChannel(Channel channel);

    public abstract Channel getUserSelectedChannelByPosition(int position);
}
