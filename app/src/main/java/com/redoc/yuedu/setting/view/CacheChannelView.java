package com.redoc.yuedu.setting.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.redoc.yuedu.R;

/**
 * Created by limen on 2016/5/15.
 */
public class CacheChannelView {
    private View rootView;
    public View getRootView() {
        return rootView;
    }

    private TextView channelNameTextView;
    private CheckBox channelSelectedCheckBox;

    public String getChannelName() {
        return channelNameTextView.getText().toString();
    }
    public void setChannelName(String name) {
        channelNameTextView.setText(name);
    }

    public boolean getChecked() {
        return channelSelectedCheckBox.isChecked();
    }
    public void setChecked(boolean checked) {
        channelSelectedCheckBox.setChecked(checked);
    }

    public CacheChannelView(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.view_channel_cache, null);
        channelNameTextView = (TextView)rootView.findViewById(R.id.channel_name);
        channelSelectedCheckBox = (CheckBox)rootView.findViewById(R.id.channel_selector);
    }

}
