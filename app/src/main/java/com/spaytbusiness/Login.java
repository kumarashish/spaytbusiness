package com.spaytbusiness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import models.BusinessProfile;
import models.UserProfile;
import utils.Utils;
import utils.Validation;

public class Login  extends Activity implements View.OnClickListener, WebApiResponseCallback {
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.signUp)
    TextView signUp;
    @BindView(R.id.paypal)
    ImageView paypal;
    @BindView(R.id.forgetpassword)
    TextView forgotPassword;
    @BindView(R.id.email_id)
    EditText emailId;
    @BindView(R.id.password)
    EditText password;
    AppController controller;
    @BindView(R.id.view)
    LinearLayout view;
    @BindView(R.id.progress_bar)
    ProgressBar progressbar;
    Validation validation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        paypal.setOnClickListener(this);
        submit.setOnClickListener(this);
        signUp.setOnClickListener(this);
        controller=(AppController)getApplicationContext();
        validation=new Validation(Login.this);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.paypal:
                Toast.makeText(Login.this, "Under Development", Toast.LENGTH_SHORT).show();
                break;

            case R.id.submit:
                if((validation.isEmailIdValid(emailId))&&(validation.isNotNull(password)))
                {
                    if(Utils.isNetworkAvailable(Login.this))
                    {
                        progressbar.setVisibility(View.VISIBLE);
                        view.setVisibility(View.GONE);
                        controller.getWebApiCall().login(Common.login,emailId.getText().toString().trim(),password.getText().toString().trim(),"",this);
                    }
                }


                break;
            case R.id.signUp:
                startActivity(new Intent(Login.this,Registration.class));
                break;
            case R.id.forgetpassword:
                startActivity(new Intent(Login.this,ForgetPassword.class));

                break;
        }

    }

    @Override
    public void onSucess(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(Utils.getStatus(value))
                {
                    controller.setProfile(new UserProfile(value));
                    controller.setBusinessProfile(new BusinessProfile(value));
                    controller.getManager().setUserLoggedIn(true);
                    controller.getManager().setUserToken(value);
                    utils.Utils.showToast(Login.this, "Logged in sucessfully.");
                    startActivity(new Intent(Login.this, Dashboard.class));
                    finish();

                }else{
                    Toast.makeText(Login.this,Utils.getMessage(value),Toast.LENGTH_SHORT).show();
                    progressbar.setVisibility(View.GONE);
                    view.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onError(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Login.this,Utils.getMessage(value),Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);
            }
        });


    }
}
