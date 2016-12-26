package com.ilp.tcs.sitesafety.preference;

import android.app.Activity;
import android.content.SharedPreferences;

import com.ilp.tcs.sitesafety.activity.SiteSafetyApplication;
import com.ilp.tcs.sitesafety.utils.Constants;

/**
 * Created by 1220278 on 6/30/2016.
 */
public class SitePreference {

    private static final String TAG = SitePreference.class.getName();

    /**
     * shared preference constant string.
     */
    private static final String SHARED_PREFERENCE = "spreference";

    /**
     * constant string for employee id.
     */
    private static final String EMP_ID = "empId";

    /**
     * constant string for employee name.
     */
    private static final String EMP_NAME = "empName";

    /**
     * constant string for employee password.
     */
    private static final String EMP_PASSWORD = "password";

    /**
     * constant string for edit flag.
     */
    private static final String EDIT_SAVE_FLAG = "flag";

    /**
     * this will hold the String as first_time_launch.
     */
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String BRANCH_ID = "branch_id";

    private static final String BRANCH_NAME = "branch_name";

    private static final String LOCATION_ID = "location_id";

    private static final String LOCATION_NAME = "location_name";

    private static final String PDF_PATH = "pdfPath";

    private static final String EXCEL_PATH = "excelPath";

    private static final String OFI_EXCEL_PATH = "ofiexcelpath";

    private static SitePreference sInstance;

    private static SharedPreferences sPreference;

    private SitePreference() {
    }

    public static synchronized SitePreference getInstance() {
        if (sInstance == null) {
            sInstance = new SitePreference();
            sPreference = SiteSafetyApplication.getContext().getSharedPreferences(SHARED_PREFERENCE, Activity.MODE_PRIVATE);
        }
        return sInstance;
    }

    public String getEmployeeId() {
        return sPreference.getString(EMP_ID, "");
    }

    public String getEmployeeName() {
        return sPreference.getString(EMP_NAME, "");
    }

    public long getSurveyId() {
        return sPreference.getLong(Constants.SURVEY_ID, -1);
    }

    public boolean getEditFlag() {
        return sPreference.getBoolean(EDIT_SAVE_FLAG, false);
    }

    public String getPassword() {
        return sPreference.getString(EMP_PASSWORD, "");
    }

    public String getBranchId() {
        return sPreference.getString(BRANCH_ID, "");
    }

    public String getBranchName() {
        return sPreference.getString(BRANCH_NAME, "Default");
    }

    public String getLocationId() {
        return sPreference.getString(LOCATION_ID, "Default");
    }

    public String getLocationName() {
        return sPreference.getString(LOCATION_NAME, "");
    }

    public String getPdfPath() {
        return sPreference.getString(PDF_PATH, "");
    }

    public String getExcelPath() {
        return sPreference.getString(EXCEL_PATH, "");
    }
    public String getOfiExcelPath() {
        return sPreference.getString(OFI_EXCEL_PATH, "");
    }

    public boolean setEmployeeId(String id) {
        SharedPreferences.Editor editor = sPreference.edit();
        editor.putString(EMP_ID, id);
        return editor.commit();
    }

    public boolean setEmployeeName(String name) {
        SharedPreferences.Editor editor = sPreference.edit();
        editor.putString(EMP_NAME, name);
        return editor.commit();
    }

    public boolean setSurveyId(long surveyid) {
        SharedPreferences.Editor editor = sPreference.edit();
        editor.putLong(Constants.SURVEY_ID, surveyid);
        return editor.commit();
    }

    public boolean setEditFlag(boolean editFlag) {
        SharedPreferences.Editor editor = sPreference.edit();
        editor.putBoolean(EDIT_SAVE_FLAG, editFlag);
        return editor.commit();
    }

    public boolean setPassword(String pwd) {
        SharedPreferences.Editor editor = sPreference.edit();
        editor.putString(EMP_PASSWORD, pwd);
        return editor.commit();
    }

    public boolean setBranchId(String branchID) {
        SharedPreferences.Editor editor = sPreference.edit();
        editor.putString(BRANCH_ID, branchID);
        return editor.commit();
    }

    public boolean setBranchName(String branchName) {
        SharedPreferences.Editor editor = sPreference.edit();
        editor.putString(BRANCH_NAME, branchName);
        return editor.commit();
    }

    public boolean setLocationId(String locationId) {
        SharedPreferences.Editor editor = sPreference.edit();
        editor.putString(LOCATION_ID, locationId);
        return editor.commit();
    }

    public boolean setLocationName(String locationName) {
        SharedPreferences.Editor editor = sPreference.edit();
        editor.putString(LOCATION_NAME, locationName);
        return editor.commit();
    }

    public boolean setPdfPath(String path) {
        SharedPreferences.Editor editor = sPreference.edit();
        editor.putString(PDF_PATH, path);
        return editor.commit();
    }

    public boolean setExcelPath(String path) {
        SharedPreferences.Editor editor = sPreference.edit();
        editor.putString(EXCEL_PATH, path);
        return editor.commit();
    }
    public boolean setOFiExcelPath(String path) {
        SharedPreferences.Editor editor = sPreference.edit();
        editor.putString(OFI_EXCEL_PATH, path);
        return editor.commit();
    }



    /**
     * @return returning a boolean that is activity launched for the first time.
     */
    public boolean isFirstTimeLaunch() {
        return sPreference.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    /**
     * @param isFirstTime taking an argument of boolean type for the validation of first time launch.
     */
    public void setFirstTimeLaunch(boolean isFirstTime) {
        SharedPreferences.Editor editor = sPreference.edit();
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.apply();
    }

    public void removeFilePath() {
        SharedPreferences.Editor editor = sPreference.edit();
        editor.remove(PDF_PATH);
        editor.remove(EXCEL_PATH);
        editor.apply();
    }
}
