package com.ilp.tcs.sitesafety.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.EditText;

import com.ilp.tcs.sitesafety.activity.SiteSafetyApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Azim Ansari on 5/24/2016.
 * Basic Utilities for the application
 */

/**
 *  Modified by Abhishek Gupta on 12/9/2016.
 */
public class Util {


    public static String getString(EditText e) {

        if (e == null) {
            return "";
        } else {
            return e.getText().toString().trim();
        }
    }

    public static String getString(TextInputLayout e) {

        if (e == null) {
            return "";
        } else if (e.getEditText() == null) {
            return "";
        } else {
            return e.getEditText().getText().toString().trim();
        }
    }

    public static void clearError(TextInputLayout til) {
        if (til != null)
            til.setError(null);
    }

    public static void showError(TextInputLayout til, @StringRes int id) {
        if (til != null)
            til.setError(SiteSafetyApplication.getContext().getResources().getString(id));
    }

    public static void showError(TextInputLayout til, String text) {
        if (til != null)
            til.setError(text);
    }

    /**
     * @param bitmap bitmap image to be converted to byte array
     * @return byte array from the bitmap
     */
    public static byte[] convertBitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    /**
     * @param image byte array to be converted to Bitmap image
     * @return corresponding Bitmap image
     */
    public static Bitmap convertBytesToBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.toString().trim().length() == 0;
    }

    /**
     * @param editText EditText to perform validation
     * @return true if the input is null or empty
     */
    public static boolean isEmpty(EditText editText) {
        if (editText == null)
            return true;
        else {
            String str = editText.getText().toString();
            return isEmpty(str);
        }
    }

    /**
     * @param til TextInputLayout to perform validation
     * @return true if the input is null or empty
     */
    public static boolean isEmpty(TextInputLayout til) {
        if (til == null)
            return true;
        else if (til.getEditText() == null) {
            return true;
        } else {
            String str = til.getEditText().getText().toString().trim();
            return isEmpty(str);
        }
    }

    public static File getExternalImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        String path = "file:" + image.getAbsolutePath();
        return image;
    }

    public static File getInternalImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_.jpg";
        return new File(SiteSafetyApplication.getContext().getFilesDir(), imageFileName);
    }


    /**
     * It reads the bitmap image from the given path with the given width and height.
     * It consumes less amount of memory by not reading the full size image
     *
     * @param path    Path of the image
     * @param targetW Width to read.
     * @param targetH Height to read.
     * @return returns the loaded bitmap image with targetW and targetH.
     */
    public static Bitmap getScaledBitmap(String path, int targetH, int targetW) {

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(path, bmOptions);
    }

    /**
     * @param path Path from which file part will be removed
     * @return returns new string
     */
    public static String removeFileFromPath(String path) {
        return path.startsWith("file:") ? path.substring(5) : path;
    }

    /**
     * Request the media scanner to scan a file and add it to the media database.
     *
     * @param context given context which can send broadcast.
     * @param path    file path to scan and add to media database.
     */
    public static void scanMedia(Context context, String path) {
        Log.i("Util scanMedia", "broadcast!");
    //    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            File file = new File(path);
            Uri uri = Uri.fromFile(file);
            Intent scanFileIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
            context.sendBroadcast(scanFileIntent);
       // } else {
       //     context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+Environment.getExternalStorageDirectory().getAbsolutePath()+"/HSE")));
      //;  }

    }

    public static Bitmap handleExifRotation(Bitmap bitmap, String path) {

        ExifInterface exif;
        int orientation = ExifInterface.ORIENTATION_NORMAL;
        try {
            exif = new ExifInterface(path);
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            DisplayMetrics display = SiteSafetyApplication.getContext().getResources().getDisplayMetrics();

            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();

            Bitmap bmScaled = Bitmap.createScaledBitmap(bmRotated, display.widthPixels, display.heightPixels, false);
            bmRotated.recycle();

            return bmScaled;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void geoTag(String imagePath, double latitude, double longitude) {
        try {
            ExifInterface exif = new ExifInterface(imagePath);
            int num1Lat = (int) Math.floor(latitude);
            int num2Lat = (int) Math.floor((latitude - num1Lat) * 60);
            double num3Lat = (latitude - ((double) num1Lat + ((double) num2Lat / 60))) * 3600000;

            int num1Lon = (int) Math.floor(longitude);
            int num2Lon = (int) Math.floor((longitude - num1Lon) * 60);
            double num3Lon = (longitude - ((double) num1Lon + ((double) num2Lon / 60))) * 3600000;

            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, num1Lat + "/1," + num2Lat + "/1," + num3Lat + "/1000");
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, num1Lon + "/1," + num2Lon + "/1," + num3Lon + "/1000");


            if (latitude > 0) {
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "N");
            } else {
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "S");
            }

            if (longitude > 0) {
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "E");
            } else {
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "W");
            }

            exif.saveAttributes();
        } catch (IOException e) {
            Log.e("error", e.getLocalizedMessage());
        }

    }

    public static Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth)
    {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.max(xScale, yScale);

        //get the resulting size after scaling
        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        //figure out where we should translate to
        float dx = (newWidth - scaledWidth) / 2;
        float dy = (newHeight - scaledHeight) / 2;

        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
        Canvas canvas = new Canvas(dest);
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        matrix.postTranslate(dx, dy);
        canvas.drawBitmap(source, matrix, null);
        return dest;
    }

}
