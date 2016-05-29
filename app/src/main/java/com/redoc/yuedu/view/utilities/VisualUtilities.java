package com.redoc.yuedu.view.utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.redoc.yuedu.YueduApplication;

/**
 * Created by limen on 2016/5/29.
 */
public class VisualUtilities {
    public static Point getScreenSize(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point;
    }

    public static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static float px2Dp(Context context, int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return ((float)px)/scale;
    }
}
