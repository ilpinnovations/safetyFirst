package com.ilp.tcs.sitesafety.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.BaseColumns;
import android.util.Log;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.activity.SiteSafetyApplication;
import com.ilp.tcs.sitesafety.modals.Category;
import com.ilp.tcs.sitesafety.modals.ChildItem;
import com.ilp.tcs.sitesafety.modals.GroupItem;
import com.ilp.tcs.sitesafety.modals.Image;
import com.ilp.tcs.sitesafety.modals.Point;
import com.ilp.tcs.sitesafety.modals.SurveyResponse;
import com.ilp.tcs.sitesafety.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Azim Ansari on 5/25/2016.
 * DbHelper singleton class for managing database
 */
public class DbHelper extends SQLiteOpenHelper {

    /**
     * Final and Static Database Name.
     */

    public static final String DATABASE_NAME = "sitesafety.db";
    /**
     * Final and Static Database Version.
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * TAG used for log printing
     */
    private static final String TAG = DbHelper.class.getSimpleName();

    /**
     * Static reference of DbHelper.
     */
    private static DbHelper mDbHelper;

    /**
     * @param context Context of the calling component
     *                Creating database if not exists
     */
    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * @return returns static singleton instance of the {@link DbHelper}
     */
    public static DbHelper getInstance() {
        if (mDbHelper == null) {
            mDbHelper = new DbHelper(SiteSafetyApplication.getContext());
        }

        return mDbHelper;
    }

    /**
     * Helper function that insert the values into table.
     *
     * @param db        the name of database in which data has to be inserted.
     * @param tableName the name of table in which values has to be inserted.
     * @param values    values which are to be inserted into table.
     * @return returns primary key of the inserted row.
     */
    public static long insert(SQLiteDatabase db, String tableName, ContentValues values) {
        return db.insert(tableName, null, values);
    }

    /**
     * Helper function that updates the values from table.
     *
     * @param db        the name of database in which data has to be updated.
     * @param tableName the name of table in which values has to be updated.
     * @param values    values which are to be updated into table.
     * @param id        primary key with reference of which updation will take place.
     * @return returns number of rows updated.
     */
    public static int update(SQLiteDatabase db, String tableName, ContentValues values, long id) {
        return db.update(tableName, values, BaseColumns._ID + " = ?", new String[]{String.valueOf(id)});
    }

    /**
     * Helper function that deletes the values from table
     *
     * @param db        the name of database from which data has to be deleted.
     * @param tableName the name of table from which data has to be deleted.
     * @param id        primary key with reference of which deletion wil take place.
     * @return returns number of rows deleted.
     */
    public static int delete(SQLiteDatabase db, String tableName, long id) {
        return db.delete(tableName, BaseColumns._ID + " = ?", new String[]{String.valueOf(id)});
    }

    /**
     * Helper function that parses a given table into a string
     * and returns it for easy printing. The string consists of
     * the table name and then each row is iterated through with
     * column_name: value pairs printed out.
     *
     * @param tableName the name of the table to print
     */
    public static void logPrintTable(String tableName) {
        Log.d(TAG, "**************************************************************************");
        Log.d(TAG, String.format("Table Name :%s\n", tableName));
        SQLiteDatabase db = getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        if (cursor != null && cursor.moveToFirst()) {
            String[] columnNames = cursor.getColumnNames();
            do {
                StringBuilder row = new StringBuilder();
                for (String name : columnNames) {
                    row.append(String.format(" %s: %s,\t", name, cursor.getString(cursor.getColumnIndex(name))));
                }
                Log.d(TAG, row.toString());
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        Log.d(TAG, "**************************************************************************");
    }

    /**
     * Helper function that fetches groupitems from database.
     *
     * @param surveyId on the basis surveyId the fetching of data will take place.
     * @return ArrayList of {@link GroupItem} filled from database with respect to given surveyId.
     */
    public static ArrayList<GroupItem> getSurveyDetails(long surveyId) {
        ArrayList<GroupItem> groupItemArrayList = new ArrayList<>();
        ArrayList<Category> categories = TblCategories.getAllCategories();

        if (categories != null) {
            for (Category c : categories) {
                GroupItem groupItem = new GroupItem(c.getCategoryId(), c.getCategoryNumber() + " - " + c.getCategoryName());

                ArrayList<Point> points = TblCategoryPoints.getSubCategoryByCategoryId(c.getCategoryId());

                if (points != null) {
                    for (Point p : points) {
                        ChildItem childItem = new ChildItem();
                        childItem.setPointId(p.getPointId());
                        childItem.setElement(p.getElement());
                        childItem.setWeight(p.getWeight());
                        childItem.setTitle(p.getPointNumber() + " - " + p.getConstraint());
                        SurveyResponse surveyResponse = TblSurveyResponse.getSurveyResponseBySurveyIdAndPointId(surveyId, childItem.getPointId());
                        ArrayList<Image> imageArrayList = TblImages.getImagesBySurveyIdAndPointId(surveyId, childItem.getPointId());
                        if (surveyResponse != null) {
                            childItem.setResponse(surveyResponse.getStatus());
                            childItem.setIsResponseGiven(surveyResponse.getStatus() != Constants.UNDEFINED);
                            childItem.setDescription(surveyResponse.getDescription());
                            if (imageArrayList != null && !imageArrayList.isEmpty())
                                childItem.setImages(imageArrayList);
                        }

                        groupItem.setChildItem(childItem);
                    }
                }
                groupItemArrayList.add(groupItem);
            }
        }
        return groupItemArrayList;
    }

    /**
     * Helper function which gives the row count of a particular table.
     *
     * @param db        name of the database on which the operation wil be performed.
     * @param tableName Name of table from which we have to count the rows.
     * @return count of rows from table.
     */
    public static long getRowCount(SQLiteDatabase db, String tableName) {
        return DatabaseUtils.queryNumEntries(db, tableName) + 1;
    }

    public SQLiteDatabase getDb() {
        return mDbHelper.getWritableDatabase();
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TblCategories.CREATE_TABLE_QUERY);
        db.execSQL(BranchDb.CREATE);
        db.execSQL(LocationDb.CREATE);
        db.execSQL(TblCategoryPoints.CREATE_TABLE_QUERY);

        Resources resource = SiteSafetyApplication.getContext().getResources();

        insertCategories(db, "1", "General", resource.getStringArray(R.array.general_1), resource.getStringArray(R.array.general_1_elements));
        insertCategories(db, "2", "Vehicle Movement within Premises", resource.getStringArray(R.array.vehicle_movement_2), resource.getStringArray(R.array.vehicle_movement_2_elements));
        insertCategories(db, "3", "Transformer Area (HT/LT)", resource.getStringArray(R.array.transform_area_3), resource.getStringArray(R.array.transform_area_3_elements));
        insertCategories(db, "4", "Electrical Panel Room (HT/LT)", resource.getStringArray(R.array.electric_panel_4), resource.getStringArray(R.array.electric_panel_4_elements));
        insertCategories(db, "5", "DG and Diesel Storage", resource.getStringArray(R.array.dg_diesel_storage_5), resource.getStringArray(R.array.dg_diesel_storage_5_elements));
        insertCategories(db, "6", "Water Treatment and STP", resource.getStringArray(R.array.water_treatment_stp_6), resource.getStringArray(R.array.water_treatment_stp_6_elements));
        insertCategories(db, "7", "Utility Block and Plant Room", resource.getStringArray(R.array.utility_block_pant_room_7), resource.getStringArray(R.array.utility_block_pant_room_7_elements));
        insertCategories(db, "8", "LPG bank", resource.getStringArray(R.array.lpg_bank_8), resource.getStringArray(R.array.lpg_bank_8_elements));
        insertCategories(db, "9", "Canteen", resource.getStringArray(R.array.canteen_9), resource.getStringArray(R.array.canteen_9_elements));
        insertCategories(db, "10", "General", resource.getStringArray(R.array.general_10), resource.getStringArray(R.array.general_10_elements));
        insertCategories(db, "11", "Fire Prevention Measures", resource.getStringArray(R.array.fire_prevention_11), resource.getStringArray(R.array.fire_prevention_11_elements));
        insertCategories(db, "12", "Fire Detection and Alarm System", resource.getStringArray(R.array.fire_detection_and_alarm_12), resource.getStringArray(R.array.fire_detection_and_alarm_12_elements));
        insertCategories(db, "13", "Emergency Escape Routes", resource.getStringArray(R.array.emergency_escape_routes_13), resource.getStringArray(R.array.emergency_escape_routes_13_elements));
        insertCategories(db, "14", "Fire Fighting Equipment", resource.getStringArray(R.array.fire_fighting_equipment_14), resource.getStringArray(R.array.fire_fighting_equipment_14_elements));
        insertCategories(db, "15", "Utility Areas and AHU Rooms", resource.getStringArray(R.array.utility_areas_and_ahu_15), resource.getStringArray(R.array.utility_areas_and_ahu_15_elements));
        insertCategories(db, "16", "Storage Areas", resource.getStringArray(R.array.storage_areas_16), resource.getStringArray(R.array.storage_areas_16_elements));
        insertCategories(db, "17", "Data Centers, Server and Hub Rooms", resource.getStringArray(R.array.data_center_server_17), resource.getStringArray(R.array.data_center_server_17_elements));
        insertCategories(db, "18", "UPS Room", resource.getStringArray(R.array.ups_room_18), resource.getStringArray(R.array.ups_room_18_elements));
        insertCategories(db, "19", "Washrooms and Toilets", resource.getStringArray(R.array.washrooms_and_toilets_19), resource.getStringArray(R.array.washrooms_and_toilets_19_elements));
        insertCategories(db, "20", "The Workplace (ODCs)", resource.getStringArray(R.array.workplace_20), resource.getStringArray(R.array.workplace_20_elements));
        insertCategories(db, "21", "General", resource.getStringArray(R.array.general_21), resource.getStringArray(R.array.general_21_elements));
        insertCategories(db, "22", "Working at height", resource.getStringArray(R.array.working_at_height_22), resource.getStringArray(R.array.working_at_height_22_elements));
        insertCategories(db, "23", "Working at high noise level", resource.getStringArray(R.array.working_at_high_noise_23), resource.getStringArray(R.array.working_at_high_noise_23_elements));
        insertCategories(db, "24", "Permit to work system", resource.getStringArray(R.array.permit_to_work_system_24), resource.getStringArray(R.array.permit_to_work_system_24_elements));
        insertCategories(db, "25", "Personal protective equipments", resource.getStringArray(R.array.personal_protective_equipments_25), resource.getStringArray(R.array.personal_protective_equipments_25_elements));
        insertCategories(db, "26", "Emergency Preparedness  & Response Plan", resource.getStringArray(R.array.emergency_preparedness_response_plan_26), resource.getStringArray(R.array.emergency_preparedness_response_plan_26_elements));

        db.execSQL(TblSurvey.CREATE_TABLE_QUERY);
        db.execSQL(TblSurveyResponse.CREATE_TABLE_QUERY);
        db.execSQL(TblImages.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void insertCategories(SQLiteDatabase db, String categoryNo, String categoryName, String[] points, String[] elements) {
        ContentValues categoryValues = new ContentValues();
        categoryValues.put(TblCategories.COLUMN_CATEGORY_NUMBER, categoryNo + ".0");
        categoryValues.put(TblCategories.COLUMN_CATEGORY_NAME, categoryName);

        long catId = insert(db, TblCategories.TABLE_NAME, categoryValues);

        ContentValues values = new ContentValues();
        for (int i = 0; i < points.length; i++) {
            values.put(TblCategoryPoints.COLUMN_CATEGORY_ID, catId);
            values.put(TblCategoryPoints.COLUMN_POINT_NUMBER, categoryNo + "." + (i + 1));
            values.put(TblCategoryPoints.COLUMN_WEIGHT, Integer.parseInt(points[i].substring(0, 1)));
            values.put(TblCategoryPoints.COLUMN_CONSTRAINT, points[i].substring(1));
            values.put(TblCategoryPoints.COLUMN_ELEMENT, elements[i]);
            insert(db, TblCategoryPoints.TABLE_NAME, values);
            values.clear();
        }
    }
}
