package com.redoc.yuedu.presenter;

import android.widget.BaseAdapter;

/**
 * Created by limen on 2016/4/30.
 */
public abstract class DigestsAdapter extends BaseAdapter {
    public abstract void fetchLatest();
    public abstract void fetchMore();
}
