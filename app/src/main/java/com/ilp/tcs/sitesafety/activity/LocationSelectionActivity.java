package com.ilp.tcs.sitesafety.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.adapters.BranchAdapter;
import com.ilp.tcs.sitesafety.adapters.FilteredLocationAdapter;
import com.ilp.tcs.sitesafety.listeners.OnDataReceived;
import com.ilp.tcs.sitesafety.modals.Datas;
import com.ilp.tcs.sitesafety.preference.SitePreference;
import com.ilp.tcs.sitesafety.tasks.RetrieveAsyncTask;
import com.ilp.tcs.sitesafety.utils.Constants;
import com.ilp.tcs.sitesafety.utils.SToast;
import com.ilp.tcs.sitesafety.utils.Util;

public class LocationSelectionActivity extends AppCompatActivity implements View.OnClickListener, OnDataReceived {

    private static final String TAG = LocationSelectionActivity.class.getName();

    private Spinner mSpnBranch;
    private Spinner mSpnLocation;

    private Button mBtnGoToSurvey;

    private BranchAdapter mBranchAdapter;

    private FilteredLocationAdapter mLocationAdapter;

    private String mBranchId;
    private String mLocationId;
    private String mBranchName;
    private String mLocationName;

    private TextView mTxtViewConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selection);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeComponents();
    }

    private void initializeComponents() {

        Datas.listBranches.clear();
        Datas.listFilteredLocations.clear();
        mSpnBranch = (Spinner) findViewById(R.id.spn_branches);
        mBranchAdapter = new BranchAdapter(false);
        mSpnBranch.setAdapter(mBranchAdapter);
        mSpnBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    mBranchId = Datas.listBranches.get(position).getId();
                    mBranchName = Datas.listBranches.get(position).getName();
                    mLocationId = "";
                    new RetrieveAsyncTask(Constants.FILTERED_LOCATION, LocationSelectionActivity.this, mBranchId).execute();
                } catch (Exception e) {
                    Log.e(TAG, e.toString(), e);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpnLocation = (Spinner) findViewById(R.id.spn_location);
        mLocationAdapter = new FilteredLocationAdapter();
        mSpnLocation.setAdapter(mLocationAdapter);
        mSpnLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    mLocationId = Datas.listFilteredLocations.get(position).getId();
                    mLocationName = Datas.listFilteredLocations.get(position).getName();
                } catch (Exception e) {
                    Log.e(TAG, e.toString(), e);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mTxtViewConfig = (TextView) findViewById(R.id.txt_view_config);
        mTxtViewConfig.setOnClickListener(this);

        mBtnGoToSurvey = (Button) findViewById(R.id.btn_go);
        mBtnGoToSurvey.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new RetrieveAsyncTask(Constants.LIST_BRANCH, this).execute();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_go) {

            if (!Util.isEmpty(mBranchId) && !Util.isEmpty(mLocationId)) {

                SitePreference preference = SitePreference.getInstance();
                preference.setBranchId(mBranchId);
                preference.setBranchName(mBranchName);
                preference.setLocationId(mLocationId);
                preference.setLocationName(mLocationName);

                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                SToast.showBigToast(R.string.loc_select_msg);
            }
        } else if (v.getId() == R.id.txt_view_config) {

            startActivity(new Intent(this, SettingsActivity.class));
        }
    }

    @Override
    public void onDataReceived(int result, long id, String msg) {

        if (result == Constants.LIST_BRANCH) {
            mBranchAdapter.notifyDataSetChanged();
        } else if (result == Constants.FILTERED_LOCATION) {
            mLocationAdapter.notifyDataSetChanged();
            if (Datas.listFilteredLocations.size() > 0) {
                mLocationId = Datas.listFilteredLocations.get(0).getId();
                mLocationName = Datas.listFilteredLocations.get(0).getName();
            } else {
                mLocationId = "";
                mLocationName = "";
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_history) {
            startActivity(new Intent(this, CompletedSurveyActivity.class));
        } else if (id == R.id.action_setting) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if(id == R.id.action_location_selection_logout) {
            android.app.AlertDialog.Builder logoutDialog = new android.app.AlertDialog.Builder(LocationSelectionActivity.this);
            logoutDialog.setTitle("Do you want to logout ?");
            logoutDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    try {

                        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.logout_preference), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(getString(R.string.logout_key), true);
                        editor.commit();
                        LocationSelectionActivity.this.finish();
                        System.exit(0);
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(LocationSelectionActivity.this,"Error while logout",Toast.LENGTH_SHORT).show();
                    }
                }

            });

            logoutDialog.setNegativeButton(android.R.string.cancel, null);
            logoutDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
