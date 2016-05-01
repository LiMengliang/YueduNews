package com.redoc.yuedu;

import android.app.Application;

import com.redoc.yuedu.utilities.network.VolleyUtilities;

/**
 * Created by limen on 2016/4/30.
 */
public class YueduApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VolleyUtilities.initialize(getApplicationContext());
    }
}
