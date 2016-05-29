package com.redoc.yuedu.setting.view;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.WindowManager;
import android.widget.GridView;

import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.redoc.yuedu.R;
import com.redoc.yuedu.setting.controller.AlbumnImagesAdapter;
import com.redoc.yuedu.utilities.network.LoadImageUtilities;

import java.util.ArrayList;
import java.util.List;

public class ImageSelectionActivity extends Activity {

    private GridView imagesGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        imagesGridView = (GridView)findViewById(R.id.images);
        imagesGridView.setAdapter(new AlbumnImagesAdapter(this, getImages()));
        imagesGridView.setOnScrollListener(new PauseOnScrollListener(
                LoadImageUtilities.imageLoader, true,true));
    }

    private List<String> getImages() {
        Uri externalImages = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        List<String> imagePaths = new ArrayList<>();
        Cursor mCursor = contentResolver.query(externalImages, null, MediaStore.Images.Media.MIME_TYPE + "=? or "
            + MediaStore.Images.Media.MIME_TYPE + "=?", new String[] { "image/jpeg", "image/png" },
            MediaStore.Images.Media.DATE_MODIFIED);
        if (mCursor == null) {
          return imagePaths;
        }
        while(mCursor.moveToNext()) {
            String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            imagePaths.add(path);
        }
        return imagePaths;
    }
}
