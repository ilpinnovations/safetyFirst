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
 * AsyncTask to perform updation of data in background.
 * Created by 1244696 on 6/10/2016.
 */
/**
 *  Modified by Abhishek Gupta on 24/8/2016.
 */

public class UpdateTask extends AsyncTask<Void, Void, Long> {
    /**
     * interface reference to handle callbacks.
     */
    private UpdateTaskCallback mCallback;
    private ArrayList<GroupItem> groupItemsList;
    /**
     * flag to check for errors after updation.
     */
    private boolean isError = false;
    /**
     * progress dialog to represent progress of updation.
     */
    private ProgressDialog mDialog;
    private Context mContext;
    private String title;

    public UpdateTask(Context context, UpdateTaskCallback listener, ArrayList<GroupItem> groupListItems, String name) {
        this.mContext = context;
        mCallback = listener;
        groupItemsList = groupListItems;
        title = name;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Updating report...");
        mDialog.setCancelable(false);
        mDialog.show();
    }

    @Override
    protected Long doInBackground(Void... params) {

        SQLiteDatabase db = DbHelper.getInstance().getWritableDatabase();
        long surveyId = SitePreference.getInstance().getSurveyId();
        if (surveyId != -1) {
            isError = updateResponse(db, surveyId);
        }
        if (isError)
            surveyId = -1;

        return surveyId;
    }


    /**
     * Function to update the survey.
     *
     * @param db       database name in which the operation will take place.
     * @param surveyId unique surveyId by which updation will take place.
     * @return boolean response to check for error.
     */
    private boolean updateResponse(SQLiteDatabase db, long surveyId) {
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
                responseValues.put(TblSurveyResponse.COLUMN_STATUS, response);
                responseValues.put(TblSurveyResponse.COLUMN_DESCRIPTION, description);
                int result = db.update(TblSurveyResponse.TABLE_NAME, responseValues, TblSurveyResponse.COLUMN_SURVEY_ID + " = ? AND " + TblSurveyResponse.COLUMN_POINT_ID + " = ?", new String[]{String.valueOf(surveyId), String.valueOf(childItem.getPointId())});
                // int result = DbHelper.update(db, TblSurveyResponse.TABLE_NAME, responseValues, surveyId);
                if (result == 0) {
                    isError = true;
                    break;
                }

                // Deleting old images
                db.delete(TblImages.TABLE_NAME, TblImages.COLUMN_SURVEY_ID + " = ? AND " + TblImages.COLUMN_POINT_ID + " = ?", new String[]{String.valueOf(surveyId), String.valueOf(childItem.getPointId())});


                /*for (Image img : childItem.getImages()) {
                    ContentValues imageValues = new ContentValues();
                    imageValues.put(TblImages.COLUMN_IMAGE, img.getImagePath());
                    imageValues.put(TblImages.COLUMN_CAPTION, img.getCaption());
                    Log.v("aaaaUPtpointid", String.valueOf(childItem.getPointId()));
                    int imgResult = db.update(TblImages.TABLE_NAME, imageValues, TblImages.COLUMN_SURVEY_ID + " = ? AND " + TblImages.COLUMN_POINT_ID + " = ?", new String[]{String.valueOf(surveyId), String.valueOf(childItem.getPointId())});
                    // int imgResult = DbHelper.update(db, TblImages.TABLE_NAME, imageValues, surveyId);
                    if (imgResult == 0) {
                        isError = true;
                        break;
                    }
                }*/

              /*  for (Image img1 : childItem.getImages()) {
                    long surveyid = img1.getSurveyId();
                    int results = db.delete(TblImages.TABLE_NAME, TblImages.COLUMN_SURVEY_ID+" = ?", new String[]{String.valueOf(surveyid)});
                    if (results == -1){
                        isError = true;
                        Log.v("aaaaUptsk117delete","error");
                        break;
                    }
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
                        Log.v("aaaaUptsk132insert","error");
                        isError = true;
                        break;
                    }
                }*/
                // Inserting new images
                for (Image img : childItem.getImages()) {
                    ContentValues imageValues = new ContentValues();
                    imageValues.put(TblImages.COLUMN_SURVEY_ID, surveyId);
                    imageValues.put(TblImages.COLUMN_POINT_ID, childItem.getPointId());
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
        ContentValues values = new ContentValues();
        values.put(TblSurvey.COLUMN_TITLE, title);
        values.put(TblSurvey.COLUMN_UPDATED_BY, SitePreference.getInstance().getEmployeeName());
        values.put(TblSurvey.COLUMN_UPDATED_DATE_TIME, System.currentTimeMillis());
        values.put(TblSurvey.COLUMN_SCORE_BY_WEIGHTED_CATEGORY, 1);
        values.put(TblSurvey.COLUMN_SCORE_BY_WEIGHTED_QUESTION, 1);
        values.put(TblSurvey.IS_COMPLETED, "0");
        values.putNull(TblSurvey.COLUMN_PDF_PATH);
        values.putNull(TblSurvey.COLUMN_OFI_PDF_PATH);
        values.putNull(TblSurvey.COLUMN_EXCEL_PATH);
        values.putNull(TblSurvey.COLUMN_WORD_PATH);

        DbHelper.update(db, TblSurvey.TABLE_NAME, values, surveyId);
        return isError;
    }

    @Override
    protected void onPostExecute(Long surveyId) {
        super.onPostExecute(surveyId);
        mDialog.dismiss();
        mCallback.onUpdateComplete(surveyId);
    }

}
