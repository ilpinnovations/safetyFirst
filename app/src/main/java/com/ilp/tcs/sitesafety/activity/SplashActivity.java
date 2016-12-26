package com.ilp.tcs.sitesafety.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.preference.SitePreference;

/**
 * Created by Azim Ansari on 5/24/2016.
 * First splash screen of the application
 */

/**
 * Modified by Abhishek Gupta on 8/30/2016.
 * Splash Screen of the application
 */
public class SplashActivity extends AppCompatActivity implements Runnable {

    /**
     * time value for timeout of splash screen.
     */
    private static final long SPLASH_TIMEOUT = 2000L;
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler = new Handler();
        mHandler.postDelayed(this, SPLASH_TIMEOUT);
    }

    @Override
    public void run() {

        if (SitePreference.getInstance().isFirstTimeLaunch()) {
            Intent intent = new Intent(this, SlideScreen.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mHandler != null) {
            mHandler.removeCallbacks(this);
        }
    }
}
