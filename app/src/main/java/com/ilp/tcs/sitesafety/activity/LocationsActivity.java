package com.ilp.tcs.sitesafety.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.fragment.FragmentAddLocation;
import com.ilp.tcs.sitesafety.fragment.FragmentListLocation;
import com.ilp.tcs.sitesafety.listeners.OnActivityFragmentCommunication;
import com.ilp.tcs.sitesafety.utils.Constants;

public class LocationsActivity extends AppCompatActivity implements OnActivityFragmentCommunication {

    private Toolbar mToolbar;

    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onActivityCommunication(Constants.ADD_LOCATION);
                mToolbar.setTitle("Add Location");
                mFab.hide();

            }
        });

        onActivityCommunication(Constants.LIST_LOCATION);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            doOnBackPress();
        }
        return false;
    }

    private void doOnBackPress() {
        if (getCurrentFragment() instanceof FragmentAddLocation) {
            onActivityCommunication(Constants.LIST_LOCATION);
        } else {
            finish();
        }
    }

    protected Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.content_frame);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            doOnBackPress();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCommunication(int type) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        Fragment fragment = null;
        if (type == Constants.ADD_LOCATION) {
            fragment = new FragmentAddLocation();
        } else if (type == Constants.LIST_LOCATION) {
            fragment = new FragmentListLocation();
            mToolbar.setTitle("Locations");
            mFab.show();
        }

        transaction.replace(R.id.content_frame, fragment, "").commit();
    }
}
