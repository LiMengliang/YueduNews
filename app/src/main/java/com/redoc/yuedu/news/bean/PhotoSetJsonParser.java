package com.redoc.yuedu.news.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limen on 2016/6/30.
 */
public class PhotoSetJsonParser {
    private String descriptionKey = "desc";
    private String photosKey = "photos";
    private String imageUrlKey = "imgurl";
    private String noteKey = "note";
    public PhotoSet parseJsonToPhotoSet(JSONObject jsonObject) throws JSONException {
        String description = jsonObject.getString(descriptionKey);
        List<PhotoInfo> photoSetList = new ArrayList<>();
        JSONArray photosJsonArray = jsonObject.getJSONArray(photosKey);
        for(int i = 0; i < photosJsonArray.length(); i++) {
            JSONObject photoObject = photosJsonArray.getJSONObject(i);
            String imageUrl = photoObject.getString(imageUrlKey);
            String note = photoObject.getString(noteKey);
            photoSetList.add(new PhotoInfo(imageUrl, note));
        }
        return new PhotoSet(photoSetList, description);
    }
}
