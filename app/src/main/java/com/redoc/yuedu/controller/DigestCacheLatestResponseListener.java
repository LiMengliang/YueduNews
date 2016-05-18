package com.redoc.yuedu.controller;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by limen on 2016/5/15.
 */
public interface DigestCacheLatestResponseListener {
    void onResponse(JSONArray digestJSONArray);
}
