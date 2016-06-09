package com.redoc.yuedu.news.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.redoc.yuedu.R;
import com.redoc.yuedu.YueduApplication;
import com.redoc.yuedu.bean.Channel;
import com.redoc.yuedu.controller.CacheDigestProvider;
import com.redoc.yuedu.controller.DigestCacheLatestResponseListener;
import com.redoc.yuedu.controller.DigestCacheMoreResponseListener;
import com.redoc.yuedu.controller.DigestsAdapter;
import com.redoc.yuedu.controller.DigestsProvider;
import com.redoc.yuedu.controller.WebDigestsProvider;
import com.redoc.yuedu.news.View.NewsDigestView;
import com.redoc.yuedu.news.bean.NewsChannel;
import com.redoc.yuedu.news.bean.NewsDigest;
import com.redoc.yuedu.news.bean.NewsDigestsJsonParser;
import com.redoc.yuedu.utilities.network.NetworkUtilities;

import org.json.JSONArray;
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
    private WebDigestsProvider webDigestsProvider;
    private CacheDigestProvider cacheDigestprovider;
    private List<NewsDigest> newsDigests;
    // record number of digest images loadded while fetchLatest, in fetchLatest() will set this value to 0,
    // which means each time call a fetchLatest(), adapter will load 10 images automatically, regardless if
    // list is scrolling.
    private int countLoadLatestDigestImages = 0;
    private final int maxLoadLatestDigestIamges = 10;

    public NewsDigestsAdapter(Context context, NewsChannel channel) {
        this.context = context;
        this.channel = channel;
        // TODO: Combine web and cache provider here
        webDigestsProvider = new WebDigestsProvider(new NewsDigestLatestResponseListener(), new NewsDigestMoreResponseListener());
        cacheDigestprovider = new CacheDigestProvider(new NewsDigestCacheLatestResponseListener(), new NewsDigsetCacheMoreResponseListener());
        newsDigests = new ArrayList<NewsDigest>();
    }

    @Override
    public void fetchLatest() {
        // TODO: only use web provider when network is available
        newsDigests.clear();
        DigestsProvider digestsProvider = getDigestProvider();
        if(digestsProvider == cacheDigestprovider) {
            Toast toast = Toast.makeText(context, YueduApplication.Context.getResources().getString(R.string.no_network_read_cache),
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        digestsProvider.fetchLatest(channel, context, this);
        index = 20;
        countLoadLatestDigestImages = 0;
    }

    @Override
    public void fetchMore() {
        // TODO: only use web provider when network is available
        getDigestProvider().fetchMore(channel, index, context, this);
        index += 20;
    }

    @Override
    public int getCount() {
        return newsDigests.size();
    }

    @Override
    public Object getItem(int position) {
        return newsDigests.get(position);
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
        if(maxLoadLatestDigestIamges > countLoadLatestDigestImages) {
            countLoadLatestDigestImages++;
         ((NewsDigestView)convertView.getTag()).loadImages(newsDigests.get(position));
        }
        else {
            ((NewsDigestView)convertView.getTag()).clearImages();
        }
        // If position == Count - 10, we need to cache more news
        if(position == getCount() - 10) {
            fetchMore();
        }
        return convertView;
    }

    private DigestsProvider getDigestProvider() {
        if(NetworkUtilities.isWifiAvailable()) {
            return webDigestsProvider;
        }
        return cacheDigestprovider;
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
                if(newsDigests.size() > 0) {
                    updateNewsDigestsToEnd(newsDigests);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class NewsDigestCacheLatestResponseListener implements DigestCacheLatestResponseListener {

        @Override
        public void onResponse(JSONObject digestJSONObject) {
            try {
                List<NewsDigest> newsDigests = NewsDigestsJsonParser.instance.parseJsonToNewsDigestModels(digestJSONObject, channel);
                updateNewsDigstsToStart(newsDigests);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class NewsDigsetCacheMoreResponseListener implements DigestCacheMoreResponseListener {

        @Override
        public void onResponse(JSONObject jsonObject) {
            try {
                List<NewsDigest> newsDigests = NewsDigestsJsonParser.instance.parseJsonToNewsDigestModels(jsonObject, channel);
                if(newsDigests.size() > 0) {
                    updateNewsDigestsToEnd(newsDigests);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
