package com.redoc.yuedu.setting.view;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.redoc.yuedu.R;
import com.redoc.yuedu.YueduApplication;
import com.redoc.yuedu.model.CacheProgressStatus;
import com.redoc.yuedu.presenter.CacheStatus;
import com.redoc.yuedu.offlineCache.service.ChannelLocalCacheWorker;
import com.redoc.yuedu.offlineCache.view.OfflineCacheActivity;
import com.redoc.yuedu.setting.presenter.OfflineCacheProgressSimpleBroadcastReceiver;
import com.redoc.yuedu.offlineCache.utilities.OfflineCacheUtils;
import com.redoc.yuedu.utilities.cache.ACacheUtilities;
import com.redoc.yuedu.utilities.cache.CacheUtilities;
import com.redoc.yuedu.utilities.network.LoadImageUtilities;

import java.util.Calendar;

/**
 * Created by limen on 2016/5/12.
 */
public class UserSettingCategoryFragment extends Fragment {

    public static String CacheableChannelKey = "CacheableChannelsKey";
    public static Integer CacheProgress = 0x1200;
    public static int SelectIconRequest = 0x001;

    private View settingView;
    private View offlineView;
    private View favoriteView;
    private TextView cacheProgress;
    private ImageButton userIcon;
    private TextView cacheTime;
    private CheckBox autoCache;
    public static UserSettingCategoryFragment newInstance() {
        UserSettingCategoryFragment fragment = new UserSettingCategoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_setting_category, null, false);
        settingView = rootView.findViewById(R.id.setting_view);
        offlineView = rootView.findViewById(R.id.offline_view);
        cacheTime = (TextView)rootView.findViewById(R.id.cache_time_schedule);
        cacheTime.setText(OfflineCacheUtils.calendarToHourAndMin(OfflineCacheUtils.getOfflineCacheScheduleFromPreference()));
        favoriteView = rootView.findViewById(R.id.favorite_view);
        cacheProgress = (TextView)rootView.findViewById(R.id.progress);
        userIcon = (ImageButton)rootView.findViewById(R.id.user_icon);
        autoCache = (CheckBox)rootView.findViewById(R.id.auto_cache);
        autoCache.setChecked(OfflineCacheUtils.getAutoCacheEnabledFromPreference());

        offlineView.setOnClickListener(new OfflineViewClickListener());
        userIcon.setOnClickListener(new UserIconClickListener());
        cacheTime.setOnClickListener(new CacheTimeScheduleListener());
        autoCache.setOnCheckedChangeListener(new AutoCacheChecked());

        Bitmap cachedIcon = ACacheUtilities.getCacheImage(getActivity(), ImageSelectionActivity.usreIconCacheKey);
        if(cachedIcon != null) {
            userIcon.setImageBitmap(cachedIcon);
        }
        // WeakReference<UserSettingCategoryFragmentHandler> weakHandlerReference =
        //         new WeakReference<>(new UserSettingCategoryFragmentHandler(cacheProgress));
        // ChannelLocalCacheWorker.getInstance().AddHandler(weakHandlerReference.get());
        // Register progress broadcast receiver
        OfflineCacheProgressSimpleBroadcastReceiver receiver = new OfflineCacheProgressSimpleBroadcastReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.redoc.yuedu.CACHE_PROGRESS_UPDATED");
        YueduApplication.Context.registerReceiver(receiver, intentFilter);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SelectIconRequest && resultCode == Activity.RESULT_OK) {
            String selectedPath = data.getExtras().getString(ImageSelectionActivity.selectedIconPath);
            if(!selectedPath.equals("")) {
               LoadImageUtilities.displayImage("file://"+selectedPath, userIcon);
            }
        }
    }

    public void updateOfflineCacheProgress(CacheProgressStatus cacheProgressStatus) {
        if(cacheProgressStatus.getCacheStatus() == CacheStatus.NotStarted) {
            cacheProgress.setText("");
            return;
        }
        switch (cacheProgressStatus.getCacheType()) {
            case Digest:
                cacheProgress.setText(YueduApplication.Context.getString(R.string.cache_status_downloading) +
                        cacheProgressStatus.getChannelName() + CacheUtilities.getCacheTypeName(cacheProgressStatus.getCacheType()));
                break;
            case Image:
                cacheProgress.setText(YueduApplication.Context.getString(R.string.cache_status_downloading) +
                        cacheProgressStatus.getChannelName() + CacheUtilities.getCacheTypeName(cacheProgressStatus.getCacheType()) + " " +
                        cacheProgressStatus.getCurrentIndex() + "/" + cacheProgressStatus.getTotalCount());
                break;
            case Detail:
                break;
        }
    }

    class OfflineViewClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            ComponentName comp = new ComponentName(getActivity(), OfflineCacheActivity.class);
            intent.setComponent(comp);
            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList(CacheableChannelKey,
                    YueduApplication.getCategoriesManager().getChacheableChannels());
            intent.putExtras(arguments);
            startActivity(intent);
        }
    }

    class UserIconClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), ImageSelectionActivity.class);
            startActivityForResult(intent, UserSettingCategoryFragment.SelectIconRequest);
        }
    }

    class CacheTimeScheduleListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            TimePickerDialog timePicker = new TimePickerDialog(getActivity(),
                    new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    cacheTime.setText(OfflineCacheUtils.calendarToHourAndMin(calendar));
                    OfflineCacheUtils.writeOfflineCacheScheduleToPreference(calendar);
                    if(autoCache.isChecked()) {
                        OfflineCacheUtils.setOfflineCacheSchedule(calendar);
                    }
                }
            }, 12, 0, true);
            timePicker.show();
        }
    }

    class AutoCacheChecked implements CheckBox.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            OfflineCacheUtils.writeAutoCacheEnabledToPreference(isChecked);
            if(isChecked) {
                Calendar calendar = OfflineCacheUtils.getOfflineCacheScheduleFromPreference();
                OfflineCacheUtils.setOfflineCacheSchedule(calendar);
            }
        }
    }

    static class UserSettingCategoryFragmentHandler extends Handler {
        private TextView cacheProgress;

        public UserSettingCategoryFragmentHandler(TextView cacheProgress) {
            this.cacheProgress = cacheProgress;
        }
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == ChannelLocalCacheWorker.ProgressMessage) {
                Bundle bundle = msg.getData();
                CacheProgressStatus cacheProgressStatus = bundle.getParcelable(ChannelLocalCacheWorker.ProgressMessageKey);
                if(cacheProgressStatus.getCacheStatus() == CacheStatus.NotStarted) {
                    cacheProgress.setText("");
                    return;
                }
                switch (cacheProgressStatus.getCacheType()) {
                    case Digest:
                        cacheProgress.setText(YueduApplication.Context.getString(R.string.cache_status_downloading) +
                                cacheProgressStatus.getChannelName() + CacheUtilities.getCacheTypeName(cacheProgressStatus.getCacheType()));
                        break;
                    case Image:
                        cacheProgress.setText(YueduApplication.Context.getString(R.string.cache_status_downloading) +
                                cacheProgressStatus.getChannelName() + CacheUtilities.getCacheTypeName(cacheProgressStatus.getCacheType()) + " " +
                                cacheProgressStatus.getCurrentIndex() + "/" + cacheProgressStatus.getTotalCount());
                        break;
                    case Detail:
                        break;
                }
            }
        }
    }
}
