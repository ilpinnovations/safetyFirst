package com.ilp.tcs.sitesafety.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.preference.SitePreference;
import com.ilp.tcs.sitesafety.utils.SToast;
import com.ilp.tcs.sitesafety.utils.Util;

import java.io.File;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 15;
    private TextInputLayout mTxtInputLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTxtInputLayoutPassword = (TextInputLayout) findViewById(R.id.ti_pwd);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);

        int hasStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogin) {

            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/HSE/IMAGE");
            if (!directory.isDirectory()) {
                //noinspection ResultOfMethodCallIgnored
                directory.mkdirs();
            }

            if (!Util.isEmpty(mTxtInputLayoutPassword)) {
                Util.clearError(mTxtInputLayoutPassword);
                String pwd = SitePreference.getInstance().getPassword();
                if (pwd.contentEquals(Util.getString(mTxtInputLayoutPassword))) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Util.showError(mTxtInputLayoutPassword, R.string.auth_error);
                    SToast.showSmallToast(R.string.auth_error);
                }
            } else {
                Util.showError(mTxtInputLayoutPassword, R.string.reg_error);
                SToast.showSmallToast(R.string.reg_error);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    SToast.showBigToast(R.string.permission_error);
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
        }
    }
}
