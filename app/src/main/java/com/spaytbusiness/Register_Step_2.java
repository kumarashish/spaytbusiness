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

    @BindView(R.id. input_company_name)
    android.support.design.widget.TextInputLayout input_company_name;
    @BindView(R.id. input_doornumber)
    android.support.design.widget.TextInputLayout input_doornumber;
    @BindView(R.id. input_sreet)
    android.support.design.widget.TextInputLayout input_sreet;
    @BindView(R.id. input_city)
    android.support.design.widget.TextInputLayout input_city;
    @BindView(R.id. input_pincode)
    android.support.design.widget.TextInputLayout input_pincode;
    @BindView(R.id. input_c_phone)
    android.support.design.widget.TextInputLayout input_c_phone;
    @BindView(R.id. input_c_email)
    android.support.design.widget.TextInputLayout input_c_email;

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
        companyName.addTextChangedListener(new MyTextWatcher(companyName));
        door.addTextChangedListener(new MyTextWatcher(door));
        street.addTextChangedListener(new MyTextWatcher(street));
        city.addTextChangedListener(new MyTextWatcher(city));
        zipcode.addTextChangedListener(new MyTextWatcher(zipcode));
        c_phone.addTextChangedListener(new MyTextWatcher(c_phone));
        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView child=(TextView) parent.getChildAt(0);
                child.setTextColor(Color.WHITE);
                child.setTextSize(18);
                //child.setTypeface(getResources().getFont(R.font.light));
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
                      categorylist.clear();
                      categorylistName.add("Select Business Category");
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
                                categories.setAdapter(new ArrayAdapter<String>(Register_Step_2.this, android.R.layout.simple_spinner_item,categorylistName));
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
                if (!validation.isNotNull(companyName, input_company_name)) {
                    return;
                }
                if(categories.getSelectedItemPosition()==0)
                {   Utils.showToast(Register_Step_2.this,"Please select business category");
                    return;
                }
                if (!validation.isNotNull(door, input_doornumber)) {
                    return;
                }
                if (!validation.isNotNull(street, input_sreet)) {
                    return;
                }
                if (!validation.isNotNull(city, input_city)) {
                    return;
                }
                if (!validation.isNotNull(zipcode, input_pincode)) {
                    return;
                }
                if (!validation.isNotNull(c_phone, input_c_phone)) {
                    return;
                }

                if (Utils.isNetworkAvailable(Register_Step_2.this)) {
                    progressBar2.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.GONE);
                    apiCall = validateBusinessName;
                    controller.getWebApiCall().postFlormData(Common.isBusinessExists, "company_name", companyName.getText().toString(), Register_Step_2.this);


                }

                break;
        }

    }



    public void submitRegistration()
    {
        apiCall=submitRegistration;
        Registration.reg_model.setCompany_name(companyName.getText().toString());
        Registration.reg_model.setCategory_id(categorylist.get(categories.getSelectedItemPosition()-1).getCategoryId());
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

    private class MyTextWatcher implements TextWatcher {
        private View view;
        private MyTextWatcher(View view) {
            this.view = view;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.firstName:
                    if(companyName.getText().length()>2) {
                        validation.isNotNull(companyName,input_company_name);
                    }
                    break;


                case R.id.doorNumber:
                    if(door.getText().length()>1) {
                        validation.isNotNull(door, input_doornumber);
                    }
                    break;
                case R.id.street:
                    if(street.getText().length()>4) {
                        validation.isNotNull(street, input_sreet);
                    }
                    break;


                case R.id.city:
                    if(city.getText().length()>1) {
                        validation.isNotNull(city, input_city);
                    }
                    break;
                case R.id.zipcode:
                    if(zipcode.getText().length()>1) {
                        validation.isNotNull(zipcode, input_pincode);
                    }
                    break;
                    case R.id.companyphonenumber:
                        if(c_phone.getText().length()>4) {
                            validation.isNotNull(c_phone, input_c_phone);
                        }
                        break;

            }
        }
    }
}