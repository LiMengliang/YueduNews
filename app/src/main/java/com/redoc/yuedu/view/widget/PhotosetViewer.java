package com.redoc.yuedu.view.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by limen on 2016/7/2.
 */
public class PhotosetViewer extends ViewPager implements OnChildMovingListener {
    /** 当前子控件是否处理拖动状态 */
    private boolean mChildIsBeingDragged=false;

    public PhotosetViewer(Context context) {
        super(context);
    }

    public PhotosetViewer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if(mChildIsBeingDragged)
            return false;
        return super.onInterceptTouchEvent(arg0);
    }

    @Override public void startDrag() {
        mChildIsBeingDragged=true;
    }

    @Override public void stopDrag() {
        mChildIsBeingDragged=false;
    }
}
