package com.ilp.tcs.sitesafety.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ilp.tcs.sitesafety.utils.SQLiteDataTypes;

import java.util.Date;

/**
 * Created by Fedric Antony on 02-07-2016.
 */
public class LocationDb implements Tables {

    public static final String NAME = "location_name";
    public static final String BRANCH_ID = "branch_id";

    public static final String TABLE_NAME = "location";

    public static final String CREATE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    _ID + SQLiteDataTypes.TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT, " +
                    NAME + SQLiteDataTypes.TYPE_TEXT + " NOT NULL, " +
                    BRANCH_ID + SQLiteDataTypes.TYPE_INTEGER + " not null, " +
                    CREATED_ON + SQLiteDataTypes.TYPE_BLOB + " NOT NULL, " +
                    UPDATED_ON + SQLiteDataTypes.TYPE_BLOB + " not null, " +
                    STATUS + SQLiteDataTypes.TYPE_INTEGER + " not null" +
                    " )";
    private static final String QUERY = "select location._id,location.location_name,location.branch_id,location.created_on,location.updated_on,location.status,branch.branch_name " +
            "from location left join branch on branch._id=location.branch_id where location.status=1;";


    private static LocationDb sInstance;
    private String[] mColumns = {_ID, NAME, BRANCH_ID, CREATED_ON, UPDATED_ON, STATUS};

    private LocationDb() {

    }

    public static LocationDb getInstance() {

        if (sInstance == null) {
            sInstance = new LocationDb();
        }
        return sInstance;
    }

    public long insert(ContentValues cv) {

        cv.put(CREATED_ON, new Date().getTime());
        cv.put(UPDATED_ON, "");
        cv.put(STATUS, "1");

        return DbHelper.getInstance().getDb().insert(TABLE_NAME, null, cv);
    }

    public long update(long id, ContentValues cv) {
        cv.put(UPDATED_ON, new Date().getTime());
        return DbHelper.getInstance().getDb().update(TABLE_NAME, cv, _ID + "=" + id, null);
    }

    public long deletes(long id) {
        ContentValues cv = new ContentValues();
        cv.put(STATUS, "0");
        return DbHelper.getInstance().getDb().update(TABLE_NAME, cv, _ID + "=" + id, null);
    }

    public Cursor getAllColumns() {

        try {
            return DbHelper.getInstance().getDb().rawQuery(QUERY, null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor filterByBranch(String id) {

        String query = "select location._id,location.location_name,location.branch_id,location.created_on,location.updated_on,location.status,branch.branch_name " +
                "from location left join branch on branch._id=location.branch_id where location.status=1 and location.branch_id=" + id + ";";

        try {
            return DbHelper.getInstance().getDb().rawQuery(query, null);
        } catch (Exception e) {
            return null;
        }
    }
}
