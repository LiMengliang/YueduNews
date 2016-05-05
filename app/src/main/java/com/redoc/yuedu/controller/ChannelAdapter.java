package com.redoc.yuedu.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.redoc.yuedu.bean.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limen on 2016/4/30.
 */
public class ChannelAdapter<T extends Channel> extends FragmentStatePagerAdapter {
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
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return channels.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment obj = (Fragment)super.instantiateItem(container, position);
        return obj;
    }
}
