package com.redoc.yuedu.setting.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.redoc.yuedu.R;

/**
 * Created by limen on 2016/5/28.
 */
public class AlbumnImageView {
    private View rootView;
    public View getRootView() {
        return rootView;
    }
    private ImageView selector;

    private ImageView imageView;
    public ImageView getImageView() {
        return imageView;
    }
    public AlbumnImageView(Context context) {
        rootView = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.widget_albumn_image_item, null);
        imageView = (ImageView)rootView.findViewById(R.id.albumn_image_item);
        selector = (ImageView)rootView.findViewById(R.id.selector);
    }

    public void setSelected(boolean selected) {
        if(selected) {
            selector.setVisibility(View.VISIBLE);
        }
        else {
            selector.setVisibility(View.INVISIBLE);
        }
    }
}
