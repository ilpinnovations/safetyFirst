package com.ilp.tcs.sitesafety.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.preference.SitePreference;
import com.ilp.tcs.sitesafety.utils.SToast;
import com.ilp.tcs.sitesafety.utils.Util;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout mTxtLayoutEmployeeId;
    private TextInputLayout mTxtLayoutName;
    private TextInputLayout mTxtLayoutPassword;

    private Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registration);
        initializeComponents();
    }

    private void initializeComponents() {

        mTxtLayoutEmployeeId = (TextInputLayout) findViewById(R.id.til_employee_id);
        mTxtLayoutName = (TextInputLayout) findViewById(R.id.til_name);
        mTxtLayoutPassword = (TextInputLayout) findViewById(R.id.til_pwd);

        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_register) {

            if (!Util.isEmpty(mTxtLayoutEmployeeId) && !Util.isEmpty(mTxtLayoutName) && !Util.isEmpty(mTxtLayoutPassword)) {

                SitePreference preference = SitePreference.getInstance();

                preference.setEmployeeId(Util.getString(mTxtLayoutEmployeeId));
                preference.setEmployeeName(Util.getString(mTxtLayoutName));
                preference.setPassword(Util.getString(mTxtLayoutPassword));

                preference.setFirstTimeLaunch(false);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                SToast.showSmallToast(R.string.reg_error);
            }
        }
    }
}
