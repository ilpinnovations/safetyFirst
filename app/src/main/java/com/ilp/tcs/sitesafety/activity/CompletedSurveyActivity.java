package com.ilp.tcs.sitesafety.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.adapters.SurveyListAdapter;
import com.ilp.tcs.sitesafety.database.DbHelper;
import com.ilp.tcs.sitesafety.database.TblSurvey;
import com.ilp.tcs.sitesafety.modals.ChildItem;
import com.ilp.tcs.sitesafety.modals.GroupItem;
import com.ilp.tcs.sitesafety.modals.Survey;
import com.ilp.tcs.sitesafety.utils.Util;

import java.util.ArrayList;

/**
 * This class holds the List of completed surveys
 */
public class CompletedSurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Audit History");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView surveyHistoryRecycler = (RecyclerView) findViewById(R.id.rvSurveyHistoryList);
        RecyclerView.LayoutManager surveyHistoryListLayout = new LinearLayoutManager(this);
        surveyHistoryRecycler.setLayoutManager(surveyHistoryListLayout);
        ArrayList<Survey> surveyArrayList = TblSurvey.getAllSurveys();
//        DbHelper.logPrintTable(TblSurvey.TABLE_NAME);
        if (surveyArrayList == null || surveyArrayList.isEmpty()) {
            Toast.makeText(this, "No audit Found", Toast.LENGTH_SHORT).show();
        } else {
            SurveyListAdapter surveyListAdapter = new SurveyListAdapter(surveyArrayList, this);
            surveyHistoryRecycler.setAdapter(surveyListAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_survey_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_setting) {
            startActivity(new Intent(this, SettingsActivity.class));
        }else if(id == R.id.action_complete_servey_logout) {
            android.app.AlertDialog.Builder logoutDialog = new android.app.AlertDialog.Builder(CompletedSurveyActivity.this);
            logoutDialog.setTitle("Do you want to logout ?");
            logoutDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    try {
                        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.logout_preference), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(getString(R.string.logout_key), true);
                        editor.commit();

                        CompletedSurveyActivity.this.finish();
                        System.exit(0);
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(CompletedSurveyActivity.this,"Error while logout",Toast.LENGTH_SHORT).show();
                    }
                }

            });

            logoutDialog.setNegativeButton(android.R.string.cancel, null);
            logoutDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
