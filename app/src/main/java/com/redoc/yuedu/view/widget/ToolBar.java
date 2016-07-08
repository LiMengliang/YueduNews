package com.redoc.yuedu.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.redoc.yuedu.R;

import java.nio.charset.Charset;
import java.util.zip.Inflater;

/**
 * Created by limen on 2016/5/29.
 */
public class ToolBar extends RelativeLayout {
    private TextView titleText;
    private Button rightButton;
    private LinearLayout backButton;

    public ToolBar(Context context) {
        this(context, null);
    }

    public ToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_action_bar, this, true);
        titleText = (TextView)findViewById(R.id.toolbar_title);
        rightButton = (Button)findViewById(R.id.toolbar_right_buttom);
        backButton = (LinearLayout)findViewById(R.id.titleAndBack);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ToolbarWithButton);
        CharSequence toolbarTitle = a.getText(R.styleable.ToolbarWithButton_toolbar_title);
        if(toolbarTitle != null) {
            titleText.setText(toolbarTitle);
        }
        CharSequence rightButtonText = a.getText(R.styleable.ToolbarWithButton_toolbar_button_text);
        if(rightButtonText != null) {
            rightButton.setText(rightButtonText);
        }
        Drawable background = a.getDrawable(R.styleable.ToolbarWithButton_toolbar_background);
        this.setBackground(background);
        a.recycle();
    }

    public ToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBackClickListener(OnClickListener onClickListener) {
        backButton.setOnClickListener(onClickListener);
    }

    public void setRightButtonClickListener(OnClickListener onClickListener) {
        rightButton.setOnClickListener(onClickListener);
    }

    public void setEnableToRightButton(boolean enabled) {
        rightButton.setEnabled(enabled);
    }
}
