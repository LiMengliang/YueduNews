package com.redoc.yuedu.news.bean;

/**
 * Created by limen on 2016/7/1.
 */
public class PhotoInfo {
    private String imageUrl;
    private String note;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getNote() {
        return note;
    }

    public PhotoInfo(String imageUrl, String note) {
        this.imageUrl = imageUrl;
        this.note = note;
    }
}
