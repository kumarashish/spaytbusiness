package com.spaytbusiness;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import utils.Utils;
import utils.Validation;

public class ForgetPassword extends Activity implements View.OnClickListener , WebApiResponseCallback {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.submit)
    Button submit;
    Validation validation;
    @BindView(R.id.emailId)
    EditText emailId;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    AppController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        controller=(AppController)getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        validation=new Validation(ForgetPassword.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                if(validation.isEmailIdValid(emailId))
                {
                    if(Utils.isNetworkAvailable(ForgetPassword.this))
                    {
                        submit.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        controller.getWebApiCall().forgetPassword(Common.forgetPassword,emailId.getText().toString(),this);
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
                if(Utils.getStatus(value))
                {
finish();
                    Toast.makeText(ForgetPassword.this,Utils.getMessage(value),Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(ForgetPassword.this,Utils.getMessage(value),Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onError(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ForgetPassword.this,Utils.getMessage(value),Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);

            }
        });

    }
}