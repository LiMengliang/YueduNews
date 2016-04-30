package com.redoc.yuedu.bean;

/**
 * Created by limen on 2016/4/30.
 */
public abstract class Category {
    private String categoryName;
    public String getCategoryName() {
        return categoryName;
    }
    protected void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    private int categoryIconResourceId;
    public int getCategoryIconResourceId() {
        return categoryIconResourceId;
    }
    protected void setCategoryIconResourceId(int id) {
        categoryIconResourceId = id;
    }
}
