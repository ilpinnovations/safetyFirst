package com.ilp.tcs.sitesafety.activity;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.preference.SitePreference;
import com.ilp.tcs.sitesafety.utils.SToast;
import com.ilp.tcs.sitesafety.utils.Util;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout tilCurrPass, tilNewPass, tilConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Change Password");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tilCurrPass = (TextInputLayout) findViewById(R.id.tilCurrPass);
        tilNewPass = (TextInputLayout) findViewById(R.id.tilNewPass);
        tilConfirmPass = (TextInputLayout) findViewById(R.id.tilConfirmPass);

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        if (Util.isEmpty(tilCurrPass)) {
            Util.showError(tilCurrPass, "Please enter current password");
        } else if (Util.isEmpty(tilNewPass)) {
            Util.showError(tilNewPass, "Please enter new password");
        } else if (Util.isEmpty(tilConfirmPass)) {
            Util.showError(tilConfirmPass, "Please enter password to confirm");
        } else {
            Util.clearError(tilCurrPass);
            Util.clearError(tilNewPass);
            Util.clearError(tilConfirmPass);
            String pwd = SitePreference.getInstance().getPassword();

            if (pwd.contentEquals(Util.getString(tilCurrPass))) {
                String newPass = Util.getString(tilNewPass);
                String confirmPass = Util.getString(tilConfirmPass);

                if (newPass.equals(confirmPass)) {
                    SitePreference.getInstance().setPassword(newPass);
                    SToast.showSmallToast("Password changed successfully");
                    onBackPressed();
                } else {
                    Util.showError(tilConfirmPass, "Password do not match");
                }
            } else {
                Util.showError(tilCurrPass, R.string.auth_error);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
