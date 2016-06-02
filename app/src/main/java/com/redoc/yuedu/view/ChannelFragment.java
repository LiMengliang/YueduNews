package com.redoc.yuedu.view;

import android.support.v4.app.Fragment;

/**
 * Created by limen on 2016/4/30.
 */
public abstract class ChannelFragment extends Fragment {

    public abstract boolean shouldRefreshChannel();

    public abstract void refresh();
}
