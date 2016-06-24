package com.redoc.yuedu.bean;

import java.util.List;

/**
 * Created by limen on 2016/4/30.
 */
public abstract class Category {
    private String categoryName;
    public String getCategoryName() {
        return categoryName;
    }

    public abstract List<String> getChannelsCreationSQL();

    // public abstract void initializeDefaultChannelsInfo();

    private int categoryIconResourceId;
    public int getCategoryIconResourceId() {
        return categoryIconResourceId;
    }

    protected Category(String categoryName, int categoryIconResourceId) {
        this.categoryName = categoryName;
        this.categoryIconResourceId = categoryIconResourceId;
    }
}
