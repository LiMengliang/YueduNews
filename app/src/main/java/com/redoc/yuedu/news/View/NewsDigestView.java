package com.redoc.yuedu.news.View;

import android.app.Application;
import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.redoc.yuedu.R;
import com.redoc.yuedu.bean.Digest;
import com.redoc.yuedu.news.bean.NewsDigest;
import com.redoc.yuedu.utilities.network.ImageLoaderOption;
import com.redoc.yuedu.utilities.network.VolleyUtilities;
import com.redoc.yuedu.view.DelayLoadImageControl;
import com.redoc.yuedu.view.MainActivity;

import static com.redoc.yuedu.news.View.NewsDigestViewType.*;

/**
 * Created by limen on 2016/5/2.
 */
public class NewsDigestView implements DelayLoadImageControl {
    // Single image digest view
    private RelativeLayout singleImageDigest;
    private TextView singleImageDigestTitle;
    private TextView singleImageDigestSource;
    private TextView singleImageDigestContent;
    private ImageView singleImageDigestImage;
    private ImageLoader singleImageLoader;
    private ImageLoader.ImageListener singleImageLoadListener;
    // Multi images digest view
    private RelativeLayout multiImagesDigest;
    private TextView multiImageDigestTitle;
    private TextView multiImageDigestSource;
    private ImageView multiImageDigestImageA;
    private ImageView multiImageDigestImageB;
    private ImageView multiImageDigestImageC;
    // Photo set digest view
    private RelativeLayout photoSetDigest;
    private TextView photoSetDigestTitle;
    private TextView photoSetDigestSource;
    private ImageView photoSetDigestMainImage;
    private ImageView photoSetDigestImageA;
    private ImageView photoSetDigestImageB;

    private com.nostra13.universalimageloader.core.ImageLoader imageLoader;

    private FrameLayout rootView;
    public FrameLayout getRootView() {
        return rootView;
    }

    private NewsDigestViewType newsDigestViewType;

    public NewsDigestView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = (FrameLayout)inflater.inflate(R.layout.widget_digest, null);
        initializeAllViews(rootView);
        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        // TODO: Initialize view with digest model
    }

    public void updateView(NewsDigest digest) {
        if(digest.getDigestImages().size() == 1) {
            newsDigestViewType = SingleImage;
        }
        else if(digest.getPhotoSetId() != null) {
            newsDigestViewType = PhotSetDigest;
        }
        else {
            newsDigestViewType = MultiImages;
        }
        switch (newsDigestViewType) {
            case SingleImage: {
                multiImagesDigest.setVisibility(View.GONE);
                singleImageDigest.setVisibility(View.VISIBLE);
                photoSetDigest.setVisibility(View.GONE);
                singleImageDigestTitle.setText(digest.getDigestTitle());
                singleImageDigestContent.setText(digest.getDigest());
                singleImageDigestSource.setText(digest.getSource());
                break;
            }
            case MultiImages: {
                multiImagesDigest.setVisibility(View.VISIBLE);
                singleImageDigest.setVisibility(View.GONE);
                photoSetDigest.setVisibility(View.GONE);
                multiImageDigestTitle.setText(digest.getDigestTitle());
                multiImageDigestSource.setText(digest.getSource());
                break;
            }
            case PhotSetDigest:{
                multiImagesDigest.setVisibility(View.GONE);
                singleImageDigest.setVisibility(View.GONE);
                photoSetDigest.setVisibility(View.VISIBLE);
                photoSetDigestTitle.setText(digest.getDigestTitle());
                photoSetDigestSource.setText(digest.getSource());
            }
        }
    }

    @Override
    public void LoadImages(Digest digest) {
        if(digest.getClass() == NewsDigest.class) {
            NewsDigest newsDigest = (NewsDigest)digest;
            switch (newsDigestViewType) {
                case SingleImage:{
                    imageLoader.displayImage(newsDigest.getDigestImages().get(0), singleImageDigestImage, ImageLoaderOption.getListOptions());
                    break;
                }
                case MultiImages:{
                    imageLoader.displayImage(newsDigest.getDigestImages().get(0), multiImageDigestImageA, ImageLoaderOption.getListOptions());
                    imageLoader.displayImage(newsDigest.getDigestImages().get(1), multiImageDigestImageB, ImageLoaderOption.getListOptions());
                    imageLoader.displayImage(newsDigest.getDigestImages().get(2), multiImageDigestImageC, ImageLoaderOption.getListOptions());
                    break;
                }
                case PhotSetDigest: {
                    imageLoader.displayImage(newsDigest.getDigestImages().get(1), photoSetDigestMainImage, ImageLoaderOption.getListOptions());
                    imageLoader.displayImage(newsDigest.getDigestImages().get(0), photoSetDigestImageA, ImageLoaderOption.getListOptions());
                    imageLoader.displayImage(newsDigest.getDigestImages().get(2), photoSetDigestImageB, ImageLoaderOption.getListOptions());
                    break;
                }
            }
        }
    }

    private void initializeAllViews(View rootView) {
        singleImageDigest = (RelativeLayout)rootView.findViewById(R.id.single_image_digest);
        singleImageDigestTitle = (TextView)rootView.findViewById(R.id.single_image_digest_title);
        singleImageDigestContent = (TextView)rootView.findViewById(R.id.single_image_digest_digest);
        singleImageDigestSource = (TextView)rootView.findViewById(R.id.single_image_digest_source);
        singleImageDigestImage = (ImageView)rootView.findViewById(R.id.single_image_digest_image);
        singleImageLoader = new ImageLoader(VolleyUtilities.RequestQueue, VolleyUtilities.BitmapCache);
        singleImageLoadListener = ImageLoader.getImageListener(singleImageDigestImage,
                R.drawable.default_digset_image, R.drawable.default_digset_image);

        multiImagesDigest = (RelativeLayout)rootView.findViewById(R.id.multi_image_digest);
        multiImageDigestTitle = (TextView)rootView.findViewById(R.id.multi_image_digest_title);
        multiImageDigestSource = (TextView)rootView.findViewById(R.id.multi_image_digest_source);
        multiImageDigestImageA = (ImageView)rootView.findViewById(R.id.multi_image_digest_image_a);
        multiImageDigestImageB = (ImageView)rootView.findViewById(R.id.multi_image_digest_image_b);
        multiImageDigestImageC = (ImageView)rootView.findViewById(R.id.multi_image_digest_image_c);

        photoSetDigest = (RelativeLayout)rootView.findViewById(R.id.photo_set_digest);
        photoSetDigestTitle = (TextView)rootView.findViewById(R.id.photo_set_digest_title);
        photoSetDigestSource = (TextView)rootView.findViewById(R.id.photo_set_digest_source);
        photoSetDigestImageA = (ImageView)rootView.findViewById(R.id.photo_set_digest_right_image_a);
        photoSetDigestImageB = (ImageView)rootView.findViewById(R.id.photo_set_digest_right_image_b);
        photoSetDigestMainImage = (ImageView)rootView.findViewById(R.id.photo_set_digest_main_image);
    }
}
