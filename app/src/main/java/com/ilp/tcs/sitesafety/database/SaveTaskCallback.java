package com.ilp.tcs.sitesafety.database;

/**
 * Created by 1244696 on 6/3/2016.
 * Interface to listen to save completion
 */
public interface SaveTaskCallback {
    void onSaveComplete(long surveyId);
}
