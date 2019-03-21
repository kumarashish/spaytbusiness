package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.spaytbusiness.BusinessProductDetails;
import com.spaytbusiness.BusinessUserDetails;
import com.spaytbusiness.Business_Location_Detais;
import com.spaytbusiness.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.BusinessUserAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.BusinessUserClicked;
import interfaces.WebApiResponseCallback;
import models.BusinessProfile;
import models.UserProfile;
import utils.Utils;

/**
 * Created by ashish.kumar on 19-02-2019.
 */

public class Users extends Fragment implements WebApiResponseCallback,View.OnClickListener, BusinessUserClicked {
    AppController controller;

   TextView heading;
    TextView  count;
    ImageView add;
    ProgressBar progress_bar;
    ListView listView;
    TextView nodata;
    ArrayList<UserProfile> businessUserList=new ArrayList<>();
    BusinessUserClicked callback;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller=(AppController)getActivity().getApplicationContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        callback=this;
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.offers, container, false);
       heading=(TextView)v.findViewById(R.id.name);
        count=(TextView)v.findViewById(R.id.count);
        add=(ImageView) v.findViewById(R.id.add);
        progress_bar=(ProgressBar)v.findViewById(R.id.progress_bar);
        listView=(ListView)v.findViewById(R.id.listView);
        nodata=(TextView)v.findViewById(R.id.nodata);
        heading.setText("Business Users");
        count.setText("");
        ButterKnife.bind(getActivity());
        if(Utils.isNetworkAvailable(getActivity()))
        {
            progress_bar.setVisibility(View.VISIBLE);
            controller.getWebApiCall().getDataCommon(Common.businessUserUrl,controller.getManager().getUserToken(),this);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(controller.getProfile().getRole().equalsIgnoreCase("Admin")) {
                    BusinessUserDetails.model = null;
                    getActivity().startActivityForResult(new Intent(getActivity(), BusinessUserDetails.class), 2);
                }else{
                    Utils.showToast(getActivity(),"You are not authorised to add user");
                }
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }




    @Override
    public void onSucess(final String value) {
        businessUserList.clear();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (Utils.getStatus(value)) {

                            JSONObject jsonObject = new JSONObject(value);
                            JSONArray userList = jsonObject.getJSONArray("businessusers_details");
                            if ((userList != null) && (userList.length() > 0)) {
                                for (int i = 0; i < userList.length(); i++) {
                                    businessUserList.add(new UserProfile(userList.getJSONObject(i)));
                                }


                                heading.setText("Business Users");
                                count.setText(Integer.toString(businessUserList.size()));
                                listView.setAdapter(new BusinessUserAdapter(businessUserList, getActivity(),callback));
                                listView.setVisibility(View.VISIBLE);
                                nodata.setVisibility(View.GONE);
                            } else {

                                nodata.setVisibility(View.VISIBLE);
                                nodata.setText("No User Registered Yet");
                                count.setText("0");
                                listView.setVisibility(View.GONE);

                            }
                        }else{
                            nodata.setVisibility(View.VISIBLE);
                            nodata.setText("No User Registered Yet");
                            count.setText("0");
                            listView.setVisibility(View.GONE);
                        }
                        } catch(Exception ex){
                            ex.fillInStackTrace();
                        }

                    progress_bar.setVisibility(View.GONE);
                }
            });


    }

    @Override
    public void onError(String value) {
        if(value.length()>0)
        {

        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onUserSelected(UserProfile model) {
        BusinessUserDetails.model=model;
        getActivity().startActivityForResult(new Intent(getActivity(),BusinessUserDetails.class),2);

    }

    @Override
    public void onDeleteCLicked(UserProfile model) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==2)&&(resultCode==-1))
        {
            progress_bar.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            controller.getWebApiCall().getDataCommon(Common.businessUserUrl,controller.getManager().getUserToken(),this);

        }
    }
}
