package com.redoc.yuedu;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.redoc.yuedu.utilities.network.LoadImageUtilities;
import com.redoc.yuedu.utilities.network.VolleyUtilities;

import java.io.File;

/**
 * Created by limen on 2016/4/30.
 */
public class YueduApplication extends Application {
    public static Context Context;

    @Override
    public void onCreate() {
        super.onCreate();
        Context = getApplicationContext();
        VolleyUtilities.initialize(Context);
        LoadImageUtilities.initImageLoader(getApplicationContext());
    }
}
