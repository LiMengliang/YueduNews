package com.redoc.yuedu.news.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.redoc.yuedu.R;
import com.redoc.yuedu.bean.Digest;
import com.redoc.yuedu.news.bean.NewsDigest;
import com.redoc.yuedu.view.DelayLoadImageControl;

/**
 * Created by limen on 2016/5/2.
 */
public class NewsDigestView implements DelayLoadImageControl {
    private TextView textView;
    private FrameLayout rootView;
    public FrameLayout getRootView() {
        return rootView;
    }

    public NewsDigestView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = (FrameLayout)inflater.inflate(R.layout.widget_digest, null);
        textView = (TextView)rootView.findViewById(R.id.digest_title);
        // TODO: Initialize view with digest model
    }

    public void updateView(NewsDigest digest) {
        textView.setText(digest.getDigestTitle());
    }

    @Override
    public void LoadImages() {

    }
}
