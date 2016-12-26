package com.ilp.tcs.sitesafety.database;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.ilp.tcs.sitesafety.modals.ChildItem;
import com.ilp.tcs.sitesafety.modals.GroupItem;
import com.ilp.tcs.sitesafety.modals.Image;
import com.ilp.tcs.sitesafety.preference.SitePreference;

import java.util.ArrayList;

/**
 * Created by 1244696 on 6/3/2016.
 * AsyncTask to perform saving of survey in background thread.
 */
public class SaveTask extends AsyncTask<Void, Void, Long> {
    /**
     * Interface reference to handle callbacks.
     */
    private SaveTaskCallback mCallback;
    /**
     * arraylist of groupitems.
     */
    private ArrayList<GroupItem> groupItemsList;
    /**
     * false to check for error.
     */
    private boolean isError = false;
    private Context mContext;
    /**
     * DialogBox to show progress of SaveTask.
     */
    private ProgressDialog mDialog;

    private String mSurveyName;

    public SaveTask(Context context, SaveTaskCallback listener, ArrayList<GroupItem> groupListItems, String name) {
        this.mContext = context;
        mCallback = listener;
        groupItemsList = groupListItems;
        mSurveyName = name;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Saving report...");
        mDialog.setCancelable(false);
        mDialog.show();
    }

    @Override
    protected Long doInBackground(Void... params) {
        SQLiteDatabase db = DbHelper.getInstance().getWritableDatabase();
        long surveyId = insertSurvey(db);
        if (surveyId != -1) {
            isError = insertResponse(db, surveyId);
        }
        if (isError)
            surveyId = -1;

        return surveyId;
    }

    /**
     * Function to insert new survey in SurveyTable.
     *
     * @param db name of database in which insertion will take place.
     * @return Survey id of the inserted survey.
     */
    private long insertSurvey(SQLiteDatabase db) {

        String empId = SitePreference.getInstance().getEmployeeId();
        String empName = SitePreference.getInstance().getEmployeeName();

        ContentValues surveyValues = new ContentValues();
        surveyValues.put(TblSurvey.COLUMN_TITLE, mSurveyName);
        surveyValues.put(TblSurvey.COLUMN_CREATED_DATE_TIME, System.currentTimeMillis());
        surveyValues.put(TblSurvey.COLUMN_USER_ID, empId);
        surveyValues.put(TblSurvey.COLUMN_USER_NAME, empName);
        surveyValues.put(TblSurvey.COLUMN_UPDATED_BY, empName);
        surveyValues.put(TblSurvey.COLUMN_UPDATED_DATE_TIME, System.currentTimeMillis());
        surveyValues.put(TblSurvey.COLUMN_SCORE_BY_WEIGHTED_CATEGORY, 1);
        surveyValues.put(TblSurvey.COLUMN_SCORE_BY_WEIGHTED_QUESTION, 1);
        surveyValues.put(TblSurvey.IS_COMPLETED, "0");
        surveyValues.put(TblSurvey.SURVEY_BRANCH, SitePreference.getInstance().getBranchName());
        surveyValues.put(TblSurvey.SURVEY_LOCATION, SitePreference.getInstance().getLocationName());

        long result = DbHelper.insert(db, TblSurvey.TABLE_NAME, surveyValues);
        if (result == -1) {
            isError = true;
        }
        return result;
    }

    /**
     * Function to insert new survey response into sureyResponse Table.
     *
     * @param db       name of database in which insertion will take place.
     * @param surveyId insertion will take place with reference to survey id.
     * @return boolean flag that survey response is inserted or not.
     */
    private boolean insertResponse(SQLiteDatabase db, long surveyId) {
        if (this.isError)
            return true;

        boolean isError = false;
        for (GroupItem groupItem : groupItemsList) {
            ArrayList<ChildItem> children = groupItem.getChildItems();

            ContentValues responseValues = new ContentValues();
            for (ChildItem childItem : children) {
                responseValues.clear();
                long childPointId = childItem.getPointId();
                int response = childItem.getResponse();
                String description = childItem.getDescription();
                responseValues.put(TblSurveyResponse.COLUMN_SURVEY_ID, surveyId);
                responseValues.put(TblSurveyResponse.COLUMN_POINT_ID, childPointId);
                responseValues.put(TblSurveyResponse.COLUMN_STATUS, response);
                responseValues.put(TblSurveyResponse.COLUMN_DESCRIPTION, description);
                long result = DbHelper.insert(db, TblSurveyResponse.TABLE_NAME, responseValues);
                if (result == -1) {
                    isError = true;
                    break;
                }

                for (Image img : childItem.getImages()) {
                    ContentValues imageValues = new ContentValues();
                    imageValues.put(TblImages.COLUMN_SURVEY_ID, surveyId);
                    imageValues.put(TblImages.COLUMN_POINT_ID, childPointId);
                    imageValues.put(TblImages.COLUMN_IMAGE, img.getImagePath());
                    imageValues.put(TblImages.COLUMN_LOCATION, img.getLocation());
                    imageValues.put(TblImages.COLUMN_CAPTION, img.getCaption());
                    long imgResult = DbHelper.insert(db, TblImages.TABLE_NAME, imageValues);
                    if (imgResult == -1) {
                        isError = true;
                        break;
                    }
                }
            }
        }
        return isError;
    }

    @Override
    protected void onPostExecute(Long surveyId) {
        super.onPostExecute(surveyId);
        mDialog.dismiss();
        mCallback.onSaveComplete(surveyId);
    }
}

