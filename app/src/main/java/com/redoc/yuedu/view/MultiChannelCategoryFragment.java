package com.redoc.yuedu.view;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.redoc.yuedu.R;

import java.util.ArrayList;

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
    // private ArrayList<NewsChannelFragment> mChannelsFragments;
    private LinearLayout mChannelSelectors;
    private HorizontalScrollView mHorizontalScrollView;
    private DisplayMetrics mDisplayMatrix;
    private Activity mActivity;
    // private NewsChannelsManager mChannelManager;

    public MultiChannelCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_multi_channel_category, container, false);
    //     mActivity = getActivity();
    //     NewsChannelsAdapter mChannelsAdapter = new NewsChannelsAdapter(getChildFragmentManager());
    //     mChannelManager = new NewsChannelsManager(mActivity, mChannelsAdapter);
    //     // initializeChannels();
    //     initializeChannelSelectorsAndFragments();
    //     // set selected channel, in this method, we'll also update news digests.
    //     setSelectedChannel(0);
        return mRootView;
    }

    // private void initializeChannelSelectorsAndFragments() {
    //     mChannelSelectors = (LinearLayout) mRootView.findViewById(R.id.channelSelectors);
    //     mHorizontalScrollView = (HorizontalScrollView)mRootView.findViewById(R.id.channelSelectorBar);
    //     mChannelsViewPager = (ViewPager)mRootView.findViewById(R.id.digestViewPager);
    //     mChannelSelectors.removeAllViews();
    //     mDisplayMatrix = new DisplayMetrics();
    //     mActivity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMatrix);
    //     int chanelItemWidth = mDisplayMatrix.widthPixels / 7;
    //     LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(chanelItemWidth,ViewGroup.LayoutParams.WRAP_CONTENT);
    //     params.gravity = Gravity.CENTER_VERTICAL;
    //     // TODO:Maybe channel selector can also bind to an adapter
    //     int index = 0;
    //     for(NewsChannelViewBean channel : mChannelManager.getUserSelctedChannels()) {
    //         // Add channel name to selector
    //         TextView textView = new TextView(mActivity);
    //         textView.setText(channel.getChannelName());
    //         textView.setTextSize(15);
    //         textView.setOnClickListener(new ChannelNameOnClickListener());
    //         mChannelSelectors.addView(textView, index++, params);
    //     }
    //     mChannelsViewPager.setAdapter(mChannelManager.getChannelsAdapter());
    //     mChannelsViewPager.setOffscreenPageLimit(1);
    //     mChannelsViewPager.setCurrentItem(0);
    //     mChannelsViewPager.addOnPageChangeListener(new DigestsViewPagerChangedListener(this));
    // }
//
    // private void setSelectedChannel(int position)
    // {
    //     // Initialize channel digests list
    //     NewsChannelViewBean selectedChannel = mChannelManager.getChannel(position);
    //     NewsChannelFragment newsChannelFragment = mChannelManager.getChannelFragment(position);
    //     if(newsChannelFragment.reachTopOfAllNewsOrFirstInitiated()) {
    //         selectedChannel.getNewsDigestsManager().fetchLatestNews(getContext());
    //     }
    //     // Scroll channel selector
    //     for (int i = 0; i < mChannelSelectors.getChildCount(); i++) {
    //         View channelItem = mChannelSelectors.getChildAt(position);
    //         int width = channelItem.getMeasuredWidth();
    //         int left = channelItem.getLeft();
    //         int xDestination = left + width / 2 - mDisplayMatrix.widthPixels / 2;
    //         mHorizontalScrollView.smoothScrollTo(xDestination, 0);
    //     }
    //     // Check corresponding channel.
    //     for (int j = 0; j < mChannelSelectors.getChildCount(); j++) {
    //         TextView checkView = (TextView) mChannelSelectors.getChildAt(j);
    //         if (j == position) {
    //             checkView.setTextColor(0xff000000);
    //         } else {
    //             checkView.setTextColor(0xffffffff);
    //         }
    //     }
    // }
//
    // class ChannelNameOnClickListener implements View.OnClickListener {
//
    //     @Override
    //     public void onClick(View v) {
//
    //     }
    // }
//
    // class DigestsViewPagerChangedListener implements ViewPager.OnPageChangeListener {
//
    //     float lastPositionOffset = -1;
    //     private NewsFragment newsFragment;
//
    //     public DigestsViewPagerChangedListener(NewsFragment fragment) {
    //         newsFragment = fragment;
    //     }
    //     @Override
    //     public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    //         if(lastPositionOffset == -1) {
    //             lastPositionOffset = positionOffset;
    //             return;
    //         }
    //         if(lastPositionOffset < positionOffset && positionOffset > 0.999) {
    //             newsFragment.setSelectedChannel(position+1);
    //             lastPositionOffset = -1;
    //             return;
    //         }
    //         else if(lastPositionOffset > positionOffset && positionOffset < 0.001) {
    //             newsFragment.setSelectedChannel(position);
    //             lastPositionOffset = -1;
    //             return;
    //         }
    //         lastPositionOffset = positionOffset;
    //     }
//
    //     @Override
    //     public void onPageSelected(int position) {
    //         //newsFragment.setSelectedChannel(position);
    //     }
//
    //     @Override
    //     public void onPageScrollStateChanged(int state) {
//
    //     }
    // }
}
