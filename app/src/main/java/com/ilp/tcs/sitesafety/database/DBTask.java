package com.ilp.tcs.sitesafety.database;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Azim Ansari on 5/25/2016.
 * Other database operations
 */
public class DBTask extends AsyncTask<ContentValues, Void, Long> {

    private final Context mContext;
    /**
     * reference of interface functions to handle callbacks.
     */
    private final DBCallbacks mCallback;
    /**
     * reference of DBOperations to use functions from that.
     */
    private final DBOperations mDBOperation;
    /**
     * variable that stores primary key.
     */
    private final long primaryKey;


    public DBTask(Context context, DBCallbacks callback, DBOperations operation, long id) {
        this.mContext = context;
        this.mCallback = callback;
        this.mDBOperation = operation;
        this.primaryKey = id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Long doInBackground(ContentValues... params) {

      /*  if (mDBOperation == DBOperations.INSERT) {
            return TblMovie.insert(params[0]);
        } else if (mDBOperation == DBOperations.UPDATE) {

        } else if (mDBOperation == DBOperations.DELETE) {
            int i = TblMovie.delete(primaryKey);
            return (long) i;
        }*/

        return null;
    }

    @Override
    protected void onPostExecute(Long result) {
        super.onPostExecute(result);
        mCallback.onResult(result);
    }
}
