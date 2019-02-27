package fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.spaytbusiness.Add_Products;
import com.spaytbusiness.BusinessProductDetails;
import com.spaytbusiness.BusinessUserDetails;
import com.spaytbusiness.Business_Location_Detais;
import com.spaytbusiness.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.BusinessProductAdapter;
import adapter.BusinessUserAdapter;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.BusinessProductClicked;
import interfaces.WebApiResponseCallback;
import models.BusinessProductModel;
import models.Business_locations;
import models.UserProfile;
import utils.Utils;

public class Products extends Fragment implements WebApiResponseCallback,View.OnClickListener, BusinessProductClicked {
    AppController controller;

    TextView heading;
    TextView  count;
    ImageView add;
    ProgressBar progress_bar;
    ListView listView;
    TextView nodata;
    ArrayList<BusinessProductModel> businessProductsList=new ArrayList<>();
    BusinessProductClicked callback;
    int apiCall;
    int getApiCall=1,deleteApiCAll=2;
    ProgressDialog progressDialog;
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
         callback=this;
        webApiResponseCallback=this;
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        View v= inflater.inflate(R.layout.offers, container, false);
        heading=(TextView)v.findViewById(R.id.name);
        count=(TextView)v.findViewById(R.id.count);
        add=(ImageView) v.findViewById(R.id.add);
        progress_bar=(ProgressBar) v.findViewById(R.id.progress_bar);
        listView=(ListView)v.findViewById(R.id.listView);
        nodata=(TextView)v.findViewById(R.id.nodata);
        heading.setText("Products");
        count.setText("");
        ButterKnife.bind(getActivity());
        if(Utils.isNetworkAvailable(getActivity()))
        {
            progress_bar.setVisibility(View.VISIBLE);
            apiCall=getApiCall;
            controller.getWebApiCall().getDataCommon(Common.businessProducts,controller.getManager().getUserToken(),this);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Business_Location_Detais.model=null;
                getActivity().startActivityForResult(new Intent(getActivity(),BusinessProductDetails.class),2);
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
        businessProductsList.clear();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (apiCall) {
                    case 1:
                    try {
                        JSONObject jsonObject = new JSONObject(value);
                        JSONArray productList = jsonObject.getJSONArray("businessproducts_details");
                        if ((productList != null) && (productList.length() > 0)) {
                            for (int i = 0; i < productList.length(); i++) {
                                businessProductsList.add(new BusinessProductModel(productList.getJSONObject(i)));

                            }

                            count.setText(Integer.toString(businessProductsList.size()));
                            listView.setAdapter(new BusinessProductAdapter(businessProductsList, getActivity(), callback));
                            listView.setVisibility(View.VISIBLE);
                            nodata.setVisibility(View.GONE);
                        } else {

                            nodata.setVisibility(View.VISIBLE);
                            nodata.setText("No Products Added");
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
                            controller.getWebApiCall().getDataCommon(Common.businessProducts,controller.getManager().getUserToken(),webApiResponseCallback);
                        }
                        Utils.showToast(getActivity(),Utils.getMessage(value));
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
    public void onProductSelected(BusinessProductModel model) {
        BusinessProductDetails.model=model;
        getActivity().startActivityForResult(new Intent(getActivity(),BusinessProductDetails.class),2);
    }
    public void showAlert(final BusinessProductModel model)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Are you sure you want to delete this product ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        apiCall=deleteApiCAll;
                        progressDialog.show();
                        controller.getWebApiCall().postData(Common.deleteBusinessProduct,controller.getManager().getUserToken(),Common.id,new String[]{model.getId()},webApiResponseCallback);

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
    public void onDeleteCLicked(final BusinessProductModel model) {
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
            controller.getWebApiCall().getDataCommon(Common.businessProducts,controller.getManager().getUserToken(),webApiResponseCallback);

        }
    }
}