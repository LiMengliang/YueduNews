package com.redoc.yuedu;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.redoc.yuedu.view.MainActivity;
import com.redoc.yuedu.view.utilities.AnimationUtilities;

/**
 * Splash screen activity.
 */
public class SplashActivity extends AppCompatActivity
{
    private View splashImage = null;
    private View mStartButton = null;
    private View mSplashIcon = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        // splashImage = findViewById(R.id.splashImage);
        // splashImage.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
        //         | View.SYSTEM_UI_FLAG_FULLSCREEN
        //         | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        //         | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        //         | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        //         | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        mStartButton = findViewById(R.id.startButton);
        mSplashIcon = findViewById(R.id.splashIconImage);

        mStartButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ComponentName componentName = new ComponentName(SplashActivity.this, MainActivity.class);
                Intent intent = new Intent();
                intent.setComponent(componentName);
                startActivity(intent);
                finish();
            }
        });
        AnimationUtilities.startAlphaAnim(mStartButton, 0, 1, 1000);
        AnimationUtilities.startAlphaAnim(mSplashIcon, 0, 1, 1000);
    }
}