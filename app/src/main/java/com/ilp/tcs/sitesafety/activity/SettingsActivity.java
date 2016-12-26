package com.ilp.tcs.sitesafety.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.adapters.SettingsAdapter;
import com.ilp.tcs.sitesafety.utils.Constants;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = (ListView) findViewById(R.id.listview_settings_menu);
        SettingsAdapter adapter = new SettingsAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    startActivity(new Intent(SettingsActivity.this, BranchActivity.class));
                } else if (position == 1) {
                    startActivity(new Intent(SettingsActivity.this, LocationsActivity.class));
                } else if (position == 2) {
                    startActivity(new Intent(SettingsActivity.this, ChangePasswordActivity.class));
                } else if (position == 3) {
                    Intent i = new Intent(SettingsActivity.this, SlideScreen.class);
                    i.putExtra(Constants.IS_HELP_FROM_SETTINGS, 1);
                    startActivity(i);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
