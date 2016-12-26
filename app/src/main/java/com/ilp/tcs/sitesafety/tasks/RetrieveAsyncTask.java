package com.ilp.tcs.sitesafety.tasks;

import android.database.Cursor;
import android.os.AsyncTask;

import com.ilp.tcs.sitesafety.database.BranchDb;
import com.ilp.tcs.sitesafety.database.LocationDb;
import com.ilp.tcs.sitesafety.listeners.OnDataReceived;
import com.ilp.tcs.sitesafety.modals.Datas;
import com.ilp.tcs.sitesafety.utils.Constants;
import com.ilp.tcs.sitesafety.vo.Branch;
import com.ilp.tcs.sitesafety.vo.Locations;

/**
 * Created by Fedric Antony on 7/5/2016.
 */
public class RetrieveAsyncTask extends AsyncTask<Void, Void, Void> {

    private int mTransaction;
    private OnDataReceived mListener;
    private String mBranchId;

    public RetrieveAsyncTask(int trn, OnDataReceived listener) {

        mTransaction = trn;
        mListener = listener;
    }

    public RetrieveAsyncTask(int trn, OnDataReceived listener, String id) {

        mTransaction = trn;
        mListener = listener;
        mBranchId = id;
    }

    @Override
    protected Void doInBackground(Void... params) {

        if (mTransaction == Constants.LIST_BRANCH) {
            retrieveBranches();
        } else if (mTransaction == Constants.LIST_LOCATION) {
            retrieveLocation();
        } else if ((mTransaction == Constants.FILTERED_LOCATION)) {
            retrieveFilteredLocation();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        if (mListener != null) {

            if (mTransaction == Constants.LIST_BRANCH) {
                mListener.onDataReceived(Constants.LIST_BRANCH, -1, "");
            } else if (mTransaction == Constants.LIST_LOCATION) {
                mListener.onDataReceived(Constants.LIST_LOCATION, -1, "");
            } else if (mTransaction == Constants.FILTERED_LOCATION) {
                mListener.onDataReceived(Constants.FILTERED_LOCATION, -1, "");
            }
        }
    }

    private void retrieveBranches() {

        Datas.listBranches.clear();
        Cursor cursor = BranchDb.getInstance().getAllColumns();

        if (cursor != null) {

            int length = cursor.getCount();

            if (length > 0) {
                cursor.moveToFirst();
                do {

                    Branch b = new Branch();
                    b.setId(cursor.getString(cursor.getColumnIndex(BranchDb._ID)));
                    b.setName(cursor.getString(cursor.getColumnIndex(BranchDb.NAME)));
                    b.setCreatedOn(cursor.getString(cursor.getColumnIndex(BranchDb.CREATED_ON)));
                    b.setUpdatedOn(cursor.getString(cursor.getColumnIndex(BranchDb.UPDATED_ON)));
                    b.setStatus(cursor.getString(cursor.getColumnIndex(BranchDb.STATUS)));

                    Datas.listBranches.add(b);
                }
                while (cursor.moveToNext());
            }
        }
    }

    private void retrieveLocation() {

        Datas.listLocations.clear();
        Cursor cursor = LocationDb.getInstance().getAllColumns();

        if (cursor != null) {

            int length = cursor.getCount();

            if (length > 0) {
                cursor.moveToFirst();

                do {

                    Locations l = new Locations();
                    l.setId(cursor.getString(cursor.getColumnIndex(LocationDb._ID)));
                    l.setName(cursor.getString(cursor.getColumnIndex(LocationDb.NAME)));
                    l.setBranchName(cursor.getString(cursor.getColumnIndex(BranchDb.NAME)));
                    l.setBranchId(cursor.getString(cursor.getColumnIndex(LocationDb.BRANCH_ID)));
                    l.setCreatedOn(cursor.getString(cursor.getColumnIndex(LocationDb.CREATED_ON)));
                    l.setUpdatedOn(cursor.getString(cursor.getColumnIndex(LocationDb.UPDATED_ON)));
                    l.setStatus(cursor.getString(cursor.getColumnIndex(LocationDb.STATUS)));

                    Datas.listLocations.add(l);
                }
                while (cursor.moveToNext());
            }
        }
    }

    private void retrieveFilteredLocation() {

        Datas.listFilteredLocations.clear();
        Cursor cursor = LocationDb.getInstance().filterByBranch(mBranchId);

        if (cursor != null) {

            int length = cursor.getCount();

            if (length > 0) {
                cursor.moveToFirst();

                do {

                    Locations l = new Locations();
                    l.setId(cursor.getString(cursor.getColumnIndex(LocationDb._ID)));
                    l.setName(cursor.getString(cursor.getColumnIndex(LocationDb.NAME)));
                    l.setBranchName(cursor.getString(cursor.getColumnIndex(BranchDb.NAME)));
                    l.setBranchId(cursor.getString(cursor.getColumnIndex(LocationDb.BRANCH_ID)));
                    l.setCreatedOn(cursor.getString(cursor.getColumnIndex(LocationDb.CREATED_ON)));
                    l.setUpdatedOn(cursor.getString(cursor.getColumnIndex(LocationDb.UPDATED_ON)));
                    l.setStatus(cursor.getString(cursor.getColumnIndex(LocationDb.STATUS)));

                    Datas.listFilteredLocations.add(l);
                }
                while (cursor.moveToNext());
            }
        }
    }
}
