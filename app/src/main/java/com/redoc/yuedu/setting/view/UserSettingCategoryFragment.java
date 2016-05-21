package com.redoc.yuedu.setting.view;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.redoc.yuedu.R;
import com.redoc.yuedu.YueduApplication;
import com.redoc.yuedu.bean.CacheProgressStatus;
import com.redoc.yuedu.controller.ChannelCache;
import com.redoc.yuedu.view.MainActivity;

/**
 * Created by limen on 2016/5/12.
 */
public class UserSettingCategoryFragment extends Fragment {

    public static String CacheableChannelKey = "CacheableChannelsKey";
    public static Integer CacheProgress = 0x1200;

    private View settingView;
    private View offlineView;
    private View favoriteView;
    private TextView cacheProgress;
    public static UserSettingCategoryFragment newInstance() {
        UserSettingCategoryFragment fragment = new UserSettingCategoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_setting_category, null, false);
        settingView = rootView.findViewById(R.id.setting_view);
        offlineView = rootView.findViewById(R.id.offline_view);
        favoriteView = rootView.findViewById(R.id.favorite_view);
        cacheProgress = (TextView)rootView.findViewById(R.id.progress);

        offlineView.setOnClickListener(new OfflineViewClickListener());

        ChannelCache.getInstance().AddHandler(new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == ChannelCache.ProgressMessage) {
                    Bundle bundle = msg.getData();
                    CacheProgressStatus cacheProgressStatus = bundle.getParcelable(ChannelCache.ProgressMessageKey);
                    if(cacheProgressStatus.getFinished()) {
                        cacheProgress.setText(YueduApplication.Context.getString(R.string.cache_progress_finished));
                    }
                    else {
                        cacheProgress.setText(String.format(YueduApplication.Context.getString(R.string.cache_progress),
                                cacheProgressStatus.getChannelName()));
                    }
                }
            }
        });
        return rootView;
    }

    class OfflineViewClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            ComponentName comp = new ComponentName(getActivity(), OfflineCacheActivity.class);
            intent.setComponent(comp);
            Bundle arguments = new Bundle();
            MainActivity activity = (MainActivity)getActivity();
            arguments.putParcelableArrayList(CacheableChannelKey, activity.getCategoriesManager().getChacheableChannels());
            intent.putExtras(arguments);
            startActivity(intent);
        }
    }
}
