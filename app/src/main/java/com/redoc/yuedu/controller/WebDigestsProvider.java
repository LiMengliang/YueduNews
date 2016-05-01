package com.redoc.yuedu.controller;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.news.bean.NewsDigest;
import com.redoc.yuedu.news.bean.NewsDigestsJsonParser;
import com.redoc.yuedu.utilities.network.VolleyUtilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limen on 2016/4/30.
 */
public class WebDigestsProvider implements DigestsProvider {
    private Response.Listener<JSONObject> onFetchLatestListener;
    private Response.Listener<JSONObject> onFetchMoreListener;

    public WebDigestsProvider(Response.Listener<JSONObject> onFetchLatestListener, Response.Listener<JSONObject> onFetchMoreListener) {
        this.onFetchLatestListener = onFetchLatestListener;
        this.onFetchMoreListener = onFetchMoreListener;
    }

    @Override
    public void fetchLatest(Channel channel,Context context, final DigestsAdapter digestsAdapter) {
       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(channel.getHttpLink(0), null, onFetchLatestListener,
            new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        VolleyUtilities.RequestQueue.add(jsonObjectRequest);
    }

    @Override
    public void fetchMore(Channel channel, int index, Context context, DigestsAdapter digestsAdapter) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(channel.getHttpLink(0), null, onFetchMoreListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        VolleyUtilities.RequestQueue.add(jsonObjectRequest);
    }
}
