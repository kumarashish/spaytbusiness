package fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.spaytbusiness.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import models.BusinessProfile;
import models.CategoryModel;
import utils.Utils;

public class Business extends Fragment implements WebApiResponseCallback ,View.OnClickListener {
    ProgressBar progress_bar;
    LinearLayout progress_bar2;
    AppController controller;
    BusinessProfile profile;
    LinearLayout mainLayout;
    EditText companyName;
    Spinner categories;
    EditText phone;
    EditText companyEmailId;
    EditText vatId;
    EditText paypalId;
    EditText doorNumber;
    EditText street;
    EditText city;
    EditText pincode;
    ArrayList<CategoryModel> categorylist=new ArrayList<>();
    ArrayList<String>categorylistName=new ArrayList<>();
    Button submit;
    int apiCall;
    int getData=1,updateProile=2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller=(AppController)getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.profile_settings, container, false);
        progress_bar=(ProgressBar)v.findViewById(R.id.progress_bar);
        progress_bar2=(LinearLayout)v.findViewById(R.id.progress_bar2);
        mainLayout=(LinearLayout)v.findViewById(R.id.mainView);
        companyName=(EditText)v.findViewById(R.id.companyName);
        street=(EditText)v.findViewById(R.id.street);
        categories=(Spinner) v.findViewById(R.id.categories);
        phone=(EditText)v.findViewById(R.id.phone);
        companyEmailId=(EditText)v.findViewById(R.id.companyEmailId);
        vatId=(EditText)v.findViewById(R.id.vatId);
        paypalId=(EditText)v.findViewById(R.id.paypalId);
        doorNumber=(EditText)v.findViewById(R.id.doorNumber);
        city=(EditText)v.findViewById(R.id.city);
        pincode=(EditText)v.findViewById(R.id.pincode);
        submit=(Button)v.findViewById(R.id.submit);
        submit.setOnClickListener(this);
        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView child=(TextView) parent.getChildAt(0);
                //child.setTextColor(getActivity().getColor(R.color.grey));
                child.setTextSize(18);
                //child.setTypeface(getResources().getFont(R.font.light));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(Utils.isNetworkAvailable(getActivity()))
        { runThread();
            progress_bar.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.GONE);
            apiCall=getData;
            controller.getWebApiCall().getDataCommon(Common.businessProfile,controller.getManager().getUserToken(),this);

        }
        return v;
    }

    public void runThread() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = controller.getWebApiCall().getData(Common.getCategories);
                if (result != null) {
                    if (Utils.getStatus(result) == true) {
                        JSONArray jsonArray = Utils.getJSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                CategoryModel model = new CategoryModel(jsonObject);
                                categorylist.add(model);
                                categorylistName.add(model.getCategoryName());
                            } catch (Exception ex) {
                                ex.fillInStackTrace();
                            }

                        }
                        if (categorylistName.size() > 0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    categories.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categorylistName));

                                }
                            });
                        }
                    }
                }
            }
        });
        t.start();
    }

    @Override
    public void onSucess(final  String value) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (apiCall) {
                    case 1:
                    try {
                        JSONObject jsonObject = new JSONObject(value);
                        JSONObject business_details = jsonObject.getJSONObject("business_details");

                        profile = new BusinessProfile(business_details);
                        setData();

                    } catch (Exception ex) {
                        ex.fillInStackTrace();
                    }

                    progress_bar.setVisibility(View.GONE);
                        break;
                    case 2:
                        Utils.showToast(getActivity(),Utils.getMessage(value));
                        progress_bar2.setVisibility(View.GONE);
                        submit.setVisibility(View.VISIBLE);
                        break;
                }


            }

        });}

    @Override
    public void onError(String value) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress_bar.setVisibility(View.GONE);
                if(progress_bar2.getVisibility()==View.VISIBLE)
                {
                    progress_bar2.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                }
            }
        });
        Utils.showToast(getActivity(),Utils.getMessage(value));
    }

    public void setData()
    {
        progress_bar.setVisibility(View.GONE);
        mainLayout.setVisibility(View.VISIBLE);
        companyName.setText(profile.getCompany_name());
        categories.setSelection(categorylistName.indexOf(profile.getCategory_name().trim()));
        phone.setText(profile.getPhone_number());
        companyEmailId.setText(profile.getBusiness_email());
        vatId.setText(profile.getVat_id());
        paypalId.setText(profile.getPaypal_email());
        street.setText(profile.getStreet_name());
        doorNumber.setText(profile.getDoor_no());
        city.setText(profile.getCity());
        pincode.setText(profile.getZip_code());

    }

    public boolean isAllFildsValidated() {
        if ((companyName.getText().length() > 0) && (street.getText().length() > 0) && (doorNumber.getText().length() > 0) && (city.getText().length() > 0) && (pincode.getText().length() > 0) && (phone.getText().length() > 0) && (companyEmailId.getText().length() > 0)) {
            return true;
        } else {
            if (companyName.getText().length() == 0) {
                Toast.makeText(getActivity(),"Please enter company name",Toast.LENGTH_SHORT).show();
            } else if (street.getText().length() == 0) {
                Toast.makeText(getActivity(),"Please enter street name",Toast.LENGTH_SHORT).show();
            } else if (doorNumber.getText().length() == 0) {
                Toast.makeText(getActivity(),"Please enter door number",Toast.LENGTH_SHORT).show();
            } else if (city.getText().length() == 0) {
                Toast.makeText(getActivity(),"Please enter city",Toast.LENGTH_SHORT).show();
            } else if (pincode.getText().length() == 0) {
                Toast.makeText(getActivity(),"Please enter zipcode",Toast.LENGTH_SHORT).show();
            } else if (phone.getText().length() == 0) {
                Toast.makeText(getActivity(),"Please enter phone number",Toast.LENGTH_SHORT).show();
            } else if (companyEmailId.getText().length() == 0) {
                Toast.makeText(getActivity(),"Please enter Company Emnail Id",Toast.LENGTH_SHORT).show();
            }

        }
        return false;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.submit:
                if(isAllFildsValidated()) {
                    apiCall = updateProile;
                    submit.setVisibility(View.GONE);
                    progress_bar2.setVisibility(View.VISIBLE);
                    controller.getWebApiCall().postData(Common.updateBusinessProfile, controller.getManager().getUserToken(), Common.updateBusinessKeys, getData(), Business.this);
                }
                break;
        }
    }

    public String[] getData() {
        String paypalIdValue = "";
        if (paypalId.getText().length() > 0) {
            paypalIdValue = paypalId.getText().toString();
        }
        String VATId = "";
        if (vatId.getText().length() > 0) {
            VATId = vatId.getText().toString();
        }
        return new String[]{companyEmailId.getText().toString(), categorylist.get(categories.getSelectedItemPosition()).getCategoryId(), companyName.getText().toString(), street.getText().toString(), doorNumber.getText().toString(), city.getText().toString(), pincode.getText().toString(), phone.getText().toString(), paypalIdValue, VATId};
    }
}