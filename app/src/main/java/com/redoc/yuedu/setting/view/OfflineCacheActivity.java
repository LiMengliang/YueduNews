package com.redoc.yuedu.setting.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.redoc.yuedu.R;
import com.redoc.yuedu.YueduApplication;
import com.redoc.yuedu.bean.CacheProgressStatus;
import com.redoc.yuedu.bean.CacheType;
import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.controller.CacheStatus;
import com.redoc.yuedu.controller.ChannelCache;
import com.redoc.yuedu.utilities.cache.CacheUtilities;
import com.redoc.yuedu.utilities.preference.PreferenceUtilities;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OfflineCacheActivity extends Activity {

    private ArrayList<Channel> allCacheableChannels;
    private List<Channel> checkedChannel = new ArrayList<>();
    private ToggleButton selectAll;
    private TextView firstLine;
    private TextView secondLine;
    private TextView thirdLine;
    private AllCacheChannelAdapter allCacheChannelAdapter;
    private CacheProgressStatus cacheProgressStatus = null;

    public final static String CacheSettingPreference = "CacheSettingPreference";

    public void setCacheProgressStatus(CacheProgressStatus status) {
        cacheProgressStatus = status;
    }
    public CacheProgressStatus getCacheProgressStatus() {
        return cacheProgressStatus;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_cache);
        ListView allChannelsList = (ListView) findViewById(R.id.all_channels);
        ToggleButton startStopCache = (ToggleButton) findViewById(R.id.startStopCache);
        selectAll = (ToggleButton) findViewById(R.id.select_all);
        ImageButton backButton = (ImageButton) findViewById(R.id.back);
        firstLine = (TextView) findViewById(R.id.cache_progress_line_one);
        secondLine = (TextView) findViewById(R.id.cache_progress_line_two);
        thirdLine = (TextView) findViewById(R.id.cache_progress_line_three);

        startStopCache.setOnClickListener(new StartStopCacheClickedListener(checkedChannel, this));
        allCacheableChannels = getIntent().getParcelableArrayListExtra(UserSettingCategoryFragment.CacheableChannelKey);
        allCacheChannelAdapter = new AllCacheChannelAdapter(this, allCacheableChannels);
        allChannelsList.setAdapter(allCacheChannelAdapter);
        allChannelsList.setOnItemClickListener(new ChannelListItemClickedListener());
        backButton.setOnClickListener(new BackClickedListener());
        selectAll.setOnClickListener(new SelectAllClickedListener());

        WeakReference<CacheProgressHandler> weakCacheProgressHandlerReference =
                new WeakReference<>(new CacheProgressHandler(firstLine,
                        secondLine, thirdLine, startStopCache, selectAll, this));
        ChannelCache.getInstance().AddHandler(weakCacheProgressHandlerReference.get());
        initializeDisplayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(cacheProgressStatus != null) {
            cacheProgressStatus.writeToPreferences();
        }
    }

    public void initializeDisplayer() {
        CacheProgressStatus status = CacheProgressStatus.getFromPreferences();
        switch (status.getCacheStatus()) {
            case NotStarted:
                if(status.getLastCacheTime() == 0) {
                    firstLine.setText("");
                    secondLine.setText("没有缓存");
                    thirdLine.setText("");
                }
                else {
                    firstLine.setText(YueduApplication.Context.getString(R.string.cache_progress_last_cache_time));
                    long timeSpan = (new Date().getTime() - status.getLastCacheTime())/1000/60;
                    if(timeSpan < 1) {
                        secondLine.setText("刚刚");
                    }
                    else if(timeSpan >= 60) {
                        timeSpan = timeSpan / 60;
                        if(timeSpan >= 24) {
                            timeSpan = timeSpan / 24;
                            secondLine.setText(Long.toString(timeSpan) + "天前");
                        }
                        else {
                            secondLine.setText(Long.toString(timeSpan)+"小时前");
                        }
                    }
                    else {
                        secondLine.setText(Long.toString(timeSpan)+"分钟前");
                    }
                    thirdLine.setText("");
                }
                break;
            case InProgress:
                firstLine.setText(YueduApplication.Context.getString(R.string.cache_status_downloading));
                secondLine.setText(status.getChannelName());
                thirdLine.setText(CacheUtilities.getCacheTypeName(status.getCacheType()));
                break;
            case Paused:
                firstLine.setText(YueduApplication.Context.getString(R.string.cache_status_paused));
                secondLine.setText(status.getChannelName());
                thirdLine.setText(CacheUtilities.getCacheTypeName(status.getCacheType()));
                break;
        }
    }

    static class CacheProgressHandler extends Handler {
        private TextView firstLine;
        private TextView secondLine;
        private TextView thirdLine;
        private ToggleButton startStopCache;
        private ToggleButton selectAll;
        private OfflineCacheActivity activity;

        CacheProgressHandler(TextView firstLine, TextView secondLine, TextView thirdLine, ToggleButton startStopCache, ToggleButton selectAll,
                             OfflineCacheActivity activity) {
            this.firstLine = firstLine;
            this.secondLine = secondLine;
            this.thirdLine = thirdLine;
            this.startStopCache = startStopCache;
            this.selectAll = selectAll;
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            if(msg.what == ChannelCache.ProgressMessage) {
                Bundle bundle = msg.getData();
                CacheProgressStatus cacheProgressStatus = bundle.getParcelable(ChannelCache.ProgressMessageKey);
                activity.setCacheProgressStatus(cacheProgressStatus);
                if(cacheProgressStatus.getCacheStatus() == CacheStatus.NotStarted) {
                    firstLine.setText("");
                    secondLine.setText(YueduApplication.Context.getString(R.string.cache_progress_finished));
                    thirdLine.setText("");
                    startStopCache.setChecked(false);
                    selectAll.setEnabled(true);
                }
                else {
                    secondLine.setText(cacheProgressStatus.getChannelName());
                    thirdLine.setText(CacheUtilities.getCacheTypeName(cacheProgressStatus.getCacheType()));
                }
            }
        }
    }

    class AllCacheChannelAdapter extends BaseAdapter {

        private Context context;
        private List<Channel> allChannels;

        public AllCacheChannelAdapter(Context context, List<Channel> allChannels) {
            this.context = context;
            this.allChannels = allChannels;
            for(Channel channel : allChannels) {
                if(PreferenceUtilities.getBooleanValue(CacheSettingPreference, channel.getChannelId())) {
                    checkedChannel.add(channel);
                }
            }
        }

        public void addCacheChannel(Channel channel) {
            checkedChannel.add(channel);
            PreferenceUtilities.writeToPreference(CacheSettingPreference, channel.getChannelId(), true);
            notifyDataSetChanged();
        }

        public void removeCacheChannel(Channel channel) {
            checkedChannel.remove(channel);
            PreferenceUtilities.writeToPreference(CacheSettingPreference, channel.getChannelId(), false);
            notifyDataSetChanged();
        }

        public void addAllChannel(List<Channel> channels) {
            checkedChannel.addAll(channels);
            for(Channel channel : channels) {
                PreferenceUtilities.writeToPreference(CacheSettingPreference, channel.getChannelId(), true);
            }
            notifyDataSetChanged();
        }

        public void removeAllCachedChannels() {
            checkedChannel.clear();
            notifyDataSetChanged();
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
            Channel channel = allCacheableChannels.get(position);
            if (checkedChannel.contains(channel)) {
                allCacheChannelAdapter.removeCacheChannel(channel);
            } else {
                allCacheChannelAdapter.addCacheChannel(channel);
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
            if (ChannelCache.getInstance().getStatus() == CacheStatus.NotStarted) {
                ChannelCache.getInstance().startCache(channelsToCache, context);
                firstLine.setText(YueduApplication.Context.getString(R.string.cache_status_downloading));
                selectAll.setEnabled(false);
            }
            else if(ChannelCache.getInstance().getStatus() == CacheStatus.InProgress) {
                ChannelCache.getInstance().pause();
                firstLine.setText(YueduApplication.Context.getString(R.string.cache_status_paused));
            }
            else if(ChannelCache.getInstance().getStatus() == CacheStatus.Paused) {
                ChannelCache.getInstance().resume();
                firstLine.setText(YueduApplication.Context.getString(R.string.cache_status_downloading));
            }
        }
    }

    class SelectAllClickedListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(selectAll.isChecked()) {
                checkedChannel.clear();
                allCacheChannelAdapter.addAllChannel(allCacheableChannels);
            }
            else{
                allCacheChannelAdapter.removeAllCachedChannels();
            }
        }
    }
}
