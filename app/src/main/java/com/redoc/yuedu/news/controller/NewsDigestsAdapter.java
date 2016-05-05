package com.redoc.yuedu.news.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.controller.DigestsAdapter;
import com.redoc.yuedu.controller.DigestsProvider;
import com.redoc.yuedu.controller.WebDigestsProvider;
import com.redoc.yuedu.news.View.NewsDigestView;
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

    private Context context;
    private NewsChannel channel;
    private int index = 0;
    private DigestsProvider digestsProvider;
    private List<NewsDigest> newsDigests;

    public NewsDigestsAdapter(Context context, NewsChannel channel) {
        this.context = context;
        this.channel = channel;
        digestsProvider = new WebDigestsProvider(new NewsDigestLatestResponseListener(), new NewsDigestMoreResponseListener());
        newsDigests = new ArrayList<NewsDigest>();
    }

    @Override
    public void fetchLatest() {
        // TODO: only use web provider when network is available
        digestsProvider.fetchLatest(channel, context, this);
        index += 20;
    }

    @Override
    public void fetchMore() {
        // TODO: only use web provider when network is available
        digestsProvider.fetchMore(channel, index, context, this);
        index += 20;
    }

    @Override
    public int getCount() {
        return newsDigests.size();
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
        if(convertView == null) {
            NewsDigestView newsDigestView = new NewsDigestView(context);
            convertView = newsDigestView.getRootView();
            convertView.setTag(newsDigestView);
        }
        ((NewsDigestView)convertView.getTag()).updateView(newsDigests.get(position));
        ((NewsDigestView)convertView.getTag()).LoadImages(newsDigests.get(position));
        return convertView;
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
