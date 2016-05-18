package com.redoc.yuedu.setting.view;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redoc.yuedu.R;
import com.redoc.yuedu.view.MainActivity;

/**
 * Created by limen on 2016/5/12.
 */
public class UserSettingCategoryFragment extends Fragment {

    public static String CacheableChannelKey = "CacheableChannelsKey";

    private View settingView;
    private View offlineView;
    private View favoriteView;
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

        offlineView.setOnClickListener(new OfflineViewClickListener());
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
