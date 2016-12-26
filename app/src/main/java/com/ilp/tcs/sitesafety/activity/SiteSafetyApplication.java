package com.ilp.tcs.sitesafety.activity;

import android.app.Application;
import android.content.Context;

/**
 * Created by Azim Ansari on 5/24/2016.
 * Application class
 */
public class SiteSafetyApplication extends Application {

    /**
     * Application context
     */
    private static SiteSafetyApplication sContext;

    /**
     * @return returns Application context
     */
    public static SiteSafetyApplication getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
