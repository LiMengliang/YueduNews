package com.redoc.yuedu.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limen on 2016/4/30.
 */
public class MultiChannelCategory extends Category {
    @Override
    public List<String> getChannelsCreationSQL() {
        return new ArrayList<>();
    }

    public MultiChannelCategory(String categoryName, int categoryIconResoruceId) {
        super(categoryName, categoryIconResoruceId);
    }
}
