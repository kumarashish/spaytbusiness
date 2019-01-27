package com.spaytbusiness;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import models.CategoryModel;
import models.RegistrationModel;
import utils.Utils;
import utils.Validation;

public class Register_Step_2 extends Activity implements View.OnClickListener , WebApiResponseCallback {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.categories)
    Spinner categories;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.progressbar2)
    ProgressBar progressBar2;
    @BindView(R.id.mainView)
    LinearLayout mainView;
    AppController controller;
    ArrayList<CategoryModel>categorylist=new ArrayList<>();
    ArrayList<String>categorylistName=new ArrayList<>();
    @BindView(R.id.companyname)
    EditText companyName;
    @BindView(R.id.street)
    EditText street;
    @BindView(R.id.doorNumber)
    EditText door;
    @BindView(R.id.city)
    EditText city;
    @BindView(R.id.zipCode)
    EditText zipcode;
    @BindView(R.id.companyphonenumber)
    EditText c_phone;
    @BindView(R.id.companyEmailId)
    EditText c_emailId;
    @BindView(R.id.companyVatId)
    EditText c_VatId;
    @BindView(R.id.paypalId)
    EditText c_paypalId;
    Validation validation;
    int apiCall=0;
    int validateBusinessName=2,submitRegistration=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        controller=(AppController)getApplicationContext();
        validation=new Validation(Register_Step_2.this);
        ButterKnife.bind(this);
        submit.setOnClickListener(this);
        back.setOnClickListener(this);

        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        c_VatId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Registration.reg_model.setVat_id(s.toString().trim());

            }
        });
        c_emailId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Registration.reg_model.setBusiness_email(s.toString().trim());
            }
        });
        c_paypalId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Registration.reg_model.setPaypal_email(s.toString().trim());
            }
        });
        if(Utils.isNetworkAvailable(Register_Step_2.this))
        {
            progressBar.setVisibility(View.VISIBLE);

            Thread t=new Thread(new Runnable() {
                @Override
                public void run() {

              String result=controller.getWebApiCall().getData(Common.getCategories);
              if(result!=null)
              {if(Utils.getStatus(result)==true)
                  {
                      JSONArray jsonArray=Utils.getJSONArray(result);
                      for(int i=0;i<jsonArray.length();i++)
                      {try {
                          JSONObject jsonObject = jsonArray.getJSONObject(i);
                          CategoryModel model=new CategoryModel(jsonObject);
                          categorylist.add(model);
                          categorylistName.add(model.getCategoryName());
                      }catch (Exception ex)
                      {
                          ex.fillInStackTrace();
                      }

                      }
                      if(categorylistName.size()>0)
                      {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                categories.setAdapter(new ArrayAdapter<String>(Register_Step_2.this,
                                        android.R.layout.simple_spinner_item,categorylistName));
                                progressBar.setVisibility(View.GONE);
                                mainView.setVisibility(View.VISIBLE);
                            }
                        });
                      }
                  }

              }

                }
            });
            t.start();
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

              if((validation.isNotNull(companyName))&&(validation.isNotNull(street))&&(validation.isNotNull(door))&&(validation.isNotNull(city))&&(validation.isNotNull(zipcode))&&(validation.isNotNull(c_phone)))
              {
                  if(Utils.isNetworkAvailable(Register_Step_2.this)) {
                      progressBar2.setVisibility(View.VISIBLE);
                      submit.setVisibility(View.GONE);
                      apiCall = validateBusinessName;
                      controller.getWebApiCall().postFlormData(Common.isBusinessExists, "company_name", companyName.getText().toString(), Register_Step_2.this);
                  }

                  }else{
                  if(companyName.getText().length()==0)
                  {
                      Toast.makeText(Register_Step_2.this,"Please enter Company name",Toast.LENGTH_SHORT).show();
                  }
                  else if(street.getText().length()==0)
                  {
                      Toast.makeText(Register_Step_2.this,"Please enter Street name",Toast.LENGTH_SHORT).show();
                  }else if(door.getText().length()==0)
                  {
                      Toast.makeText(Register_Step_2.this,"Please enter Door Number",Toast.LENGTH_SHORT).show();
                  }else if(city.getText().length()==0)
                  {
                      Toast.makeText(Register_Step_2.this,"Please enter City",Toast.LENGTH_SHORT).show();
                  }else if(zipcode.getText().length()==0)
                  {
                      Toast.makeText(Register_Step_2.this,"Please enter Company zipcode",Toast.LENGTH_SHORT).show();
                  }else if(c_phone.getText().length()==0)
                  {   Toast.makeText(Register_Step_2.this,"Please enter Company Phone number",Toast.LENGTH_SHORT).show();

                  }
              }

                break;
        }

    }



    public void submitRegistration()
    {
        apiCall=submitRegistration;
        Registration.reg_model.setCompany_name(companyName.getText().toString());
        Registration.reg_model.setCategory_id(categorylist.get(categories.getSelectedItemPosition()).getCategoryId());
        Registration.reg_model.setStreet_name(street.getText().toString());
        Registration.reg_model.setDoor_no(door.getText().toString());
        Registration.reg_model.setCity(city.getText().toString());
        Registration.reg_model.setZip_code(zipcode.getText().toString());
        Registration.reg_model.setPhoneNumber(c_phone.getText().toString());
        controller.getWebApiCall().register(Common.registerUser,Registration.reg_model,Register_Step_2.this);


    }

    @Override
    public void onSucess(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            switch (apiCall) {
                case 2:
                    if(Utils.getStatus(value)==false) {
                        submitRegistration();
                    }else{
                        Toast.makeText(Register_Step_2.this,Utils.getMessage(value),Toast.LENGTH_SHORT).show();
                        progressBar2.setVisibility(View.GONE);
                        submit.setVisibility(View.VISIBLE);
                    }
                    break;
                case 3:
                    if(Utils.getStatus(value)==true) {
                        Toast.makeText(Register_Step_2.this,Utils.getMessage(value),Toast.LENGTH_SHORT).show();
                        Utils.logout(Register_Step_2.this);
                    }else{
                        Toast.makeText(Register_Step_2.this,Utils.getMessage(value),Toast.LENGTH_SHORT).show();
                        progressBar2.setVisibility(View.GONE);
                        submit.setVisibility(View.VISIBLE);
                    }
                    break;


        }
            }
        });
    }

    @Override
    public void onError(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Register_Step_2.this,Utils.getMessage(value),Toast.LENGTH_SHORT).show();
                progressBar2.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
            }
        });
    }
}