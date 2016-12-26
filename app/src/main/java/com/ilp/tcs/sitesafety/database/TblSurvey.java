package com.ilp.tcs.sitesafety.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.ilp.tcs.sitesafety.modals.Survey;
import com.ilp.tcs.sitesafety.utils.SQLiteDataTypes;

import java.util.ArrayList;

/**
 * Created by Azim Ansari on 5/26/2016.
 * Survey table to store every survey by user.
 */
public class TblSurvey implements BaseColumns {

    public static final String TABLE_NAME = "tbl_TblSurvey";

    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_CREATED_DATE_TIME = "created_date_time";
    public static final String COLUMN_UPDATED_BY = "updated_by";
    public static final String COLUMN_UPDATED_DATE_TIME = "updated_date_time";
    public static final String COLUMN_SCORE_BY_WEIGHTED_CATEGORY = "score_by_category";
    public static final String COLUMN_SCORE_BY_WEIGHTED_QUESTION = "score_by_question";
    public static final String COLUMN_PDF_PATH = "pdf_path";
    public static final String COLUMN_OFI_PDF_PATH = "ofi_pdf_path";
    public static final String COLUMN_EXCEL_PATH = "excel_path";
    public static final String COLUMN_WORD_PATH = "word_path";
    public static final String IS_COMPLETED = "is_completed";
    public static final String SURVEY_BRANCH = "survey_branch";
    public static final String SURVEY_LOCATION = "survey_loc";

    private static final String COMMA_SEP = " , ";

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    BaseColumns._ID + SQLiteDataTypes.TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP +
                    COLUMN_TITLE + SQLiteDataTypes.TYPE_TEXT + COMMA_SEP +
                    COLUMN_USER_ID + SQLiteDataTypes.TYPE_INTEGER + " NOT NULL " + COMMA_SEP +
                    COLUMN_USER_NAME + SQLiteDataTypes.TYPE_TEXT + " NOT NULL " + COMMA_SEP +
                    COLUMN_CREATED_DATE_TIME + SQLiteDataTypes.TYPE_INTEGER + " NOT NULL " + COMMA_SEP +
                    COLUMN_UPDATED_BY + SQLiteDataTypes.TYPE_TEXT + COMMA_SEP +
                    COLUMN_SCORE_BY_WEIGHTED_CATEGORY + SQLiteDataTypes.TYPE_INTEGER + COMMA_SEP +
                    COLUMN_SCORE_BY_WEIGHTED_QUESTION + SQLiteDataTypes.TYPE_INTEGER + COMMA_SEP +
                    COLUMN_UPDATED_DATE_TIME + SQLiteDataTypes.TYPE_INTEGER + COMMA_SEP +
                    COLUMN_PDF_PATH + SQLiteDataTypes.TYPE_TEXT + COMMA_SEP +
                    COLUMN_OFI_PDF_PATH + SQLiteDataTypes.TYPE_TEXT + COMMA_SEP +
                    COLUMN_EXCEL_PATH + SQLiteDataTypes.TYPE_TEXT + COMMA_SEP +
                    COLUMN_WORD_PATH + SQLiteDataTypes.TYPE_TEXT + COMMA_SEP +
                    IS_COMPLETED + SQLiteDataTypes.TYPE_INTEGER + " NOT NULL " + COMMA_SEP +
                    SURVEY_BRANCH + SQLiteDataTypes.TYPE_TEXT + " NOT NULL " + COMMA_SEP +
                    SURVEY_LOCATION + SQLiteDataTypes.TYPE_TEXT + " NOT NULL " +
                    " )";


    /**
     * Function that Values from survey table on basis of survey Id.
     *
     * @param id works as reference by which data will be fetched.
     * @return Object of TblSurvey.
     */
    public static Survey getSurveyById(long id) {
        DbHelper helper = DbHelper.getInstance();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, _ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            long surveyId = cursor.getLong(cursor.getColumnIndex(_ID));
            long userId = cursor.getLong(cursor.getColumnIndex(COLUMN_USER_ID));
            String userName = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME));
            long createdDateTime = cursor.getLong(cursor.getColumnIndex(COLUMN_CREATED_DATE_TIME));
            String surveyTitle = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String updatedBy = cursor.getString(cursor.getColumnIndex(COLUMN_UPDATED_BY));
            long updatedDateTime = cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATED_DATE_TIME));
            long scoreByWeightedCategory = cursor.getLong(cursor.getColumnIndex(COLUMN_SCORE_BY_WEIGHTED_CATEGORY));
            long scoreByWeightedQuestion = cursor.getLong(cursor.getColumnIndex(COLUMN_SCORE_BY_WEIGHTED_QUESTION));
            String Pdf_path = cursor.getString(cursor.getColumnIndex(COLUMN_PDF_PATH));
            String ofi_pdf_path = cursor.getString(cursor.getColumnIndex(COLUMN_OFI_PDF_PATH));
            String Excel_path = cursor.getString(cursor.getColumnIndex(COLUMN_EXCEL_PATH));
            String Word_path = cursor.getString(cursor.getColumnIndex(COLUMN_WORD_PATH));
            String completeStatus = cursor.getString(cursor.getColumnIndex(IS_COMPLETED));
            String branch = cursor.getString(cursor.getColumnIndex(SURVEY_BRANCH));
            String location = cursor.getString(cursor.getColumnIndex(SURVEY_LOCATION));

            Survey survey = new Survey();
            survey.setSurveyId(surveyId);
            survey.setUserId(userId);
            survey.setUserName(userName);
            survey.setCreatedDateTime(createdDateTime);
            survey.setSurveyTitle(surveyTitle);
            survey.setUpdatedBy(updatedBy);
            survey.setUpdatedDateTime(updatedDateTime);
            survey.setScoreByWeightedCategory(scoreByWeightedCategory);
            survey.setScoreByWeightedQuestion(scoreByWeightedQuestion);
            survey.setPdfPath(Pdf_path);
            survey.setOFIPdfPath(ofi_pdf_path);
            survey.setExcelPath(Excel_path);
            survey.setWordPath(Word_path);
            survey.setSurveyCompleteStatus(completeStatus);
            survey.setSurveyBranch(branch);
            survey.setSurveyLocation(location);

            cursor.close();
            db.close();
            return survey;
        }

        return null;
    }


    /**
     * Function to get all surveys.
     *
     * @return Arraylist of Survey.
     */
    public static ArrayList<Survey> getAllSurveys() {

        ArrayList<Survey> surveys;

        DbHelper helper = DbHelper.getInstance();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            surveys = new ArrayList<>();

            do {
                long surveyId = cursor.getLong(cursor.getColumnIndex(_ID));
                long userId = cursor.getLong(cursor.getColumnIndex(COLUMN_USER_ID));
                String userName = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME));
                long createdDateTime = cursor.getLong(cursor.getColumnIndex(COLUMN_CREATED_DATE_TIME));
                String surveyTitle = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String updatedBy = cursor.getString(cursor.getColumnIndex(COLUMN_UPDATED_BY));
                long updatedDateTime = cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATED_DATE_TIME));
                long scoreByWeightedCategory = cursor.getLong(cursor.getColumnIndex(COLUMN_SCORE_BY_WEIGHTED_CATEGORY));
                long scoreByWeightedQuestion = cursor.getLong(cursor.getColumnIndex(COLUMN_SCORE_BY_WEIGHTED_QUESTION));
                String Pdf_path = cursor.getString(cursor.getColumnIndex(COLUMN_PDF_PATH));
                String ofi_pdf_path = cursor.getString(cursor.getColumnIndex(COLUMN_OFI_PDF_PATH));
                String Excel_path = cursor.getString(cursor.getColumnIndex(COLUMN_EXCEL_PATH));
                String Word_path = cursor.getString(cursor.getColumnIndex(COLUMN_WORD_PATH));
                String completeStatus = cursor.getString(cursor.getColumnIndex(IS_COMPLETED));
                String branch = cursor.getString(cursor.getColumnIndex(SURVEY_BRANCH));
                String location = cursor.getString(cursor.getColumnIndex(SURVEY_LOCATION));

                Survey survey = new Survey();
                survey.setSurveyId(surveyId);
                survey.setUserId(userId);
                survey.setUserName(userName);
                survey.setCreatedDateTime(createdDateTime);
                survey.setSurveyTitle(surveyTitle);
                survey.setUpdatedBy(updatedBy);
                survey.setUpdatedDateTime(updatedDateTime);
                survey.setScoreByWeightedCategory(scoreByWeightedCategory);
                survey.setScoreByWeightedQuestion(scoreByWeightedQuestion);
                survey.setPdfPath(Pdf_path);
                survey.setOFIPdfPath(ofi_pdf_path);
                survey.setExcelPath(Excel_path);
                survey.setWordPath(Word_path);
                survey.setSurveyCompleteStatus(completeStatus);
                survey.setSurveyBranch(branch);
                survey.setSurveyLocation(location);

                surveys.add(survey);
            }
            while (cursor.moveToNext());

            cursor.close();
            db.close();
            return surveys;
        }

        return null;
    }
}
