package com.redoc.yuedu.bean;

/**
 * Created by limen on 2016/4/30.
 */
public class Category {
    private String categoryName;
    public String getCategoryName() {
        return categoryName;
    }

    private int categoryIconResourceId;
    public int getCategoryIconResourceId() {
        return categoryIconResourceId;
    }

    protected Category(String categoryName, int categoryIconResourceId) {
        this.categoryName = categoryName;
        this.categoryIconResourceId = categoryIconResourceId;
    }
}
