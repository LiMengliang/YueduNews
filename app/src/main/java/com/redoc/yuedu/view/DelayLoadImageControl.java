package com.redoc.yuedu.view;

import com.redoc.yuedu.model.Digest;

/**
 * Created by limen on 2016/5/2.
 */
public interface DelayLoadImageControl {
    void clearImages();
    void loadImages(Digest digest);
    boolean isImageCleared();
}
