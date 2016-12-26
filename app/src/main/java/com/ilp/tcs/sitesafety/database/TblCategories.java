package com.ilp.tcs.sitesafety.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.ilp.tcs.sitesafety.modals.Category;
import com.ilp.tcs.sitesafety.utils.SQLiteDataTypes;

import java.util.ArrayList;

/**
 * Created by Azim Ansari on 5/26/2016.
 * Contains all categories
 */
public class TblCategories implements BaseColumns {

    /**
     * Table Name
     */
    public static final String TABLE_NAME = "TblCategories";

    /**
     * Column category number of table.
     */
    public static final String COLUMN_CATEGORY_NUMBER = "category_number";
    /**
     * Column category number of table.
     */
    public static final String COLUMN_CATEGORY_NAME = "category_name";

    /**
     * String to represent comma seperation.
     */
    private static final String COMMA_SEP = " , ";

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    BaseColumns._ID + SQLiteDataTypes.TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP +
                    COLUMN_CATEGORY_NUMBER + SQLiteDataTypes.TYPE_TEXT + " NOT NULL " + COMMA_SEP +
                    COLUMN_CATEGORY_NAME + SQLiteDataTypes.TYPE_TEXT + " NOT NULL " +
                    " )";

    /**
     * @param id primary key to get a category
     * @return category having given primary key otherwise null
     */
    public static Category getCategoryById(long id) {
        DbHelper helper = DbHelper.getInstance();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, _ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null);
        db.close();
        if (cursor != null && cursor.moveToFirst()) {
            long _id = cursor.getLong(cursor.getColumnIndex(_ID));
            String number = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NUMBER));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME));

            Category category = new Category();
            category.setCategoryId(_id);
            category.setCategoryNumber(number);
            category.setCategoryName(name);

            cursor.close();
            return category;
        }

        return null;
    }

    /**
     * @return returns all the categories from the table
     */
    public static ArrayList<Category> getAllCategories() {

        ArrayList<Category> categories;

        DbHelper helper = DbHelper.getInstance();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            categories = new ArrayList<>();

            do {
                long _id = cursor.getLong(cursor.getColumnIndex(_ID));
                String number = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NUMBER));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME));

                Category category = new Category();
                category.setCategoryId(_id);
                category.setCategoryNumber(number);
                category.setCategoryName(name);

                categories.add(category);
            }
            while (cursor.moveToNext());

            cursor.close();
            return categories;
        }

        return null;
    }
}
