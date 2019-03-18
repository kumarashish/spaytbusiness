package com.spaytbusiness;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import models.UserProfile;
import utils.Utils;
import utils.Validation;

/**
 * Created by ashish.kumar on 26-02-2019.
 */

public class BusinessUserDetails  extends Activity implements View.OnClickListener, WebApiResponseCallback{
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.heading)
    TextView heading;
    @BindView(R.id.salutation)
    Spinner salutation;
            @BindView(R.id.firstName)
            EditText fname;
            @BindView(R.id.lastName)
            EditText lname;
            @BindView(R.id.email)
            EditText email;
            @BindView(R.id.isactive)
    Switch isactive;
            @BindView(R.id.role)
    Spinner role;
            @BindView(R.id.submit)
            Button submit;
    public static UserProfile model=null;
    int apiCall;
    int addUser=1,updateUser=2;
    Validation validation;
AppController controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_user_details);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        controller=(AppController)getApplicationContext();
        validation=new Validation(this);
        setValue();
        salutation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView child=(TextView) parent.getChildAt(0);
                child.setTextColor(getResources().getColor(R.color.blue,getTheme()));
                child.setTextSize(18);
               // child.setTypeface(getResources().getFont(R.font.light));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        isactive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    isactive.setTextColor(getResources().getColor(R.color.blue,getTheme()));
                }else {
                    isactive.setTextColor(getResources().getColor(R.color.grey,getTheme()));
                }
            }
        });
        role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView child=(TextView) parent.getChildAt(0);
                child.setTextColor(getResources().getColor(R.color.blue,getTheme()));
                child.setTextSize(18);
              //  child.setTypeface(getResources().getFont(R.font.light));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setValue() {
        if (model == null) {
            heading.setText("Add User");
            submit.setText("Add");
        } else {
            heading.setText("Business User Details");
            submit.setText("Update");
            salutation.setSelection(getIndex(model.getSalutation()));
            fname.setText(model.getFirst_name());
            lname.setText(model.getLast_name());
            email.setText(model.getEmail());
            isactive.setChecked(getCheck(model.getIs_active()));
            role.setSelection(getIndexRole(model.getRole()));

        }
    }
    @Override
    public void onClick(View v) {
switch (v.getId())
{
    case R.id.back:
        finish();
        break;
    case R.id.submit:
        if(isAllFildsValidated()) {

            submit.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            if(model==null) {
                apiCall =addUser;
                controller.getWebApiCall().postData(Common.addBusinessUser, controller.getManager().getUserToken(), Common.addUserKeys, getData(), BusinessUserDetails.this);
            }else {
                apiCall =updateUser;
                controller.getWebApiCall().postData(Common.updateBusinessUser, controller.getManager().getUserToken(), Common.updateUserKeys, getData(), BusinessUserDetails.this);

            }
        }
            break;
}
    }

    public String[] getData() {
        String checked = "1";
        String roleType = "1";
        if (isactive.isChecked() == false) {
            checked = "0";
        }
        if (role.getSelectedItemPosition() == 1) {
            roleType = "2";
        }
        if (model == null) {
            return new String[]{fname.getText().toString(), lname.getText().toString(), salutation.getSelectedItem().toString(), email.getText().toString(), checked, roleType,"12345"};
        } else {
            return new String[]{model.getUser_id(), fname.getText().toString(), lname.getText().toString(), salutation.getSelectedItem().toString(), email.getText().toString(), checked, roleType};
        }
    }
    public boolean isAllFildsValidated()
    {
        if((fname.getText().length()>0)&&(lname.getText().length()>0)&&(email.getText().length()>0))
        {
            if(validation.isValidEmail(email.getText().toString())) {
                return true;
            }
        }else{
            if(fname.getText().length()==0)
            {
                Toast.makeText(BusinessUserDetails.this,"Please enter first name",Toast.LENGTH_SHORT).show();
            }else  if(lname.getText().length()==0)
            {
                Toast.makeText(BusinessUserDetails.this,"Please enter last name",Toast.LENGTH_SHORT).show();
            }else  if(email.getText().length()==0)
            {
                Toast.makeText(BusinessUserDetails.this,"Please enter Email Id name",Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }
@RequiresApi(api = Build.VERSION_CODES.M)
public boolean getCheck(String value)
{
    boolean status=false;
    switch (value)
    {
        case "1":
            status=true;
            isactive.setTextColor(getResources().getColor(R.color.blue,getTheme()));

            break;
        case "0":
            status=false;
            isactive.setTextColor(getResources().getColor(R.color.grey,getTheme()));
            break;
    }
    return status;
}
    public int getIndex(String value) {
        int index = 0;
        switch (value) {
            case "Herr":
                index = 0;
                break;
            case "Frau":
                index = 1;
                break;
        }
        return index;
    }
    public int getIndexRole(String value) {
        int index = 0;
        switch (value) {
            case "Admin":
                index = 0;
                break;
            case "Manager":
                index = 1;
                break;
        }
        return index;
    }

    @Override
    public void onSucess(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(Utils.getStatus(value))
                {
                    switch (apiCall)
                    {
                        case 1:
                            Utils.showToast(BusinessUserDetails.this,"User Added  Sucessfully.");
                            Intent data = new Intent();
                            setResult(RESULT_OK, data);
                            finish();
                            break;
                        case 2:
                            Utils.showToast(BusinessUserDetails.this,"UserDetails updated Sucessfully.");
                            data = new Intent();
                            setResult(RESULT_OK, data);
                            finish();
                            break;
                    }

                }else{
                    Utils.showToast(BusinessUserDetails.this,Utils.getMessage(value));

                }
                progressBar.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onError(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
                Utils.showToast(BusinessUserDetails.this,Utils.getMessage(value));
            }
        });
    }
}
