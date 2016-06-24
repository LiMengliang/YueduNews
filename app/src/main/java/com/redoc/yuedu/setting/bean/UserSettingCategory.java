package com.redoc.yuedu.setting.bean;

import com.redoc.yuedu.bean.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limen on 2016/5/12.
 */
public class UserSettingCategory extends Category {
    @Override
    public List<String> getChannelsCreationSQL() {
        return new ArrayList<>();
    }

    // @Override
    // public void initializeDefaultChannelsInfo() {
    //     return;
    // }

    public UserSettingCategory(String categoryName, int categoryIconResoruceId) {
        super(categoryName, categoryIconResoruceId);
    }
}
