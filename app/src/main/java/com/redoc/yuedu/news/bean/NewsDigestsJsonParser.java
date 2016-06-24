package com.redoc.yuedu.news.bean;

import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.utilities.json.JsonUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limen on 2016/4/30.
 */
public class NewsDigestsJsonParser {
    public static final String title = "title";
    public static final String digest = "digest";
    public static final String docid = "docid";
    public static final String source = "source";
    public static final String ptime = "ptime";
    public static final String imgextra = "imgextra";
    public static final String imgsrc = "imgsrc";
    public static final String url = "url";
    public static final String photoSetId = "photosetID";

    public static NewsDigestsJsonParser instance = new NewsDigestsJsonParser();

    public List<NewsDigest> parseJsonToNewsDigestModels(JSONObject jsonObject,
                                                        Channel channel) throws JSONException {
        List<NewsDigest> newsDigests = new ArrayList<NewsDigest>();
        JSONArray jsonArray = jsonObject.getJSONArray(channel.getChannelId());
        for(int i = 0; i < jsonArray.length(); i++) {
            NewsDigest newsDigest = new NewsDigest();
            JSONObject digestJSONObject = jsonArray.getJSONObject(i);
            newsDigest.setDigestTitle(JsonUtilities.getString(title, digestJSONObject));
            newsDigest.setDigset(JsonUtilities.getString(digest, digestJSONObject));
            newsDigest.setDocId(JsonUtilities.getString(docid, digestJSONObject));
            newsDigest.setSource(JsonUtilities.getString(source, digestJSONObject));
            newsDigest.setTime(JsonUtilities.getString(ptime, digestJSONObject));
            if(digestJSONObject.has(imgextra)) {
                JSONArray imageSourceJSONArray = digestJSONObject.getJSONArray(imgextra);
                for(int j = 0; j < imageSourceJSONArray.length(); j++) {
                    newsDigest.addDigestImage(JsonUtilities.getString(imgsrc, imageSourceJSONArray.getJSONObject(j)));
                }
            }
            newsDigest.addDigestImage(JsonUtilities.getString(imgsrc, digestJSONObject));
            if(digestJSONObject.has(url)) {
                newsDigest.setNewsUrl(JsonUtilities.getString(url, digestJSONObject));
            }
            if(digestJSONObject.has(photoSetId)) {
                newsDigest.setPhotoSetId(JsonUtilities.getString(photoSetId, digestJSONObject));
            }
            newsDigests.add(newsDigest);
        }
        return newsDigests;
    }

    public List<NewsDigest> parseJsonToNewsDigestModels(JSONArray jsonArray,
                                                        NewsChannel channel) throws JSONException {
        List<NewsDigest> digests = new ArrayList<NewsDigest>();
        int length = jsonArray.length();
        for(int i = 0; i < length; i++) {
            JSONObject jsonObject = (JSONObject)jsonArray.get(i);
            digests.addAll(parseJsonToNewsDigestModels(jsonObject, channel));
        }
        return digests;
    }
}
