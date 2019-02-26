package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.spaytbusiness.Add_Location;
import com.spaytbusiness.Business_Location_Detais;
import com.spaytbusiness.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.BusinessLocationAdapter;
import adapter.BusinessUserAdapter;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.OnListItemSelected;
import interfaces.WebApiResponseCallback;
import models.Business_locations;
import models.UserProfile;
import utils.Utils;

public class Locations extends Fragment implements WebApiResponseCallback,View.OnClickListener, OnListItemSelected {
    AppController controller;

    TextView heading;
    TextView  count;
    ImageView add;
    ProgressBar progress_bar;
    ListView listView;
    TextView nodata;
    ArrayList<Business_locations> businessLocationList=new ArrayList<>();
OnListItemSelected callback;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller=(AppController)getActivity().getApplicationContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.offers, container, false);
        heading=(TextView)v.findViewById(R.id.name);
        count=(TextView)v.findViewById(R.id.count);
        add=(ImageView) v.findViewById(R.id.add);
        progress_bar=(ProgressBar) v.findViewById(R.id.progress_bar);
        listView=(ListView)v.findViewById(R.id.listView);
        nodata=(TextView)v.findViewById(R.id.nodata);
        heading.setText("Business Locations");
        count.setText("");
        callback=this;
        ButterKnife.bind(getActivity());
        if(Utils.isNetworkAvailable(getActivity()))
        {
            progress_bar.setVisibility(View.VISIBLE);
            controller.getWebApiCall().getDataCommon(Common.businessLocationUrl,controller.getManager().getUserToken(),this);
        }
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }




    @Override
    public void onSucess(final String value) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    JSONObject jsonObject=new JSONObject(value);
                    JSONArray businesslocations_details=jsonObject.getJSONArray("businesslocations_details");
                    if((businesslocations_details!=null)&&(businesslocations_details.length()>0))
                    {
                        for(int i=0;i<businesslocations_details.length();i++)
                        {
                            businessLocationList.add(new Business_locations(businesslocations_details.getJSONObject(i)));

                        }



                        count.setText(Integer.toString(businessLocationList.size()));
                        listView.setAdapter(new BusinessLocationAdapter(businessLocationList,getActivity(),callback));
                        listView.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                    }

                    else{

                        nodata.setVisibility(View.VISIBLE);
                        nodata.setText("No Locations added");
                        count.setText("0");
                        listView.setVisibility(View.GONE);

                    }
                }catch (Exception ex)
                {
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
    public void onBusinessLocationSlected(Business_locations model) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getActivity(),Business_Location_Detais.class));
            }
        });
    }
}