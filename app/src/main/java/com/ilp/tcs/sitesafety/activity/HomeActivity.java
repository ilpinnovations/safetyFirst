package com.ilp.tcs.sitesafety.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ilp.tcs.sitesafety.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAudit, btnHistory, btnSettings, btnRange;

    public boolean logout_status = false;
    public boolean first_time = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        SharedPreferences sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.logout_preference), MODE_PRIVATE);
        logout_status = sharedPreferences.getBoolean(getResources().getString(R.string.logout_key),true);

        if(logout_status == true)
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(getString(R.string.logout_key), false);
            editor.commit();

            this.finish();
        }





        Toolbar toolbar = (Toolbar) findViewById(R.id.hometoolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);

        btnAudit = (Button) findViewById(R.id.btnAudit);
        btnHistory = (Button) findViewById(R.id.btnHistory);
        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnRange = (Button) findViewById(R.id.btnRange);

        btnAudit.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnRange.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.action_home_logout) {
          //  Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
            android.app.AlertDialog.Builder logoutDialog = new android.app.AlertDialog.Builder(HomeActivity.this);
            logoutDialog.setTitle("Do you want to logout ?");
            logoutDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    try {

                        HomeActivity.this.finish();
                        System.exit(0);
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(HomeActivity.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                }

            });

            logoutDialog.setNegativeButton(android.R.string.cancel, null);
            logoutDialog.show();
        }




        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onResume() {
        super.onResume();





    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAudit:
                Intent intent = new Intent(this, LocationSelectionActivity.class);
                startActivity(intent);
                break;
            case R.id.btnHistory:
                startActivity(new Intent(this, CompletedSurveyActivity.class));
                break;
            case R.id.btnSettings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.btnRange:
                startActivity(new Intent(this, RangeActivity.class));
                break;
        }
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }
}
