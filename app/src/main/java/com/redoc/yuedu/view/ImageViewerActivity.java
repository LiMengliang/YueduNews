package com.redoc.yuedu.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.redoc.yuedu.R;
import com.redoc.yuedu.YueduApplication;
import com.redoc.yuedu.news.bean.Consts;
import com.redoc.yuedu.news.bean.PhotoInfo;
import com.redoc.yuedu.news.bean.PhotoSet;
import com.redoc.yuedu.news.bean.PhotoSetJsonParser;
import com.redoc.yuedu.utilities.network.LoadImageUtilities;
import com.redoc.yuedu.utilities.network.VolleyUtilities;
import com.redoc.yuedu.view.widget.OnChildMovingListener;
import com.redoc.yuedu.view.widget.PhotosetViewer;
import com.redoc.yuedu.view.widget.ToolBar;
import com.redoc.yuedu.view.widget.ZoomableImageViewer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ImageViewerActivity extends AppCompatActivity {

    private PhotosetViewer imagesViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ToolBar toolbar = (ToolBar)findViewById(R.id.toolbar);
        toolbar.setBackClickListener(new BackClickedListener(this));
        imagesViewPager = (PhotosetViewer)findViewById(R.id.imagesViewPager);
        ImagesAdapter imagesAdapter = new ImagesAdapter();
        imagesViewPager.setAdapter(imagesAdapter);
        // imagesViewPager.setOnTouchListener(new View.OnTouchListener() {
        //     @Override
        //     public boolean onTouch(View v, MotionEvent event) {
        //         Toast toast = Toast.makeText(ImageViewerActivity.this, "Test", Toast.LENGTH_LONG);
        //         toast.show();
        //         return false;
        //     }
        // });
        String photosetUrl = getIntent().getStringExtra(Consts.PHOTO_SET_URL);
        getDetails(photosetUrl, imagesAdapter);
    }

    private void getDetails(String url, ImagesAdapter imagesAdapter) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new PhotosetDetailResponseListener(imagesAdapter),
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        VolleyUtilities.RequestQueue.add(jsonObjectRequest);
    }

    static class PhotosetDetailResponseListener implements Response.Listener<JSONObject> {
        private ImagesAdapter photoAdapter;

        public PhotosetDetailResponseListener(ImagesAdapter photoAdapter) {
            super();
            this.photoAdapter = photoAdapter;
        }

        @Override
        public void onResponse(JSONObject jsonObject) {
            PhotoSetJsonParser photoSetJsonParser = new PhotoSetJsonParser();
            try {
                PhotoSet photoset = photoSetJsonParser.parseJsonToPhotoSet(jsonObject);
                photoAdapter.setPhotos(photoset.getPhotos());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    static class BackClickedListener implements View.OnClickListener {
        private ImageViewerActivity activity;
        public BackClickedListener(ImageViewerActivity activity) {
            this.activity = activity;
        }
        @Override
        public void onClick(View v) {
            activity.finish();
        }
    }

    static class ImagesAdapter extends PagerAdapter {

        private List<PhotoInfo> photos = new ArrayList<>();


        @Override
        public int getCount() {
            return photos.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = (LayoutInflater) YueduApplication.Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            RelativeLayout photoContainer = (RelativeLayout)inflater.inflate(R.layout.widget_photo_description, null);
            ZoomableImageViewer image = (ZoomableImageViewer)photoContainer.findViewById(R.id.photo);
            image.setOnMovingListener((OnChildMovingListener) container);
            LoadImageUtilities.loadBitmapFromUriAsync(photos.get(position).getImageUrl(),
                    new PhotoLoadingImageListener(image));
            final TextView description = (TextView)photoContainer.findViewById(R.id.photo_description);
            description.setText(String.format("%d/%d\t\t\t%s", position+1, photos.size(), photos.get(position).getNote()));
            photoContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (description.getVisibility() == View.VISIBLE) {
                        description.setVisibility(View.INVISIBLE);
                    } else {
                        description.setVisibility(View.VISIBLE);
                    }
                }
            });
            container.addView(photoContainer);
            return photoContainer;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView((View)object);
        }

        public void setPhotos(List<PhotoInfo> photos) {
            this.photos = photos;
            notifyDataSetChanged();
        }

        class PhotoLoadingImageListener implements ImageLoadingListener {
            private ZoomableImageViewer imageView;

            public PhotoLoadingImageListener(ZoomableImageViewer imageView) {
                this.imageView = imageView;
            }

            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        }
    }
}
