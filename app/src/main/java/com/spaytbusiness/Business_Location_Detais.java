package com.spaytbusiness;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import models.Business_locations;
import utils.Utils;

public class Business_Location_Detais extends Activity implements View.OnClickListener, WebApiResponseCallback {
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.heading)
    TextView heading;
    @BindView(R.id.location_name)
    EditText location_name;
    @BindView(R.id.doorNumber)
    EditText doorNumber;
    @BindView(R.id.street)
    EditText street;
    @BindView(R.id.city)
    EditText city;
    @BindView(R.id.zipcode)
    EditText zipcode;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.mondayMode)
    Spinner mondayMode;
    @BindView(R.id.m_mFrom)
    EditText m_mFrom;
    @BindView(R.id.m_mTo)
    EditText m_mTo;
    @BindView(R.id.m_aFrom)
    EditText m_aFrom;
    @BindView(R.id.m_aTo)
    EditText m_aTo;
    @BindView(R.id.tuesdayMode)
    Spinner tuesdayMode;
    @BindView(R.id.t_mFrom)
    EditText t_mFrom;
    @BindView(R.id.t_mTo)
    EditText t_mTo;
    @BindView(R.id.t_aFrom)
    EditText t_aFrom;
    @BindView(R.id.t_aTo)
    EditText t_aTo;
    @BindView(R.id.wednesdayMode)
    Spinner wednesdayMode;
    @BindView(R.id.w_mFrom)
    EditText w_mFrom;
    @BindView(R.id.w_mTo)
    EditText w_mTo;
    @BindView(R.id.w_aFrom)
    EditText w_aFrom;
    @BindView(R.id.w_aTo)
    EditText w_aTo;
    @BindView(R.id.thursdayMode)
    Spinner thursdayMode;
    @BindView(R.id.th_mFrom)
    EditText th_mFrom;
    @BindView(R.id.th_mTo)
    EditText th_mTo;
    @BindView(R.id.th_aFrom)
    EditText th_aFrom;
    @BindView(R.id.th_aTo)
    EditText th_aTo;
    @BindView(R.id.fridayMode)
    Spinner fridayMode;
    @BindView(R.id.f_mFrom)
    EditText f_mFrom;
    @BindView(R.id.f_mTo)
    EditText f_mTo;
    @BindView(R.id.f_aFrom)
    EditText f_aFrom;
    @BindView(R.id.f_aTo)
    EditText f_aTo;
    @BindView(R.id.saturdaymode)
    Spinner saturdaymode;
    @BindView(R.id.sat_mFrom)
    EditText sat_mFrom;
    @BindView(R.id.sat_mTo)
    EditText sat_mTo;
    @BindView(R.id.sat_aFrom)
    EditText sat_aFrom;
    @BindView(R.id.sat_aTo)
    EditText sat_aTo;
    @BindView(R.id.sundayMode)
    Spinner sundayMode;
    @BindView(R.id.sun_mFrom)
    EditText sun_mFrom;
    @BindView(R.id.sun_mTo)
    EditText sun_mTo;
    @BindView(R.id.sun_aFrom)
    EditText sun_aFrom;
    @BindView(R.id.sun_aTo)
    EditText sun_aTo;
    @BindView(R.id.submit)
    Button submit;
    public static Business_locations model=null;
    AppController controller;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_location_details);
        ButterKnife.bind(this);
        controller=(AppController)getApplicationContext();
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Please wait....");
        dialog.setCancelable(false);
        setValue();
    }

    public void setValue() {
        if (model == null) {
            heading.setText("Add Location");
            submit.setText("Submit");
        } else {
            submit.setText("Update");
            location_name.setText(model.getLocation_name());
            doorNumber.setText(model.getDoor_no());
            street.setText(model.getStreet_name());
            city.setText(model.getCity());
            zipcode.setText(model.getZip_code());
            phone.setText(model.getPhone_number());
            description.setText(model.getDescription());


            mondayMode.setSelection(getIndex(model.getMonday_mode()));
            m_mFrom.setText(model.getMonday_morning_from());
            m_mTo.setText(model.getMonday_morning_to());
            m_aFrom.setText(model.getMonday_afternoon_from());
            m_aTo.setText(model.getMonday_afternoon_to());
            mondayMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
            tuesdayMode.setSelection(getIndex(model.getTuesday_mode()));
            tuesdayMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
            t_mFrom.setText(model.getTuesday_morning_from());
            t_mTo.setText(model.getTuesday_morning_to());
            t_aFrom.setText(model.getTuesday_afternoon_from());
            t_aTo.setText(model.getTuesday_afternoon_to());

            wednesdayMode.setSelection(getIndex(model.getWednesday_mode()));
            wednesdayMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }
            });
            w_mFrom.setText(model.getWednesday_morning_from());
            w_mTo.setText(model.getWednesday_morning_to());
            w_aFrom.setText(model.getWednesday_afternoon_from());
            w_aTo.setText(model.getWednesday_afternoon_to());

            thursdayMode.setSelection(getIndex(model.getThursday_mode()));
            thursdayMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }
            });
            th_mFrom.setText(model.getThursday_morning_from());
            th_mTo.setText(model.getThursday_morning_to());
            th_aFrom.setText(model.getThursday_afternoon_from());
            th_aTo.setText(model.getThursday_afternoon_to());
            fridayMode.setSelection(getIndex(model.getFriday_mode()));
            fridayMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }
            });
            f_mFrom.setText(model.getFriday_morning_from());
            f_mTo.setText(model.getFriday_morning_to());
            f_aFrom.setText(model.getFriday_afternoon_from());
            f_aTo.setText(model.getFriday_afternoon_to());
            saturdaymode.setSelection(getIndex(model.getSaturday_mode()));
            saturdaymode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }
            });
            sat_mFrom.setText(model.getSaturday_morning_from());
            sat_mTo.setText(model.getSaturday_morning_to());
            sat_aFrom.setText(model.getSaturday_afternoon_from());
            sat_aTo.setText(model.getSaturday_afternoon_to());
            sundayMode.setSelection(getIndex(model.getSunday_mode()));
            sundayMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                    if(position==0)
                    {

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }
            });
            sun_mFrom.setText(model.getSunday_morning_from());
            sun_mTo.setText(model.getSunday_morning_to());
            sun_aFrom.setText(model.getSunday_afternoon_from());
            sun_aTo.setText(model.getSunday_afternoon_to());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.submit:
                if (isFieldsValidated()) {
                    if (Utils.isNetworkAvailable(Business_Location_Detais.this)) {
                        String url = Common.updateBusinessLocation;
                        String[] keys = null;
                        if (model == null) {
                            url = Common.addBusinessLocation;
                            keys = Common.addBusinessLocationKeys;
                        } else {
                            keys = Common.updateBusinessLocationKeys;
                        }
                       dialog.show();
                        controller.getWebApiCall().postData(url, controller.getManager().getUserToken(), keys, getRequestValues(), this);
                    }
                }
                break;
        }

    }

    public int getIndex(String value)
    {int index=0;
    switch (value) {
        case "A":
            index = 0;
            break;
        case "O":
            index = 1;
            break;
        case "S":
            index = 2;
            break;
        case "C":
            index = 3;
            break;
    }
            return index;

    }
    public boolean isFieldsValidated()
    {
     if((location_name.getText().length()>0)&&(doorNumber.getText().length()>0)&&( street.getText().length()>0)&&(city.getText().length()>0)&&(zipcode.getText().length()>0)&&(phone.getText().length()>0))
     {
         return true;
     }else{
         if(location_name.getText().length()==0)
         {
             Utils.showToast(Business_Location_Detais.this,"Please enter location name");
         }
         else if(doorNumber.getText().length()==0)
         {
             Utils.showToast(Business_Location_Detais.this,"Please enter door number");
         }else if(city.getText().length()==0)
         {
             Utils.showToast(Business_Location_Detais.this,"Please enter city");
         }
         else if(zipcode.getText().length()==0)
         {
             Utils.showToast(Business_Location_Detais.this,"Please enter zipcode");
         }else if(phone.getText().length()==0)
         {
             Utils.showToast(Business_Location_Detais.this,"Please enter phobne number");
         }
         return false;
     }

    }

    public String[] getRequestValues() {
        String locationname = location_name.getText().toString();
        String street_name = street.getText().toString();
        String door_no =doorNumber.getText().toString();
        String city_name = city.getText().toString();
        String zip_code = zipcode.getText().toString();
        String phone_number = phone.getText().toString();

        String monday_mode =mondayMode.getSelectedItem().toString().substring(0,1);
        String tuesday_mode = tuesdayMode.getSelectedItem().toString().substring(0,1);
        String wednesday_mode = wednesdayMode.getSelectedItem().toString().substring(0,1);
        String thursday_mode =  thursdayMode.getSelectedItem().toString().substring(0,1);
        String friday_mode =  fridayMode.getSelectedItem().toString().substring(0,1);
        String saturday_mode =  saturdaymode.getSelectedItem().toString().substring(0,1);
        String sunday_mode =  sundayMode.getSelectedItem().toString().substring(0,1);

        String monday_morning_from = "";
        if( m_mFrom.getText().length()>0)
        {
            monday_morning_from=m_mFrom.getText().toString();
        }
        String monday_morning_to = "";
        if( m_mTo.getText().length()>0)
        {
            monday_morning_to=m_mTo.getText().toString();
        }
        String monday_afternoon_from = "";
        if( m_aFrom.getText().length()>0)
        {
            monday_afternoon_from=m_aFrom.getText().toString();
        }
        String monday_afternoon_to = "";
        if( m_aTo.getText().length()>0)
        {
            monday_afternoon_to=m_aTo.getText().toString();
        }
        /*-------------------------------------------*/
        String tuesday_morning_from = "";
        if( t_mFrom.getText().length()>0)
        {
            tuesday_morning_from=t_mFrom.getText().toString();
        }
        String tuesday_morning_to = "";
        if( t_mTo.getText().length()>0)
        {
            tuesday_morning_to=t_mTo.getText().toString();
        }
        String tuesday_afternoon_from = "";
        if( t_aFrom.getText().length()>0)
        {
            tuesday_afternoon_from=t_aFrom.getText().toString();
        }
        String tuesday_afternoon_to = "";
        if( t_aTo.getText().length()>0)
        {
            tuesday_afternoon_to=t_aTo.getText().toString();
        }
          /*-------------------------------------------*/
        String wednesday_morning_from = "";
        if( w_mFrom.getText().length()>0)
        {
            wednesday_morning_from=w_mFrom.getText().toString();
        }
        String wednesday_morning_to = "";
        if( w_mTo.getText().length()>0)
        {
            wednesday_morning_to=w_mTo.getText().toString();
        }
        String wednesday_afternoon_from = "";
        if( w_aFrom.getText().length()>0)
        {
            wednesday_afternoon_from=w_aFrom.getText().toString();
        }
        String wednesday_afternoon_to = "";
        if( w_aTo.getText().length()>0)
        {
            wednesday_afternoon_to=w_aTo.getText().toString();
        }
          /*-------------------------------------------*/
        String thursday_morning_from = "";
        if( th_mFrom.getText().length()>0)
        {
            thursday_morning_from=m_mFrom.getText().toString();
        }
        String thursday_morning_to = "";
        if( th_mTo.getText().length()>0)
        {
            thursday_morning_to=m_mTo.getText().toString();
        }
        String thursday_afternoon_from = "";
        if( th_aFrom.getText().length()>0)
        {
            thursday_afternoon_from=m_aFrom.getText().toString();
        }
        String thursday_afternoon_to = "";
        if( th_aTo.getText().length()>0)
        {
            thursday_afternoon_to=m_aTo.getText().toString();
        }
          /*-------------------------------------------*/
        String friday_morning_from = "";
        if( f_mFrom.getText().length()>0)
        {
            friday_morning_from=f_mFrom.getText().toString();
        }
        String friday_morning_to = "";
        if( f_mTo.getText().length()>0)
        {
            friday_morning_to=f_mTo.getText().toString();
        }
        String friday_afternoon_from = "";
        if( f_aFrom.getText().length()>0)
        {
            friday_afternoon_from=f_aFrom.getText().toString();
        }
        String friday_afternoon_to = "";
        if( f_aTo.getText().length()>0)
        {
            friday_afternoon_to=f_aTo.getText().toString();
        }
          /*-------------------------------------------*/
        String saturday_morning_from = "";
        if( sat_mFrom.getText().length()>0)
        {
            saturday_morning_from=sat_mFrom.getText().toString();
        }
        String saturday_morning_to = "";
        if( sat_mTo.getText().length()>0)
        {
            saturday_morning_to =sat_mTo.getText().toString();
        }
        String saturday_afternoon_from = "";
        if( sat_aFrom.getText().length()>0)
        {
            saturday_afternoon_from=sat_aFrom.getText().toString();
        }
        String saturday_afternoon_to = "";
        if( sat_aTo.getText().length()>0)
        {
            saturday_afternoon_to=sat_aTo.getText().toString();
        }
          /*-------------------------------------------*/
        String sunday_morning_from = "";
        if( sun_mFrom.getText().length()>0)
        {
            sunday_morning_from=sun_mFrom.getText().toString();
        }
        String sunday_morning_to = "";
        if(sun_mTo.getText().length()>0)
        {
            sunday_morning_to=sun_mTo.getText().toString();
        }
        String sunday_afternoon_from = "";
        if( sun_aFrom.getText().length()>0)
        {
            sunday_afternoon_from=sun_aFrom.getText().toString();
        }
        String sunday_afternoon_to = "";
        if( sun_aTo.getText().length()>0)
        {
            sunday_afternoon_to =sun_aTo.getText().toString();
        }
        if (model == null) {
            return new String[]{locationname,
                    street_name,
                    door_no,
                    city_name,
                    zip_code,
                    phone_number,
                    monday_mode,
                    tuesday_mode,
                    wednesday_mode,
                    thursday_mode,
                    friday_mode,
                    saturday_mode,
                    sunday_mode,
                    monday_morning_from,
                    monday_morning_to,
                    monday_afternoon_from,
                    monday_afternoon_to,
                    tuesday_morning_from,
                    tuesday_morning_to,
                    tuesday_afternoon_from,
                    tuesday_afternoon_to,
                    wednesday_morning_from,
                    wednesday_morning_to,
                    wednesday_afternoon_from,
                    wednesday_afternoon_to,
                    thursday_morning_from,
                    thursday_morning_to,
                    thursday_afternoon_from,
                    thursday_afternoon_to,
                    friday_morning_from,
                    friday_morning_to,
                    friday_afternoon_from,
                    friday_afternoon_to,
                    saturday_morning_from,
                    saturday_morning_to,
                    saturday_afternoon_from,
                    saturday_afternoon_to,
                    sunday_morning_from,
                    sunday_morning_to,
                    sunday_afternoon_from,
                    sunday_afternoon_to
            };
        } else {
            return new String[]{model.getId(), locationname,
                    street_name,
                    door_no,
                    city_name,
                    zip_code,
                    phone_number,
                    monday_mode,
                    tuesday_mode,
                    wednesday_mode,
                    thursday_mode,
                    friday_mode,
                    saturday_mode,
                    sunday_mode,
                    monday_morning_from,
                    monday_morning_to,
                    monday_afternoon_from,
                    monday_afternoon_to,
                    tuesday_morning_from,
                    tuesday_morning_to,
                    tuesday_afternoon_from,
                    tuesday_afternoon_to,
                    wednesday_morning_from,
                    wednesday_morning_to,
                    wednesday_afternoon_from,
                    wednesday_afternoon_to,
                    thursday_morning_from,
                    thursday_morning_to,
                    thursday_afternoon_from,
                    thursday_afternoon_to,
                    friday_morning_from,
                    friday_morning_to,
                    friday_afternoon_from,
                    friday_afternoon_to,
                    saturday_morning_from,
                    saturday_morning_to,
                    saturday_afternoon_from,
                    saturday_afternoon_to,
                    sunday_morning_from,
                    sunday_morning_to,
                    sunday_afternoon_from,
                    sunday_afternoon_to
            };
        }
}

    @Override
    public void onSucess(final String value) {
runOnUiThread(new Runnable() {
    @Override
    public void run() {
        if(Utils.getStatus(value))
        {
            if(model==null)
            {
                Utils.showToast(Business_Location_Detais.this,"Business Location added sucessfully");
                Intent data = new Intent();
                setResult(RESULT_OK, data);
                finish();
            }else{
                Utils.showToast(Business_Location_Detais.this,"Business Location updated sucessfully");
                Intent data = new Intent();
                setResult(RESULT_OK, data);
                finish();
            }
        }else{
            Utils.showToast(Business_Location_Detais.this,Utils.getMessage(value));
        }
        dialog.cancel();
    }
});
    }

    @Override
    public void onError(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utils.showToast(Business_Location_Detais.this,Utils.getMessage(value));
                dialog.cancel();
            }
        });
    }

    @Override
    protected void onDestroy() {
        model=null;
        super.onDestroy();
    }
}
