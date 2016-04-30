package com.redoc.yuedu.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.redoc.yuedu.bean.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limen on 2016/4/30.
 */
public class ChannelAdapter<T extends Channel> extends FragmentPagerAdapter {
    private List<? extends Channel> channels;
    private ChannelsManager channelsManager;

    public ChannelAdapter(FragmentManager fragmentManager, ChannelsManager channelsManager) {
        super(fragmentManager);
        this.channelsManager = channelsManager;
        this.channels = channelsManager.getUserSelectedChannels();
    }

    public void addChannel(Channel channel) {//, NewsDigestsManager newsDigestsManager) {
        channelsManager.addUserSelectedChannel(channel);
        notifyDataSetChanged();
    }

    // TODO: Different channel may generate different fragment, we may need a fragment generator
    @Override
    public Fragment getItem(int position) {
        Channel channel = channels.get(position);
        return channelsManager.getOrCreateFragmentForChannel(channel);
    }

    @Override
    public int getCount() {
        return channels.size();
    }
}
