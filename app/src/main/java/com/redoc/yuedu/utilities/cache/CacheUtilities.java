package com.redoc.yuedu.utilities.cache;

import com.redoc.yuedu.R;
import com.redoc.yuedu.YueduApplication;
import com.redoc.yuedu.model.CacheType;

/**
 * Created by limen on 2016/5/22.
 */
public class CacheUtilities {

    public static String getCacheTypeName(CacheType cacheType) {
        switch (cacheType) {
            case Detail:
                return YueduApplication.Context.getString(R.string.cache_type_detail);
            case Digest:
                return YueduApplication.Context.getString(R.string.cache_type_digest);
            case Image:
                return YueduApplication.Context.getString(R.string.cache_type_image);
        }
        return "";
    }
}
