package com.spaytbusiness;

import android.app.Activity;
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
import interfaces.WebApiResponseCallback;
import models.Business_locations;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_location_details);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
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

    @Override
    public void onSucess(String value) {

    }

    @Override
    public void onError(String value) {

    }
}
