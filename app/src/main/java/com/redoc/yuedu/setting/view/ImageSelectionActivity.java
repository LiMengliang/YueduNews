package com.redoc.yuedu.setting.view;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toolbar;

import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.redoc.yuedu.R;
import com.redoc.yuedu.setting.controller.AlbumnImagesAdapter;
import com.redoc.yuedu.utilities.cache.ACacheUtilities;
import com.redoc.yuedu.utilities.cache.CacheUtilities;
import com.redoc.yuedu.utilities.network.LoadImageUtilities;
import com.redoc.yuedu.view.widget.ToolBar;

import java.util.ArrayList;
import java.util.List;

public class ImageSelectionActivity extends Activity {

    private GridView imagesGridView;
    private List<String> allIconPaths;
    private ToolBar toolbar;
    private String selectedPath;
    private Context context;
    public static String selectedIconPath = "SelectedIcon";
    public static String usreIconCacheKey = "UserIconCacheKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        context = this;
        allIconPaths = getImages();
        imagesGridView = (GridView)findViewById(R.id.images);
        imagesGridView.setAdapter(new AlbumnImagesAdapter(this, allIconPaths));
        imagesGridView.setOnScrollListener(new PauseOnScrollListener(
                LoadImageUtilities.imageLoader, true, true));
        imagesGridView.setOnItemClickListener(new ItemClickListener());
        toolbar = (ToolBar)findViewById(R.id.toolbar);
        toolbar.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntent().putExtra(selectedIconPath, "");
                setResult(RESULT_OK, getIntent());
                finish();
            }
        });
        toolbar.setRightButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, getIntent());
                Bitmap bitmap = LoadImageUtilities.loadBitmapFromUriAsync("file://"+selectedPath);
                ACacheUtilities.setCacheImage(context, usreIconCacheKey, bitmap);
                finish();
            }
        });
        getIntent().putExtra(selectedIconPath, "");
        toolbar.setEnableToRightButton(false);
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

    class ItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            toolbar.setEnableToRightButton(true);
            for(int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                AlbumnImageView albumnImageView = (AlbumnImageView)child.getTag();
                if(child == view) {
                    albumnImageView.setSelected(true);
                }
                else {
                    albumnImageView.setSelected(false);
                }
            }
            selectedPath = allIconPaths.get(position);
            getIntent().putExtra(selectedIconPath, allIconPaths.get(position));
        }
    }
}
