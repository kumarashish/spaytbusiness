package fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.spaytbusiness.BusinessProductDetails;
import com.spaytbusiness.BusinessUserDetails;
import com.spaytbusiness.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import models.BusinessProfile;
import models.Business_locations;
import models.UserProfile;
import utils.Utils;
import utils.Validation;

public class ProfileSettings extends Fragment implements WebApiResponseCallback {
    AppController controller;

    Spinner salutation;

    EditText fname;

    EditText lname;

    EditText email;

    Switch isactive;
    ScrollView mainLayout;

    Spinner role;

   ProgressBar progress_bar,progress_bar2;
    Button submit;
    int apiCall;
    int getData=1,updateData=2,getBusinessList=3;
    public static UserProfile model=null;
    Validation validation;
    ArrayList<Business_locations> businessLocationList=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.profile_setting, container, false);
        validation=new Validation(getActivity());
        controller=(AppController)getActivity().getApplicationContext();
        salutation=(Spinner)v.findViewById(R.id.salutation);
        mainLayout=(ScrollView)v.findViewById(R.id.mainLayout);
         fname=(EditText)v.findViewById(R.id.firstName);
         lname=(EditText)v.findViewById(R.id.lastName);
         email=(EditText)v.findViewById(R.id.email);
         progress_bar=(ProgressBar)v.findViewById(R.id.progress_bar);
        progress_bar2=(ProgressBar)v.findViewById(R.id.progress_bar2);
        role=(Spinner)v.findViewById(R.id.role);
        salutation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView child=(TextView) parent.getChildAt(0);
                child.setTextSize(18);
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    child.setTextColor(getResources().getColor(R.color.blue,getActivity().getTheme()));
                } else{
                    child.setTextColor(getResources().getColor(R.color.blue));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView child=(TextView) parent.getChildAt(0);
                child.setTextSize(18);
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    child.setTextColor(getResources().getColor(R.color.blue,getActivity().getTheme()));
                } else{
                    child.setTextColor(getResources().getColor(R.color.blue));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        submit=(Button)v.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAllFildsValidated()) {

                    submit.setVisibility(View.GONE);
                    progress_bar2.setVisibility(View.VISIBLE);
                        apiCall =updateData;
                        controller.getWebApiCall().postData(Common.updateMyProfile, controller.getManager().getUserToken(), Common.updateMeKeys, getData(), ProfileSettings.this);

                }
            }
        });
        if(Utils.isNetworkAvailable(getActivity()))
        {
            progress_bar.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.GONE);
            apiCall=getData;
            controller.getWebApiCall().getDataCommon(Common.myDetails,controller.getManager().getUserToken(),this);
            getLocations();

        }
        return v;
    }


    public void getLocations(){
        if(Utils.isNetworkAvailable(getActivity()))
        {
            apiCall=getBusinessList;
            controller.getWebApiCall().getDataCommon(Common.businessLocationUrl,controller.getManager().getUserToken(),this);
        }
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
public void setData()
{
    fname.setText(model.getFirst_name());
    lname.setText(model.getLast_name());
    email.setText(model.getEmail());
    salutation.setSelection(getIndex(model.getSalutation()));
    role.setSelection(getIndexRole(model.getRole()));
    progress_bar.setVisibility(View.GONE);
    mainLayout.setVisibility(View.VISIBLE);

}
    @Override
    public void onSucess(final String value) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (apiCall) {
                    case 1:
                        try {
                            JSONObject jsonObject = new JSONObject(value);
                            JSONObject user_details = jsonObject.getJSONObject("my_details");
                            model = new UserProfile(user_details);
                            setData();

                        } catch (Exception ex) {
                            ex.fillInStackTrace();
                        }

                        progress_bar.setVisibility(View.GONE);
                        break;
                    case 2:
                        if(Utils.getStatus(value)==true)
                        {
                            Utils.showToast(getActivity(),"Profile Udpated Sucessfully.");
                        }else {
                            Utils.showToast(getActivity(), Utils.getMessage(value));
                        }
                        progress_bar2.setVisibility(View.GONE);
                        submit.setVisibility(View.VISIBLE);
                        break;
                    case 3:
try{
                        JSONObject jsonObject = new JSONObject(value);
                        JSONArray businesslocations_details = jsonObject.getJSONArray("businesslocations_details");
                        if ((businesslocations_details != null) && (businesslocations_details.length() > 0)) {
                            for (int i = 0; i < businesslocations_details.length(); i++) {
                                businessLocationList.add(new Business_locations(businesslocations_details.getJSONObject(i)));

                            }


                        }
                } catch (Exception ex) {
                    ex.fillInStackTrace();
                }
                        break;
                }


            }});}

    @Override
    public void onError(String value) {
        Utils.showToast(getActivity(),Utils.getMessage(value));

    }
    public String[] getData() {


            return new String[]{salutation.getSelectedItem().toString(),fname.getText().toString(), lname.getText().toString(), email.getText().toString()};

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
                Toast.makeText(getActivity(),"Please enter first name",Toast.LENGTH_SHORT).show();
            }else  if(lname.getText().length()==0)
            {
                Toast.makeText(getActivity(),"Please enter last name",Toast.LENGTH_SHORT).show();
            }else  if(email.getText().length()==0)
            {
                Toast.makeText(getActivity(),"Please enter Email Id",Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }
}