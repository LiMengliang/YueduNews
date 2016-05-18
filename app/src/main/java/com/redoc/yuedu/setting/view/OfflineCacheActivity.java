package com.redoc.yuedu.setting.view;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.redoc.yuedu.R;
import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.controller.ChannelCache;

import java.util.ArrayList;
import java.util.List;

public class OfflineCacheActivity extends Activity {

    private ArrayList<Channel> allCacheableChannels;
    private List<Channel> checkedChannel = new ArrayList<Channel>();
    private ListView allChannelsList;
    private TextView cachedCounts;
    private ToggleButton startStopCache;
    private ProgressBar progressBar;
    private ImageButton backButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_cache);
        allChannelsList = (ListView) findViewById(R.id.all_channels);
        startStopCache = (ToggleButton) findViewById(R.id.startStopCache);
        cachedCounts = (TextView) findViewById(R.id.cachedCount);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        backButton = (ImageButton) findViewById(R.id.back);

        startStopCache.setOnClickListener(new StartStopCacheClickedListener(checkedChannel, this));
        allCacheableChannels = getIntent().getParcelableArrayListExtra(UserSettingCategoryFragment.CacheableChannelKey);
        allChannelsList.setAdapter(new AllCacheChannelAdapter(this, allCacheableChannels));
        allChannelsList.setOnItemClickListener(new ChannelListItemClickedListener());
        backButton.setOnClickListener(new BackClickedListener());
    }

    // TODO: use handler
    public void onCacheFinished() {
        startStopCache.setChecked(false);
    }

    public void onCachedOneChannel(String value) {
        cachedCounts.setText(value);
        progressBar.setProgress(Integer.parseInt(value));
    }

    class AllCacheChannelAdapter extends BaseAdapter {

        private Context context;
        private List<Channel> allChannels;

        public AllCacheChannelAdapter(Context context, List<Channel> allChannels) {
            this.context = context;
            this.allChannels = allChannels;
        }

        @Override
        public int getCount() {
            return allCacheableChannels.size();
        }

        @Override
        public Object getItem(int position) {
            return allChannels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CacheChannelView cacheChannelView;
            if (convertView == null) {
                cacheChannelView = new CacheChannelView(context);
                convertView = cacheChannelView.getRootView();
                convertView.setTag(cacheChannelView);
            } else {
                cacheChannelView = (CacheChannelView) convertView.getTag();
            }
            Channel channel = (Channel) getItem(position);
            cacheChannelView.setChannelName(channel.getChannelName());
            cacheChannelView.setChecked(checkedChannel.contains(channel));
            return cacheChannelView.getRootView();
        }
    }

    class BackClickedListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            finish();
        }
    }

    class ChannelListItemClickedListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CacheChannelView cacheChannelView = (CacheChannelView) view.getTag();
            Channel channel = allCacheableChannels.get(position);
            if (checkedChannel.contains(channel)) {
                checkedChannel.remove(channel);
                cacheChannelView.setChecked(false);
            } else {
                checkedChannel.add(channel);
                cacheChannelView.setChecked(true);
            }
        }
    }

    class StartStopCacheClickedListener implements View.OnClickListener {

        private Context context;
        private List<Channel> channelsToCache;

        public StartStopCacheClickedListener(List<Channel> channelsToCache, Context context) {
            this.context = context;
            this.channelsToCache = channelsToCache;
        }

        @Override
        public void onClick(View v) {
            ToggleButton toggleButton = (ToggleButton) v;
            if (toggleButton.isChecked()) {
                ChannelCache.getInstance().CacheTopDigests(channelsToCache, context);
                progressBar.setMax(channelsToCache.size());
            }
        }
    }
}
