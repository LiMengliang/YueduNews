package com.redoc.yuedu.view.utilities;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.redoc.yuedu.R;

/**
 * Created by limen on 2016/5/7.
 */
public class AnimationUtilities {
    public static void startAlphaAnim(View view, float from, float to, long duration) {
        Animation anim = new AlphaAnimation(from, to);
        anim.setDuration(duration);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }

    public static void startRotateAnim(View view, float pivotx, float pivoty, float from, float to, long duration) {
        Animation anim = new RotateAnimation(from, to, Animation.RELATIVE_TO_SELF, pivotx,  Animation.RELATIVE_TO_SELF, pivoty);
        anim.setDuration(duration);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }

    public static void startScaleAnim(View view, float fromX, float toX, float fromY, float toY, long duration) {
        Animation anim = new ScaleAnimation(fromX, toX, fromY, toY);
        anim.setDuration(duration);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }

    public static void startTranslateAnim(View view, float fromX, float toX, float fromY, float toY, long duration) {
        Animation anim = new TranslateAnimation(fromX, toX, fromY, toY);
        anim.setDuration(duration);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }

}
