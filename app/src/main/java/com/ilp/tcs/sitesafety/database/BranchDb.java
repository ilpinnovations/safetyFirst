package com.ilp.tcs.sitesafety.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ilp.tcs.sitesafety.utils.SQLiteDataTypes;

import java.util.Date;

/**
 * Created by Fedric Antony on 02-07-2016.
 */
public class BranchDb implements Tables {

    public static final String TABLE_NAME = "branch";

    public static final String NAME = "branch_name";

    public static final String CREATE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    _ID + SQLiteDataTypes.TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT, " +
                    NAME + SQLiteDataTypes.TYPE_TEXT + " NOT NULL, " +
                    CREATED_ON + SQLiteDataTypes.TYPE_BLOB + " NOT NULL, " +
                    UPDATED_ON + SQLiteDataTypes.TYPE_BLOB + " not null, " +
                    STATUS + SQLiteDataTypes.TYPE_INTEGER + " not null" +
                    " )";

    private static BranchDb sInstance;
    private String[] mColumns = {_ID, NAME, CREATED_ON, UPDATED_ON, STATUS};

    private BranchDb() {
    }

    public static BranchDb getInstance() {

        if (sInstance == null) {
            sInstance = new BranchDb();
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

        return DbHelper.getInstance().getDb().query(TABLE_NAME, mColumns, STATUS + "=1", null, null, null, null, null);
    }
}
