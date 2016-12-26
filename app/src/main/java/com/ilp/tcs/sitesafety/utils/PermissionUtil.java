package com.ilp.tcs.sitesafety.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by 1241575(Azim Ansari) on 7/21/2016.
 * Handling permission issues in Marshmallow and above.
 * Requesting permissions according to google guidelines.
 */
public class PermissionUtil {

    private static final int PERMISSION_REQUEST_CODE = 5;

    private static PermissionListener permissionListener;

    public static boolean hasPermission(@NonNull Context context, @NonNull String permission) {
        return ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(@NonNull Activity activity, @NonNull String permission) {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, PERMISSION_REQUEST_CODE);
    }

    public static boolean isPermanentlyDisabled(@NonNull Activity activity, @NonNull String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    public static void openPermissionSettings(@NonNull Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (permissionListener != null) {
                    permissionListener.onPermissionGranted(permissions[0]);
                    // Destroy listener after use
                    permissionListener = null;
                }
            } else {
                if (permissionListener != null) {
                    permissionListener.onPermissionDenied(permissions[0]);
                    // Destroy listener after use
                    permissionListener = null;
                }
            }
        }
    }

    public static void showPermissionDialog(@NonNull final Context context, CharSequence title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PermissionUtil.openPermissionSettings(context);
            }
        });
        builder.show();
    }

    public static void setPermissionListener(PermissionListener permissionListener) {
        PermissionUtil.permissionListener = permissionListener;
    }

    public interface PermissionListener {
        void onPermissionGranted(String permission);

        void onPermissionDenied(String permission);
    }
}
