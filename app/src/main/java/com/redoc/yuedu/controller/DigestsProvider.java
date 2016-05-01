package com.redoc.yuedu.controller;

import android.content.Context;

import com.redoc.yuedu.bean.Channel;

/**
 * Created by limen on 2016/4/30.
 */
public interface DigestsProvider {
    void fetchLatest(Channel channel,Context context, DigestsAdapter digestsAdapter);
    void fetchMore(Channel channel, int index, Context context, DigestsAdapter digestsAdapter);
}
