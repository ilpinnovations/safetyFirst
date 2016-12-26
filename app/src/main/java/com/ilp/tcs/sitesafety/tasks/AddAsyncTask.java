package com.ilp.tcs.sitesafety.tasks;

import android.content.ContentValues;
import android.os.AsyncTask;

import com.ilp.tcs.sitesafety.database.BranchDb;
import com.ilp.tcs.sitesafety.database.LocationDb;
import com.ilp.tcs.sitesafety.listeners.OnDataReceived;
import com.ilp.tcs.sitesafety.utils.Constants;

/**
 * Created by Santhosh on 04-07-2016.
 */
public class AddAsyncTask extends AsyncTask<Void, Void, Long> {

    private ContentValues mContentValues;
    private OnDataReceived mListener;
    private int mType;
    private long mRowId;

    public AddAsyncTask(int type, ContentValues cv, OnDataReceived c) {
        mContentValues = cv;
        mListener = c;
        mType = type;
    }

    @Override
    protected Long doInBackground(Void... params) {

        if (mType == Constants.ADD_BRANCH) {
            mRowId = BranchDb.getInstance().insert(mContentValues);
        } else if (mType == Constants.ADD_LOCATION) {
            mRowId = LocationDb.getInstance().insert(mContentValues);
        }
        return mRowId;
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);

        mListener.onDataReceived(mType, aLong, "");

    }
}
