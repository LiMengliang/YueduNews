package com.redoc.yuedu.setting.view;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.redoc.yuedu.R;
import com.redoc.yuedu.YueduApplication;
import com.redoc.yuedu.bean.CacheProgressStatus;
import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.contentprovider.ChannelProviderUtils;
import com.redoc.yuedu.contentprovider.ado.DatabaseUtils;
import com.redoc.yuedu.controller.CacheStatus;
import com.redoc.yuedu.controller.ChannelLocalCacheWorker;
import com.redoc.yuedu.setting.service.OfflineCacheProgressBroadcastReceiver;
import com.redoc.yuedu.setting.service.OfflineCacheService;
import com.redoc.yuedu.setting.utilities.OfflineCacheUtils;
import com.redoc.yuedu.utilities.cache.CacheUtilities;
import com.redoc.yuedu.view.widget.ToolBar;

import java.util.Date;
import java.util.List;

public class OfflineCacheActivity extends Activity {

    private List<Channel> cacheableChannels;
    private Button clearCache;
    private ToggleButton startStopCache;
    private TextView firstLine;
    private TextView secondLine;
    private TextView thirdLine;
    private AllCacheChannelAdapter allCacheChannelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_cache);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ListView allChannelsList = (ListView) findViewById(R.id.all_channels);
        startStopCache = (ToggleButton) findViewById(R.id.startStopCache);
        clearCache = (Button) findViewById(R.id.select_all);
        firstLine = (TextView) findViewById(R.id.cache_progress_line_one);
        secondLine = (TextView) findViewById(R.id.cache_progress_line_two);
        thirdLine = (TextView) findViewById(R.id.cache_progress_line_three);
        ToolBar toolBar = (ToolBar) findViewById(R.id.toolbar);

        cacheableChannels = getIntent().getParcelableArrayListExtra(UserSettingCategoryFragment.CacheableChannelKey);
        startStopCache.setOnClickListener(new StartStopCacheClickedListener());
        allCacheChannelAdapter = new AllCacheChannelAdapter(this, cacheableChannels);
        allChannelsList.setAdapter(allCacheChannelAdapter);
        allChannelsList.setOnItemClickListener(new ChannelListItemClickedListener());
        clearCache.setOnClickListener(new ClearCacheListener());
        toolBar.setBackClickListener(new BackClickedListener());

        initializeDisplayer();

        // Register cache progress broadcast receiver
        OfflineCacheProgressBroadcastReceiver receiver = new OfflineCacheProgressBroadcastReceiver(this);
        IntentFilter receiverIntentFilter = new IntentFilter();
        receiverIntentFilter.addAction("com.redoc.yuedu.CACHE_PROGRESS_UPDATED");
        YueduApplication.Context.registerReceiver(receiver, receiverIntentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(ChannelLocalCacheWorker.getInstance().getCacheProgressStatus() != null) {
            ChannelLocalCacheWorker.getInstance().getCacheProgressStatus().writeToPreferences();
        }
    }

    public void initializeDisplayer() {
        CacheProgressStatus status = ChannelLocalCacheWorker.getInstance().getCacheProgressStatus();
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

    public void updateCacheProgressToView(CacheProgressStatus cacheProgressStatus) {
        if(cacheProgressStatus.getCacheStatus() == CacheStatus.NotStarted) {
            firstLine.setText("");
            secondLine.setText(YueduApplication.Context.getString(R.string.cache_progress_finished));
            thirdLine.setText("");
            startStopCache.setChecked(false);
            clearCache.setEnabled(true);
        }
        else {
            switch (cacheProgressStatus.getCacheType()) {
                case Digest:
                    secondLine.setText(cacheProgressStatus.getChannelName());
                    thirdLine.setText(CacheUtilities.getCacheTypeName(cacheProgressStatus.getCacheType()));
                    break;
                case Image:
                    secondLine.setText(cacheProgressStatus.getChannelName());
                    thirdLine.setText(CacheUtilities.getCacheTypeName(
                            cacheProgressStatus.getCacheType()) + " " +
                            cacheProgressStatus.getCurrentIndex()
                            + "/" + cacheProgressStatus.getTotalCount());
                    break;
                case Detail:
                    break;
            }
        }
    }

    class AllCacheChannelAdapter extends BaseAdapter {

        private Context context;
        private List<Channel> allChannels;

        public AllCacheChannelAdapter(Context context, List<Channel> allChannels) {
            this.context = context;
            this.allChannels = allChannels;
        }

        public void reverseChannelNeedCacheProperty(Channel channel) {
            channel.setNeedCache(!channel.isNeedCache());
            ContentValues values = new ContentValues();
            values.put(DatabaseUtils.NEED_CACHE, channel.isNeedCache() ? 1 : 0);
            ChannelProviderUtils.updateChannel(values, channel.getChannelId());
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return cacheableChannels.size();
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
            cacheChannelView.setChecked(channel.isNeedCache());
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
            Channel channel = cacheableChannels.get(position);
            allCacheChannelAdapter.reverseChannelNeedCacheProperty(channel);
        }
    }

    class StartStopCacheClickedListener implements View.OnClickListener {

        public StartStopCacheClickedListener() {
        }

        @Override
        public void onClick(View v) {
            clearCache.setEnabled(false);
            if (ChannelLocalCacheWorker.getInstance().getStatus() == CacheStatus.NotStarted) {
                Intent intent = new Intent(YueduApplication.Context, OfflineCacheService.class);
                intent.putExtra(OfflineCacheUtils.CACHE_ACTION, OfflineCacheUtils.START_CACHE);
                startService(intent);
                firstLine.setText(YueduApplication.Context.getString(R.string.cache_status_downloading));
            }
            else
            if(ChannelLocalCacheWorker.getInstance().getStatus() == CacheStatus.InProgress) {
                Intent intent = new Intent(YueduApplication.Context, OfflineCacheService.class);
                intent.putExtra(OfflineCacheUtils.CACHE_ACTION, OfflineCacheUtils.PAUSE_CACHE);
                startService(intent);
                firstLine.setText(YueduApplication.Context.getString(R.string.cache_status_paused));
            }
            else if(ChannelLocalCacheWorker.getInstance().getStatus() == CacheStatus.Paused) {
                Intent intent = new Intent(YueduApplication.Context, OfflineCacheService.class);
                intent.putExtra(OfflineCacheUtils.CACHE_ACTION, OfflineCacheUtils.RESUME_CACHE);
                startService(intent);
                firstLine.setText(YueduApplication.Context.getString(R.string.cache_status_downloading));
            }
        }
    }

    class ClearCacheListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            firstLine.setText("");
            secondLine.setText("没有缓存");
            thirdLine.setText("");
        }
    }
}
