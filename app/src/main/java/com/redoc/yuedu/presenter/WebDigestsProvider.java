package com.redoc.yuedu.presenter;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.redoc.yuedu.model.Channel;
import com.redoc.yuedu.utilities.network.VolleyUtilities;

import org.json.JSONObject;

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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(channel.getHttpLink(index), null, onFetchMoreListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        VolleyUtilities.RequestQueue.add(jsonObjectRequest);
    }
}
