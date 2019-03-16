package fragments;

import android.app.AlertDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.spaytbusiness.BusinessOffersDetails;
import com.spaytbusiness.BusinessProductDetails;
import com.spaytbusiness.Business_Location_Detais;
import com.spaytbusiness.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.BusinessOfferListAdapter;
import adapter.BusinessUserAdapter;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.BusinessOfferClicked;
import interfaces.WebApiResponseCallback;
import models.BusinessProductModel;
import models.Business_Offers;
import models.UserProfile;
import utils.Utils;

public class Offers   extends Fragment implements WebApiResponseCallback,View.OnClickListener, BusinessOfferClicked {
    AppController controller;

    TextView heading;
    TextView  count;
    ImageView add;
    ProgressBar progress_bar;
    ListView listView;
    TextView nodata;
    ArrayList<Business_Offers> businessOffersList=new ArrayList<>();
    BusinessOfferClicked  callback;
    int apiCall;
    int getApiCall=1,deleteApiCAll=2;
   Dialog progressDialog;
    WebApiResponseCallback webApiResponseCallback;
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

        callback=this;
        webApiResponseCallback=this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressDialog=Utils.getProgressDailog(getActivity());
        }

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
            apiCall=getApiCall;
            progress_bar.setVisibility(View.VISIBLE);
            controller.getWebApiCall().getDataCommon(Common.offersUrl,controller.getManager().getUserToken(),this);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusinessOffersDetails.model=null;
                getActivity().startActivityForResult(new Intent(getActivity(),BusinessOffersDetails.class),2);
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
        businessOffersList.clear();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (apiCall) {

                    case 1:
                    try {
                        JSONObject jsonObject = new JSONObject(value);
                        JSONArray userList = jsonObject.getJSONArray("businessproductsoffers_details");
                        if ((userList != null) && (userList.length() > 0)) {
                            for (int i = 0; i < userList.length(); i++) {
                                businessOffersList.add(new Business_Offers(userList.getJSONObject(i)));

                            }
                            count.setText(Integer.toString(businessOffersList.size()));
                            listView.setAdapter(new BusinessOfferListAdapter(businessOffersList, getActivity(), callback));
                            listView.setVisibility(View.VISIBLE);
                            nodata.setVisibility(View.GONE);
                        } else {

                            nodata.setVisibility(View.VISIBLE);
                            nodata.setText("No Offers");
                            count.setText("0");
                            listView.setVisibility(View.GONE);

                        }
                    } catch (Exception ex) {
                        ex.fillInStackTrace();
                    }
                    progress_bar.setVisibility(View.GONE);
                    break;
                    case 2:


                        if(Utils.getStatus(value)==true)
                        {
                            apiCall=getApiCall;
                            progress_bar.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                            controller.getWebApiCall().getDataCommon(Common.offersUrl,controller.getManager().getUserToken(),webApiResponseCallback);
                        }
                            Utils.showToast(getActivity(),value);
                        progressDialog.cancel();
                        break;
                }
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
    public void onOfferSelected(Business_Offers model) {
        BusinessOffersDetails.model=model;
        getActivity().startActivityForResult(new Intent(getActivity(),BusinessOffersDetails.class),2);

    }

    public void showAlert(final Business_Offers model)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Are you sure you want to delete this offers ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        apiCall=deleteApiCAll;
                        progressDialog.show();
                        controller.getWebApiCall().postData(Common.deleteBusinessOffers,controller.getManager().getUserToken(),Common.id,new String[]{model.getId()},webApiResponseCallback);

                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    @Override
    public void onDeleteCLicked(final Business_Offers model) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showAlert(model);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==2)&&(resultCode==-1))
        {
            apiCall=getApiCall;
            progress_bar.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            controller.getWebApiCall().getDataCommon(Common.offersUrl,controller.getManager().getUserToken(),webApiResponseCallback);

        }
    }
}