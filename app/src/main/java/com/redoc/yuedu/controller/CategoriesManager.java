package com.redoc.yuedu.controller;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import com.redoc.yuedu.R;
import com.redoc.yuedu.bean.CacheableCategory;
import com.redoc.yuedu.bean.Category;
import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.bean.SingleChannelCategory;
import com.redoc.yuedu.news.bean.NewsCategory;
import com.redoc.yuedu.news.controller.NewsChannelsManager;
import com.redoc.yuedu.setting.bean.UserSettingCategory;
import com.redoc.yuedu.setting.view.UserSettingCategoryFragment;
import com.redoc.yuedu.view.MultiChannelCategoryFragment;
import com.redoc.yuedu.view.SingleChannelCategoryFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limen on 2016/4/30.
 */
public class CategoriesManager {
    private Context context;
    private NewsCategory news = new NewsCategory("首页", R.drawable.category_main);
    private SingleChannelCategory audio = new SingleChannelCategory("音频", R.drawable.category_audio);
    private UserSettingCategory setting = new UserSettingCategory("设置", R.drawable.category_setting);

    private Map<Category, Fragment> categoriesAndFragments = new HashMap<Category, Fragment>();
    private ArrayList<Category> categories = new ArrayList<Category>() {
        {
            add(news);
            add(audio);
            add(setting);
        }
    };
    public ArrayList<Category> getCategories() {
        return categories;
    }

    public ArrayList<Parcelable> getChacheableChannels() {
        ArrayList<Parcelable> cacheableChannels = new ArrayList<Parcelable>();
        for(Category category : categories) {
            if(CacheableCategory.class.isAssignableFrom(category.getClass())) {
                cacheableChannels.addAll(((CacheableCategory) category).getChannelCacheInfo());
            }
        }
        return cacheableChannels;
    }

    public CategoriesManager(Context context) {
        this.context = context;
    }

    public Fragment getOrCreateFragment(Category category) {
        String categoryName = category.getCategoryName();
        if(categoriesAndFragments.containsKey(category)) {
            return categoriesAndFragments.get(category);
        }
        else {
            Fragment fragment = null;
            switch (categoryName) {
                case "首页": {
                    fragment = MultiChannelCategoryFragment.newInstance(new NewsChannelsManager(context));
                    break;
                }
                case "音频": {
                    fragment = new SingleChannelCategoryFragment();
                    break;
                }
                case "设置": {
                    fragment = UserSettingCategoryFragment.newInstance();
                    break;
                }
                default: {
                    fragment = null;
                    break;
                }
            }
            categoriesAndFragments.put(category, fragment);
            return fragment;
        }
    }
}
