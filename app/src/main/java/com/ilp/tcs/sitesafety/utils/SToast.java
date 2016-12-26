package com.ilp.tcs.sitesafety.utils;

import android.widget.Toast;

import com.ilp.tcs.sitesafety.activity.SiteSafetyApplication;

/**
 * Created by 1220278 on 6/30/2016.
 */
public class SToast {

    public static void showSmallToast(String str) {
        Toast.makeText(SiteSafetyApplication.getContext(), str, Toast.LENGTH_SHORT).show();
    }

    public static void showSmallToast(int id) {
        Toast.makeText(SiteSafetyApplication.getContext(), id, Toast.LENGTH_SHORT).show();
    }

    public static void showBigToast(String str) {
        Toast.makeText(SiteSafetyApplication.getContext(), str, Toast.LENGTH_LONG).show();
    }

    public static void showBigToast(int id) {
        Toast.makeText(SiteSafetyApplication.getContext(), id, Toast.LENGTH_LONG).show();
    }
}
