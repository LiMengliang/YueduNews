package com.redoc.yuedu.utilities.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.redoc.yuedu.YueduApplication;

/**
 * Created by limen on 2016/5/31.
 */
public class NetworkUtilities {
    public static boolean isWifiAvailable() {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) YueduApplication.Context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取NetworkInfo对象
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected() && !networkInfo.isRoaming();
    }
}
