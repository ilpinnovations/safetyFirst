package com.ilp.tcs.sitesafety.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.ilp.tcs.sitesafety.modals.Image;
import com.ilp.tcs.sitesafety.utils.SQLiteDataTypes;

import java.util.ArrayList;

/**
 * Created by Azim Ansari on 5/26/2016.
 * Table to store images
 */
public class TblImages implements BaseColumns {

    /**
     * String Table Name
     */
    public static final String TABLE_NAME = "TblImages";

    /**
     * String for Column SurveyId
     */
    public static final String COLUMN_SURVEY_ID = "survey_id";
    /**
     * String for Column PointId
     */
    public static final String COLUMN_POINT_ID = "point_id";
    /**
     * String for Column image
     */
    public static final String COLUMN_IMAGE = "image";

    public static final String COLUMN_LOCATION = "location";

    /**
     * Image cation string
     */
    public static final String COLUMN_CAPTION = "caption";

    /**
     * String for comma separation
     */
    private static final String COMMA_SEP = " , ";

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    BaseColumns._ID + SQLiteDataTypes.TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP +
                    COLUMN_SURVEY_ID + SQLiteDataTypes.TYPE_INTEGER + " NOT NULL " + COMMA_SEP +
                    COLUMN_POINT_ID + SQLiteDataTypes.TYPE_INTEGER + " NOT NULL " + COMMA_SEP +
                    COLUMN_IMAGE + SQLiteDataTypes.TYPE_TEXT + " NOT NULL " + COMMA_SEP +
                    COLUMN_LOCATION + SQLiteDataTypes.TYPE_TEXT + COMMA_SEP +
                    COLUMN_CAPTION + SQLiteDataTypes.TYPE_TEXT + COMMA_SEP +                                        // COLUMN_CAPTION allows null or empty string
                    "FOREIGN KEY" + " ( " + COLUMN_SURVEY_ID + " ) " + " REFERENCES " +
                    TblSurvey.TABLE_NAME + " ( " + TblSurvey._ID + " ) " + " ON DELETE CASCADE " + COMMA_SEP +
                    "FOREIGN KEY" + " ( " + COLUMN_POINT_ID + " ) " + " REFERENCES " +
                    TblCategoryPoints.TABLE_NAME + " ( " + TblCategoryPoints._ID + " ) " + " ON DELETE CASCADE " +
                    " )";

    /**
     * @param id primary key by reference of which data fetching will take place
     * @return object of image class.
     */
    public static Image getImageById(long id) {
        DbHelper helper = DbHelper.getInstance();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, _ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null);
        db.close();
        if (cursor != null && cursor.moveToFirst()) {

            long _id = cursor.getLong(cursor.getColumnIndex(_ID));
            long surveyId = cursor.getLong(cursor.getColumnIndex(COLUMN_SURVEY_ID));
            long subcategoryId = cursor.getInt(cursor.getColumnIndex(COLUMN_POINT_ID));
            String image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
            String location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
            String caption = cursor.getString(cursor.getColumnIndex(COLUMN_CAPTION));

            Image img = new Image();
            img.setImageId(_id);
            img.setSurveyId(surveyId);
            img.setSubcategoryId(subcategoryId);
            img.setImagePath(image);
            img.setLocation(location);
            img.setCaption(caption);

            cursor.close();
            return img;
        }

        return null;
    }


    /**
     * Function that gives array list of images.
     *
     * @return array list of images.
     */
    public static ArrayList<Image> getAllImages() {

        ArrayList<Image> images;

        DbHelper helper = DbHelper.getInstance();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            images = new ArrayList<>();

            do {
                long _id = cursor.getLong(cursor.getColumnIndex(_ID));
                long surveyId = cursor.getLong(cursor.getColumnIndex(COLUMN_SURVEY_ID));
                long subcategoryId = cursor.getInt(cursor.getColumnIndex(COLUMN_POINT_ID));
                String image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
                String location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
                String caption = cursor.getString(cursor.getColumnIndex(COLUMN_CAPTION));

                Image img = new Image();
                img.setImageId(_id);
                img.setSurveyId(surveyId);
                img.setSubcategoryId(subcategoryId);
                img.setImagePath(image);
                img.setLocation(location);
                img.setCaption(caption);

                images.add(img);
            }
            while (cursor.moveToNext());

            cursor.close();
            return images;
        }

        return null;
    }

    /**
     * @param surveyId by basis of survey id the data will be fetched.
     * @param pointId  by basis of survey id the data will be fetched.
     * @return array list of images.
     */
    public static ArrayList<Image> getImagesBySurveyIdAndPointId(long surveyId, long pointId) {

        ArrayList<Image> images;

        DbHelper helper = DbHelper.getInstance();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_SURVEY_ID + " = ? AND " + COLUMN_POINT_ID + " = ? ", new String[]{String.valueOf(surveyId), String.valueOf(pointId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            images = new ArrayList<>();

            do {
                long _id = cursor.getLong(cursor.getColumnIndex(_ID));
                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_SURVEY_ID));
                long subcategoryId = cursor.getInt(cursor.getColumnIndex(COLUMN_POINT_ID));
                String image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
                String location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
                String caption = cursor.getString(cursor.getColumnIndex(COLUMN_CAPTION));

                Image img = new Image();
                img.setImageId(_id);
                img.setSurveyId(id);
                img.setSubcategoryId(subcategoryId);
                img.setImagePath(image);
                img.setLocation(location);
                img.setCaption(caption);

                images.add(img);
            }
            while (cursor.moveToNext());

            cursor.close();
            return images;
        }

        return null;
    }

    public static void updateCaption(String imagePath, String caption) {
        SQLiteDatabase db = DbHelper.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CAPTION, caption);
        int result = db.update(TABLE_NAME, values, COLUMN_IMAGE + " = ?", new String[]{imagePath});
        if (result < 1) {
            Log.d("fail", "Error");
        }
        db.close();
    }

    public static String getCaption(String imagePath) {
        SQLiteDatabase db = DbHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_IMAGE, COLUMN_CAPTION}, COLUMN_IMAGE + " = ?", new String[]{imagePath}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String caption = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CAPTION));
            cursor.close();
            return caption;
        }
        return null;
    }
}
