package com.spaytbusiness;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import models.BusinessProfile;
import models.RegistrationModel;
import models.UserProfile;
import utils.Utils;
import utils.Validation;

public class Registration extends Activity implements View.OnClickListener, WebApiResponseCallback {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.firstName)
    EditText fname;
    @BindView(R.id.lastName)
    EditText lname;
    @BindView(R.id.salutation)
    Spinner salutation;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirmPassword)
    EditText confirmPassword;
    @BindView(R.id.next)
    Button next ;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    Validation validation;
    AppController controller;

    public static RegistrationModel reg_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_step1);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        controller=(AppController)getApplicationContext();
        validation=new Validation(Registration.this);
salutation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView child=(TextView) parent.getChildAt(0);
                child.setTextColor(Color.WHITE);
                child.setTextSize(18);
                child.setTypeface(getResources().getFont(R.font.light));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.next:
                if((validation.isNotNull(fname))&&(validation.isNotNull(lname))&&(validation.isValidEmail(email.getText().toString()))&&(validation.isNotNull(password)&&(validation.isPassword_ConfirmPasswordSame(password,confirmPassword))))
                {

                    if(Utils.isNetworkAvailable(Registration.this))
                    {
                        progressBar.setVisibility(View.VISIBLE);
                        next.setVisibility(View.GONE);
                        controller.getWebApiCall().forgetPassword(Common.isUserExists,email.getText().toString(),this);
                    }
                }

                break;

        }

    }

    @Override
    public void onSucess(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(Utils.getStatus(value)==false)
                {    reg_model=new RegistrationModel(fname.getText().toString(),lname.getText().toString(),salutation.getSelectedItem().toString(),email.getText().toString(),password.getText().toString());
                    startActivity(new Intent(Registration.this,Register_Step_2.class));
                    progressBar.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(Registration.this,Utils.getMessage(value),Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onError(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Registration.this,Utils.getMessage(value),Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
            }
        });


    }
}