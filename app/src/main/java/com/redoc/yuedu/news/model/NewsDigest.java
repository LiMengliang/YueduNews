package com.redoc.yuedu.news.model;

import com.redoc.yuedu.model.Digest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limen on 2016/4/30.
 */
public class NewsDigest extends Digest {
    private String docId;
    public String getDocId() {
        return docId;
    }
    public void setDocId(String docId) {
        this.docId = docId;
    }

    private String digest;
    public String getDigest() {
        return digest;
    }
    public void setDigset(String digest) {
        this.digest = digest;
    }

    private String tag;
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

    private List<String> mDigestImages = new ArrayList<String>();
    public List<String> getDigestImages() {
        return mDigestImages;
    }
    public void addDigestImage(String uri) {
        mDigestImages.add(uri);
    }

    private String newsUrl;
    public String getNewsUrl() {
        return newsUrl;
    }
    public void setNewsUrl(String url) {
        newsUrl = url;
    }

    private String photoSetId;
    public String getPhotoSetId() {
        return photoSetId;
    }
    public void setPhotoSetId(String photoSetId) {
        this.photoSetId = photoSetId;
    }

    private String source;
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
}
