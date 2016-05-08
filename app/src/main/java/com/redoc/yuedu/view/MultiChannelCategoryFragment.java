package com.redoc.yuedu.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.redoc.yuedu.R;
import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.controller.ChannelAdapter;
import com.redoc.yuedu.controller.ChannelsManager;
import com.redoc.yuedu.view.utilities.AnimationUtilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MultiChannelCategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MultiChannelCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultiChannelCategoryFragment extends Fragment {

    private View mRootView;
    private ViewPager mChannelsViewPager;
    private LinearLayout mChannelSelectors;
    private HorizontalScrollView mHorizontalScrollView;
    private DisplayMetrics mDisplayMatrix;
    private Activity mActivity;
    private ChannelAdapter channelAdapter;
    private ImageView channelGlide;
    private ImageView channelOk;
    private View channelsManager;
    private GridView channelsGrid;
    private boolean isManagingChannel;

    private Channel currentSelectedChannel;
    private Channel getCurrentSelectedChannel() {
        if(currentSelectedChannel == null) {
            if(channelManager != null) {
                currentSelectedChannel = channelManager.getUserSelectedChannelByPosition(0);
            }
        }
        else {
            if(!currentSelectedChannel.isSelected() && channelManager != null) {
                currentSelectedChannel = channelManager.getUserSelectedChannelByPosition(0);
            }
        }
        return currentSelectedChannel;
    }

    private ChannelsManager channelManager;
    public void setChannelsManager(ChannelsManager channelsManager) {
        this.channelManager = channelsManager;
    }

    public static MultiChannelCategoryFragment newInstance(ChannelsManager channelsManager) {
        MultiChannelCategoryFragment fragment = new MultiChannelCategoryFragment();
        fragment.setChannelsManager(channelsManager);
        return fragment;
    }

    public MultiChannelCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        channelAdapter = new ChannelAdapter(getChildFragmentManager(), channelManager);
        mActivity = getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_multi_channel_category, container, false);
        // initializeChannels();
        initializeChannelSelectorsAndFragments();
        channelGlide = (ImageView)mRootView.findViewById(R.id.channel_glide);
        channelOk = (ImageView)mRootView.findViewById(R.id.channel_ok);
        channelsManager = mRootView.findViewById(R.id.channel_manager);
        channelsGrid = (GridView)channelsManager.findViewById(R.id.channels_grid);
        channelsGrid.setAdapter(new ChannelSelectionViewAdapter(getActivity(), channelManager));
        isManagingChannel = false;
        channelsManager.setVisibility(View.GONE);

        channelGlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                channelsManager.setVisibility(View.VISIBLE);
                isManagingChannel = true;
                AnimationUtilities.startAlphaAnim(channelsGrid, 0, 1, 600);
                AnimationUtilities.startAlphaAnim(mChannelsViewPager, 1, 0, 600);
                channelOk.setVisibility(View.VISIBLE);
                channelGlide.setVisibility(View.GONE);
                mChannelsViewPager.setCurrentItem(channelManager.getSortedUserSelectedChannels().indexOf(currentSelectedChannel));
            }
        });
        channelOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isManagingChannel = false;
                AnimationUtilities.startAlphaAnim(channelsGrid, 1, 0, 600);
                AnimationUtilities.startAlphaAnim(mChannelsViewPager, 0, 1, 600);
                channelsManager.setVisibility(View.GONE);
                channelOk.setVisibility(View.GONE);
                channelGlide.setVisibility(View.VISIBLE);
                updateChannelSelector();
                mChannelsViewPager.setCurrentItem(channelManager.getSortedUserSelectedChannels().indexOf(currentSelectedChannel));
            }
        });
        return mRootView;
    }

    private void initializeChannelSelectorsAndFragments() {
        mChannelSelectors = (LinearLayout) mRootView.findViewById(R.id.channelSelectors);
        mHorizontalScrollView = (HorizontalScrollView)mRootView.findViewById(R.id.channelSelectorBar);
        mChannelsViewPager = (ViewPager)mRootView.findViewById(R.id.digestViewPager);
        mChannelSelectors.removeAllViews();
        mDisplayMatrix = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMatrix);
        updateChannelSelector();
        mChannelsViewPager.setAdapter(channelAdapter);
        // TODO: When switch back to some old pager, the list view would be empty
        mChannelsViewPager.setOffscreenPageLimit(1);
        mChannelsViewPager.setCurrentItem(0);
        mChannelsViewPager.addOnPageChangeListener(new ChannelViewPagerChangedListener(this));
    }

    private void updateChannelSelector() {
        mChannelSelectors.removeAllViews();
        int chanelItemWidth = mDisplayMatrix.widthPixels / 7;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(chanelItemWidth,ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        // TODO:Maybe channel selector can also bind to an adapter
        int index = 0;
        for(Channel channel : channelManager.getSortedUserSelectedChannels()) {
            // Add channel name to selector
            TextView textView = new TextView(mActivity);
            textView.setText(channel.getChannelName());
            textView.setTextSize(15);
            textView.setOnClickListener(new ChannelNameOnClickListener());
            mChannelSelectors.addView(textView, index++, params);
        }
        // set selected channel, in this method, we'll also update news digests.s
        updateChannelSelectorBar(getCurrentSelectedChannel());
    }

    private void initializeChannelsManagerView() {

    }

    // TODO: TextView should be modualized
    private void updateChannelSelectorBar(Channel selectedChannel) {
        int position = selectedChannel == null ? 0 : channelManager.getSortedUserSelectedChannels().indexOf(selectedChannel);
        updateChannelSelectorBar(position);
    }
    private void updateChannelSelectorBar(int selectedChannelPosition)
    {
        // Initialize channel digests list
        Channel selectedChannel = channelManager.getUserSelectedChannelByPosition(selectedChannelPosition);
        ChannelFragment channelFragment = channelManager.getOrCreateFragmentForChannel(selectedChannel);
        // Scroll channel selector
        for (int i = 0; i < mChannelSelectors.getChildCount(); i++) {
            View channelItem = mChannelSelectors.getChildAt(selectedChannelPosition);
            int width = channelItem.getMeasuredWidth();
            int left = channelItem.getLeft();
            int xDestination = left + width / 2 - mDisplayMatrix.widthPixels / 2;
            mHorizontalScrollView.smoothScrollTo(xDestination, 0);
        }
        // Check corresponding channel.
        for (int j = 0; j < mChannelSelectors.getChildCount(); j++) {
            TextView checkView = (TextView) mChannelSelectors.getChildAt(j);
            checkView.setTextColor(0xffffffff);
            if (j == selectedChannelPosition) {
                checkView.setAlpha(1.0f);
            } else {
                checkView.setAlpha(0.5f);
            }
        }
    }

    class ChannelNameOnClickListener implements View.OnClickListener {
         @Override
         public void onClick(View v) {
         }
    }

    class ChannelViewPagerChangedListener implements ViewPager.OnPageChangeListener {
        float lastPositionOffset = -1;
        private MultiChannelCategoryFragment channelFragment;
        public ChannelViewPagerChangedListener(MultiChannelCategoryFragment channelFragment) {
            this.channelFragment = channelFragment;
        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(lastPositionOffset == -1) {
                lastPositionOffset = positionOffset;
                return;
            }
            if(lastPositionOffset < positionOffset && positionOffset > 0.999) {
                channelFragment.updateChannelSelectorBar(position + 1);
                currentSelectedChannel = channelManager.getUserSelectedChannelByPosition(position + 1);
                lastPositionOffset = -1;
                return;
            }
            else if(lastPositionOffset > positionOffset && positionOffset < 0.001) {
                channelFragment.updateChannelSelectorBar(position);
                currentSelectedChannel = channelManager.getUserSelectedChannelByPosition(position);
                lastPositionOffset = -1;
                return;
            }
            lastPositionOffset = positionOffset;
        }

        @Override
        public void onPageSelected(int position) {
            //newsFragment.setSelectedChannel(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    class ChannelSelectionViewAdapter extends BaseAdapter{
        private Context context;
        private ChannelsManager channelsManager;
        private LayoutInflater layoutInflater;
        ChannelSelectionViewAdapter(Context context, ChannelsManager channelsManager) {
            this.context = context;
            this.channelsManager = channelsManager;
            this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return channelsManager.getSortedAllChannels().size();
        }

        @Override
        public Object getItem(int position) {
            return channelsManager.getSortedAllChannels().get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                final View aChannelItem = layoutInflater.inflate(R.layout.widget_channel_item, null);
                final ToggleButton button = (ToggleButton)aChannelItem.findViewById(R.id.channel_checkbox);
                final Channel channel = channelManager.getSortedAllChannels().get(position);
                button.setTextOn(channel.getChannelName());
                button.setTextOff(channel.getChannelName());
                button.setText(channel.getChannelName());
                if(channel.isSelected()) {
                    button.setChecked(true);
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (button.isChecked()) {
                            // channelManager.addUserSelectedChannel(channel);
                            channelAdapter.addChannel(channel);
                        } else {
                            // channelManager.removeUserSelectedChannel(channel);
                            channelAdapter.removeChannel(channel);
                        }
                    }
                });
                convertView = aChannelItem;
                convertView.setTag(new ViewHolder() {
                    {
                        channelItem = aChannelItem;
                    }
                });
            }
            return convertView;
        }

        class ViewHolder {
            public View channelItem;
        }
    }
}
