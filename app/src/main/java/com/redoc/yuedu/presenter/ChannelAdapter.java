package com.redoc.yuedu.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.redoc.yuedu.model.Channel;

/**
 * Created by limen on 2016/4/30.
 */
public class ChannelAdapter<T extends Channel> extends FragmentStatePagerAdapter {
    private ChannelsManager channelsManager;

    // TODO: Channel manager should have a channel adapter, instead of channel adapter have a channel manager.
    public ChannelAdapter(FragmentManager fragmentManager, ChannelsManager channelsManager) {
        super(fragmentManager);
        this.channelsManager = channelsManager;
    }

    // TODO: ChannelManager should have a addChannel method.
    public void addChannel(Channel channel) {
        channelsManager.addUserSelectedChannel(channel);
        notifyDataSetChanged();
    }

    public void removeChannel(Channel channel) {
        channelsManager.removeUserSelectedChannel(channel);
        notifyDataSetChanged();
    }

    // TODO: Different channel may generate different fragment, we may need a fragment generator
    @Override
    public Fragment getItem(int position) {
        Channel channel = channelsManager.getSortedUserSelectedChannels().get(position);
        return channelsManager.getOrCreateFragmentForChannel(channel);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return channelsManager.getSortedUserSelectedChannels().size();
    }

    // TODO: useless override
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment obj = (Fragment)super.instantiateItem(container, position);
        return obj;
    }
}
