package com.redoc.yuedu.news.bean;

import java.util.List;

/**
 * Created by limen on 2016/6/30.
 */
public class PhotoSet {
    private List<PhotoInfo> photos;
    public List<PhotoInfo> getPhotos() {
        return photos;
    }

    private String description;
    public String getDescription() {
        return description;
    }

    public PhotoSet(List<PhotoInfo> photos, String description) {
        this.photos = photos;
        this.description = description;
    }
}
