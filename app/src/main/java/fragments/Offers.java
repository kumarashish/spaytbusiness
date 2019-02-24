package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.spaytbusiness.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.BusinessUserAdapter;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import models.UserProfile;
import utils.Utils;

public class Offers   extends Fragment implements WebApiResponseCallback,View.OnClickListener {
    AppController controller;

    TextView heading;
    TextView  count;
    ImageView add;
    ProgressBar progress_bar;
    ListView listView;
    TextView nodata;
    ArrayList<UserProfile> businessUserList=new ArrayList<>();

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
        heading.setText("Offers");
        count.setText("");
        ButterKnife.bind(getActivity());
        if(Utils.isNetworkAvailable(getActivity()))
        {
            progress_bar.setVisibility(View.VISIBLE);
            controller.getWebApiCall().getDataCommon(Common.offersUrl,controller.getManager().getUserToken(),this);
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
                    JSONArray userList=jsonObject.getJSONArray("businessproductsoffers_details");
                    if((userList!=null)&&(userList.length()>0))
                    {
                        for(int i=0;i<userList.length();i++)
                        {

                        }



                        count.setText(Integer.toString(businessUserList.size()));
                        listView.setAdapter(new BusinessUserAdapter(businessUserList,getActivity()));
                        listView.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                    }



                    else{

                        nodata.setVisibility(View.VISIBLE);
                        nodata.setText("No Offers");
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
    }