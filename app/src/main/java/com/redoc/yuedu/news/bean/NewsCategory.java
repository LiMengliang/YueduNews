package com.redoc.yuedu.news.bean;

import com.redoc.yuedu.bean.CacheableCategory;
import com.redoc.yuedu.bean.MultiChannelCategory;

/**
 * Created by limen on 2016/5/13.
 */
public class NewsCategory extends MultiChannelCategory implements CacheableCategory {
    public NewsCategory(String categoryName, int categoryIconResoruceId) {
        super(categoryName, categoryIconResoruceId);
    }
}
