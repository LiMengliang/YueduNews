package com.redoc.yuedu.setting.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.redoc.yuedu.R;
import com.redoc.yuedu.setting.controller.ImagesAdapter;

public class ImageSelection extends Activity {

    private GridView imagesGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);
        imagesGridView = (GridView)findViewById(R.id.images);
        imagesGridView.setAdapter(new ImagesAdapter());
    }
}
