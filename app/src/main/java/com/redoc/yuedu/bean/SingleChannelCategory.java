package com.redoc.yuedu.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limen on 2016/4/30.
 */
public class SingleChannelCategory extends Category {
    @Override
    public List<String> getChannelsCreationSQL() {
        return new ArrayList<>();
    }

    // @Override
    // public void initializeDefaultChannelsInfo() {
    //     return;
    // }

    public SingleChannelCategory(String categoryName, int categoryIconResoruceId) {
        super(categoryName, categoryIconResoruceId);
    }
}
