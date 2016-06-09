package com.redoc.yuedu.utilities.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.redoc.yuedu.utilities.network.LoadImageUtilities;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by limen on 2016/5/15.
 */
public class ACacheUtilities {

    private static Map<Context, WeakReference<ACache>> ACacheTable = new HashMap<>();
    /**
     * 设置缓存数据（key,value）
     */
    public static void setCacheStr(Context context, String key, String value) {
        if (value != null && !value.isEmpty()) {
            tryGetOrCreateACache(context).put(key, value);
        }
    }

    /**
     * 获取缓存数据更具key
     */
    public static String getCacheStr(Context context, String key) {
        return tryGetOrCreateACache(context).getAsString(key);
    }

    public static void setCacheImage(Context context, String key, Bitmap bitmap) {
        if(bitmap != null) {
            Bitmap bitmapToSave = LoadImageUtilities.resizeBitmapToAcceptableSize(bitmap);
            tryGetOrCreateACache(context).put(key, bitmapToSave);
        }
    }

    public static Bitmap getCacheImage(Context context, String key) {
        Bitmap cachedBitmap = null;
        try {
            cachedBitmap = tryGetOrCreateACache(context).getAsBitmap(key);
        }
        catch (OutOfMemoryError error){
            Log.i("Out of memory error", key);
        }
        finally {
            return cachedBitmap;
        }
    }

    private static ACache tryGetOrCreateACache(Context context) {
        if(ACacheTable.containsKey(context)) {
            return ACacheTable.get(context).get();
        }
        return ACache.get(context, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
}
