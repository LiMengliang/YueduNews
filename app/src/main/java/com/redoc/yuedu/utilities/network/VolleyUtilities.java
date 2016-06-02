package com.redoc.yuedu.utilities.network;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by limen on 2016/4/30.
 */
public class VolleyUtilities {
    public static RequestQueue RequestQueue;

    public static void initialize(Context context) {
        RequestQueue = Volley.newRequestQueue(context);
    }
}
