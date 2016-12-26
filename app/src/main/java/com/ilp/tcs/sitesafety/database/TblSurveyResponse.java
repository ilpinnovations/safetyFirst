package com.ilp.tcs.sitesafety.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.ilp.tcs.sitesafety.modals.SurveyResponse;
import com.ilp.tcs.sitesafety.utils.SQLiteDataTypes;

import java.util.ArrayList;

/**
 * Created by Azim Ansari on 5/26/2016.
 * Survey response for each survey
 */
public class TblSurveyResponse implements BaseColumns {

    public static final String TABLE_NAME = "TblSurveyResponse";

    public static final String COLUMN_SURVEY_ID = "survey_id";
    public static final String COLUMN_POINT_ID = "point_id";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_DESCRIPTION = "description";

    private static final String COMMA_SEP = " , ";

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    BaseColumns._ID + SQLiteDataTypes.TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP +
                    COLUMN_SURVEY_ID + SQLiteDataTypes.TYPE_INTEGER + " NOT NULL " + COMMA_SEP +
                    COLUMN_POINT_ID + SQLiteDataTypes.TYPE_INTEGER + " NOT NULL " + COMMA_SEP +
                    COLUMN_STATUS + SQLiteDataTypes.TYPE_INTEGER + " NOT NULL " + COMMA_SEP +
                    COLUMN_DESCRIPTION + SQLiteDataTypes.TYPE_TEXT + COMMA_SEP +
                    "FOREIGN KEY" + " ( " + COLUMN_SURVEY_ID + " ) " + " REFERENCES " +
                    TblSurvey.TABLE_NAME + " ( " + TblSurvey._ID + " ) " + " ON DELETE CASCADE " + COMMA_SEP +
                    "FOREIGN KEY" + " ( " + COLUMN_POINT_ID + " ) " + " REFERENCES " +
                    TblCategoryPoints.TABLE_NAME + " ( " + TblCategoryPoints._ID + " ) " + " ON DELETE CASCADE " +
                    " )";


    /**
     * Function that gives survey response on basis of surveyId.
     *
     * @param id Survey Id by which survey response will be fetched.
     * @return Object of SurveyResponse.
     */
    public static SurveyResponse getSurveyResponseById(long id) {
        DbHelper helper = DbHelper.getInstance();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, _ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null);
        db.close();
        if (cursor != null && cursor.moveToFirst()) {
            long _id = cursor.getLong(cursor.getColumnIndex(_ID));
            long surveyId = cursor.getInt(cursor.getColumnIndex(COLUMN_SURVEY_ID));
            long subcategoryId = cursor.getInt(cursor.getColumnIndex(COLUMN_POINT_ID));
            String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
            int status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS));

            SurveyResponse surveyResponse = new SurveyResponse();
            surveyResponse.setSurveyResponseId(_id);
            surveyResponse.setSurveyId(surveyId);
            surveyResponse.setSubcategoryId(subcategoryId);
            surveyResponse.setDescription(description);
            surveyResponse.setStatus(status);

            cursor.close();
            return surveyResponse;
        }

        return null;
    }


    /**
     * @return arraylist of surveyResponse containing all data.
     */
    public static ArrayList<SurveyResponse> getAllSurveyResponses() {

        ArrayList<SurveyResponse> surveyResponses;

        DbHelper helper = DbHelper.getInstance();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            surveyResponses = new ArrayList<>();

            do {
                long _id = cursor.getLong(cursor.getColumnIndex(_ID));
                long surveyId = cursor.getInt(cursor.getColumnIndex(COLUMN_SURVEY_ID));
                long subcategoryId = cursor.getInt(cursor.getColumnIndex(COLUMN_POINT_ID));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                int status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS));

                SurveyResponse surveyResponse = new SurveyResponse();
                surveyResponse.setSurveyResponseId(_id);
                surveyResponse.setSurveyId(surveyId);
                surveyResponse.setSubcategoryId(subcategoryId);
                surveyResponse.setDescription(description);
                surveyResponse.setStatus(status);

                surveyResponses.add(surveyResponse);
            }
            while (cursor.moveToNext());

            cursor.close();
            return surveyResponses;
        }

        return null;
    }

    /**
     * @param uniqueSurveyId on basis of this data will be fetched.
     * @return Arraylist of surveyresponse containing data on basis of surveyId.
     */
    public static ArrayList<SurveyResponse> getSurveyBySurveyId(long uniqueSurveyId) {

        ArrayList<SurveyResponse> surveyBySurveyId;

        DbHelper helper = DbHelper.getInstance();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_SURVEY_ID + " = ? ", new String[]{String.valueOf(uniqueSurveyId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            surveyBySurveyId = new ArrayList<>();

            do {
                long _id = cursor.getLong(cursor.getColumnIndex(_ID));
                long surveyId = cursor.getInt(cursor.getColumnIndex(COLUMN_SURVEY_ID));
                long subcategoryId = cursor.getInt(cursor.getColumnIndex(COLUMN_POINT_ID));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                int status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS));

                SurveyResponse survey = new SurveyResponse();
                survey.setSurveyResponseId(_id);
                survey.setSurveyId(surveyId);
                survey.setSubcategoryId(subcategoryId);
                survey.setDescription(description);
                survey.setStatus(status);

                surveyBySurveyId.add(survey);
            }
            while (cursor.moveToNext());

            cursor.close();
            return surveyBySurveyId;
        }

        return null;
    }

    /**
     * @param uniqueSurveyid surveyId to fetch data.
     * @param uniquePointId Point Id to fatch data.
     * @return Object of survey response on basis of surveyId & pointId.
     */
    public static SurveyResponse getSurveyResponseBySurveyIdAndPointId(long uniqueSurveyid, long uniquePointId) {
        DbHelper helper = DbHelper.getInstance();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_SURVEY_ID + " = ? AND " + COLUMN_POINT_ID + " = ? ", new String[]{String.valueOf(uniqueSurveyid), String.valueOf(uniquePointId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            long _id = cursor.getLong(cursor.getColumnIndex(_ID));
            long surveyId = cursor.getInt(cursor.getColumnIndex(COLUMN_SURVEY_ID));
            long subcategoryId = cursor.getInt(cursor.getColumnIndex(COLUMN_POINT_ID));
            String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
            int status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS));

            SurveyResponse surveyResponse = new SurveyResponse();
            surveyResponse.setSurveyResponseId(_id);
            surveyResponse.setSurveyId(surveyId);
            surveyResponse.setSubcategoryId(subcategoryId);
            surveyResponse.setDescription(description);
            surveyResponse.setStatus(status);

            cursor.close();
            return surveyResponse;
        }

        return null;
    }

}
