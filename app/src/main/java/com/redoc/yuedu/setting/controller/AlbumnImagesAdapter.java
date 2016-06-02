package com.redoc.yuedu.setting.controller;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.redoc.yuedu.setting.view.AlbumnImageView;
import com.redoc.yuedu.utilities.network.LoadImageUtilities;
import com.redoc.yuedu.view.utilities.VisualUtilities;

import java.util.List;

/**
 * Created by limen on 2016/5/28.
 */
public class AlbumnImagesAdapter extends BaseAdapter {

    private List<String> imagePaths;
    private Context context;
    private int imageXYSize;

    public AlbumnImagesAdapter(Context context, List<String> imagePaths) {
        this.context =context;
        this.imagePaths = imagePaths;
        imageXYSize = VisualUtilities.getScreenSize((Activity)context).x/3 - 10;
    }

    @Override
    public int getCount() {
        return imagePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return imagePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null) {
            AlbumnImageView albumnImageView = new AlbumnImageView(context);
            imageView = albumnImageView.getImageView();
            convertView = albumnImageView.getRootView();
            convertView.setTag(albumnImageView);
            convertView.setLayoutParams(new AbsListView.LayoutParams(imageXYSize, imageXYSize));
        }
        else {
            imageView = ((AlbumnImageView)convertView.getTag()).getImageView();
        }
        // if(position == 0) {
        //     imageView.setImageResource(R.drawable.camera);
        // }
        // else {
            Uri imageUri = Uri.parse("file://"+imagePaths.get(position));
        LoadImageUtilities.displayLocalImage(imageUri.toString(), imageView);
        // }
        return convertView;
    }
}
