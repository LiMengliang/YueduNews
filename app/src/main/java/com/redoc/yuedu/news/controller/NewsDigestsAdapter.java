package com.redoc.yuedu.news.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.controller.DigestsAdapter;
import com.redoc.yuedu.controller.DigestsProvider;
import com.redoc.yuedu.controller.WebDigestsProvider;
import com.redoc.yuedu.news.bean.NewsChannel;
import com.redoc.yuedu.news.bean.NewsDigest;
import com.redoc.yuedu.news.bean.NewsDigestsJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limen on 2016/4/30.
 */
public class NewsDigestsAdapter extends DigestsAdapter {

    private NewsChannel channel;
    private int index = 0;
    private DigestsProvider digestsProvider;
    private List<NewsDigest> newsDigests;

    public NewsDigestsAdapter(NewsChannel channel) {
        this.channel = channel;
        digestsProvider = new WebDigestsProvider(new NewsDigestLatestResponseListener(), new NewsDigestMoreResponseListener());
        newsDigests = new ArrayList<NewsDigest>();
    }

    @Override
    public void fetchLatest(Context context) {
        // TODO: only use web provider when network is available
        digestsProvider.fetchLatest(channel, context, this);
        index += 20;
    }

    @Override
    public void fetchMore(Context context) {
        // TODO: only use web provider when network is available
        digestsProvider.fetchMore(channel, index, context, this);
        index += 20;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    private void updateNewsDigstsToStart(List<NewsDigest> digests) {
        digests.addAll(newsDigests);
        newsDigests = digests;
        notifyDataSetChanged();
    }

    private void updateNewsDigestsToEnd(List<NewsDigest> digests) {
        newsDigests.addAll(digests);
        notifyDataSetChanged();
    }

    class NewsDigestLatestResponseListener implements Response.Listener<JSONObject> {
        @Override
        public void onResponse(JSONObject jsonObject) {
            // TODO: Update NewsDigestAdapter here
            try {
                List<NewsDigest> newsDigests = NewsDigestsJsonParser.instance.parseJsonToNewsDigestModels(jsonObject, channel);
                updateNewsDigstsToStart(newsDigests);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class NewsDigestMoreResponseListener implements Response.Listener<JSONObject> {
        @Override
        public void onResponse(JSONObject jsonObject) {
            // TODO: Update NewsDigestAdapter here
            try {
                List<NewsDigest> newsDigests = NewsDigestsJsonParser.instance.parseJsonToNewsDigestModels(jsonObject, channel);
                updateNewsDigestsToEnd(newsDigests);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
