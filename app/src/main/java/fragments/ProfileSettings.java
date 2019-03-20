package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;

import com.spaytbusiness.R;

import org.json.JSONObject;

import butterknife.BindView;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import models.BusinessProfile;
import models.UserProfile;
import utils.Utils;

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
    int getData=1,updateData=2;
    public static UserProfile model=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.profile_setting, container, false);
        controller=(AppController)getActivity().getApplicationContext();
        salutation=(Spinner)v.findViewById(R.id.salutation);
        mainLayout=(ScrollView)v.findViewById(R.id.mainLayout);
         fname=(EditText)v.findViewById(R.id.firstName);
         lname=(EditText)v.findViewById(R.id.lastName);
         email=(EditText)v.findViewById(R.id.email);
         progress_bar=(ProgressBar)v.findViewById(R.id.progress_bar);
        progress_bar2=(ProgressBar)v.findViewById(R.id.progress_bar2);
        role=(Spinner)v.findViewById(R.id.role);

        submit=(Button)v.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if(Utils.isNetworkAvailable(getActivity()))
        {
            progress_bar.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.GONE);
            apiCall=getData;
            controller.getWebApiCall().getDataCommon(Common.myDetails,controller.getManager().getUserToken(),this);

        }
        return v;
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
                        Utils.showToast(getActivity(),Utils.getMessage(value));
                        progress_bar2.setVisibility(View.GONE);
                        submit.setVisibility(View.VISIBLE);
                        break;
                }


            }});}

    @Override
    public void onError(String value) {
        Utils.showToast(getActivity(),Utils.getMessage(value));

    }
}