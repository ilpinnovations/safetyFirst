package com.ilp.tcs.sitesafety.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.ilp.tcs.sitesafety.modals.Point;
import com.ilp.tcs.sitesafety.utils.SQLiteDataTypes;

import java.util.ArrayList;


/**
 * Created by Azim Ansari on 5/26/2016.
 * Table of sub categories
 */
public class TblCategoryPoints implements BaseColumns {

    /**
     * Table Name
     */
    public static final String TABLE_NAME = "TblCategoryPoints";

    /**
     * Column category id
     */
    public static final String COLUMN_CATEGORY_ID = "category_d";
    /**
     * Column Point Id.
     */
    public static final String COLUMN_POINT_NUMBER = "point_number";
    /**
     * Column Constraint.
     */
    public static final String COLUMN_CONSTRAINT = "constraints";

    public static final String COLUMN_WEIGHT = "weight";
    /**
     * Column Element.
     */
    public static final String COLUMN_ELEMENT = "element";

    /**
     * String to represent comma seperation.
     */
    private static final String COMMA_SEP = " , ";

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    BaseColumns._ID + SQLiteDataTypes.TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP +
                    COLUMN_CATEGORY_ID + SQLiteDataTypes.TYPE_INTEGER + " NOT NULL " + COMMA_SEP +
                    COLUMN_POINT_NUMBER + SQLiteDataTypes.TYPE_TEXT + " NOT NULL " + COMMA_SEP +
                    COLUMN_CONSTRAINT + SQLiteDataTypes.TYPE_TEXT + " NOT NULL " + COMMA_SEP +
                    COLUMN_WEIGHT + SQLiteDataTypes.TYPE_INTEGER + " NOT NULL " + COMMA_SEP +
                    COLUMN_ELEMENT + SQLiteDataTypes.TYPE_TEXT + " NOT NULL " + COMMA_SEP +
                    "FOREIGN KEY" + " ( " + COLUMN_CATEGORY_ID + " ) " + " REFERENCES " +
                    TblCategories.TABLE_NAME + " ( " + TblCategories._ID + " ) " + " ON DELETE CASCADE " +
                    " )";

    /**
     * @param id Primary key on basis of which subcategory is fetched.
     * @return arraylist of Point
     */
    public static ArrayList<Point> getSubCategoryByCategoryId(long id) {
        DbHelper helper = DbHelper.getInstance();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_CATEGORY_ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null);

        ArrayList<Point> points = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                long _id = cursor.getLong(cursor.getColumnIndex(_ID));
                long categoryId = cursor.getLong(cursor.getColumnIndex(COLUMN_CATEGORY_ID));
                String pointNumber = cursor.getString(cursor.getColumnIndex(COLUMN_POINT_NUMBER));
                String constraint = cursor.getString(cursor.getColumnIndex(COLUMN_CONSTRAINT));
                int weight = cursor.getInt(cursor.getColumnIndex(COLUMN_WEIGHT));
                String element = cursor.getString(cursor.getColumnIndex(COLUMN_ELEMENT));

                Point point = new Point();
                point.setPointId(_id);
                point.setCategoryId(categoryId);
                point.setPointNumber(pointNumber);
                point.setWeight(weight);
                point.setConstraint(constraint);
                point.setElement(element);

                points.add(point);
            } while (cursor.moveToNext());

            cursor.close();
            db.close();
            return points;
        }

        return null;
    }
}
