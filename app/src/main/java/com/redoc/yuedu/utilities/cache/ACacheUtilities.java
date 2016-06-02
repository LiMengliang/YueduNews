package com.redoc.yuedu.utilities.cache;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by limen on 2016/5/15.
 */
public class ACacheUtilities {
    /**
     * 设置缓存数据（key,value）
     */
    public static void setCacheStr(Context context, String key, String value) {
        if (value != null && !value.isEmpty()) {
            ACache.get(context).put(key, value);
        }
    }

    /**
     * 获取缓存数据更具key
     */
    public static String getCacheStr(Context context, String key) {
        return ACache.get(context).getAsString(key);
    }

    public static void setCacheImage(Context context, String key, Bitmap bitmap) {
        if(bitmap != null) {
            ACache.get(context).put(key, bitmap);
        }
    }

    public static Bitmap getCacheImage(Context context, String key) {
        return ACache.get(context).getAsBitmap(key);
    }
}
