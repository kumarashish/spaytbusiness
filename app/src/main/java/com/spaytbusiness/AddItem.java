package com.spaytbusiness;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.CircularProgressDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.BusinessLocationAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import models.BusinessProductModel;
import models.Business_locations;
import utils.Utils;

public class AddItem extends Activity implements View.OnClickListener , WebApiResponseCallback {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.mycartView)
    View myCart;
    @BindView(R.id.customer_name)
    TextView customer_name;
    //String customerCode="";
    AppController controller;
    @BindView(R.id.content)
    RelativeLayout content;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;
    @BindView(R.id.progressbar)
    ProgressBar progress;
    @BindView(R.id.progressbar2)
    ProgressBar progress2;
    @BindView(R.id.locations)
    Spinner locations;
    @BindView(R.id.product)
    Spinner product;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.price)
    EditText price;
    @BindView(R.id.quantity)
    EditText quantity;
String customerId="0";
String customerName="";

@BindView(R.id.submit)
Button submit;

    ArrayList<Business_locations> businessLocationList=new ArrayList<>();
    ArrayList<String>businessLocationNames=new ArrayList<>();
    ArrayList<String>productNames=new ArrayList<>();
    ArrayList<BusinessProductModel> businessproductList=new ArrayList<>();
    ArrayList<BusinessProductModel> offerproductList=new ArrayList<>();

@BindView(R.id.myCartCount)
        TextView myCartItemCount;
    int apiCall;
    int getLocation=1,getProducts=2,getCustomerFromCode=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        ButterKnife.bind(this);
        back.setOnClickListener(this);
        myCart.setOnClickListener(this);
        submit.setOnClickListener(this);
        controller=(AppController)getApplicationContext();
        getProductsList();

        product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) {
                    price.setText(businessproductList.get(position - 1).getTotal_price());
                    quantity.setText("1");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
if((price.getText().length()>0)&&(s.length()>0))
{  double val=Double.parseDouble(price.getText().toString())*Integer.parseInt(quantity.getText().toString());
    total.setText(Double.toString(val)+" £");
}
            }
        });
        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if((quantity.getText().length()>0)&&(s.length()>0))
                {  double val=Double.parseDouble(price.getText().toString())*Integer.parseInt(quantity.getText().toString());
                    total.setText(Double.toString(val)+" £");
                }
            }
        });
    }
public void getProductsList()
{
    if(Utils.isNetworkAvailable(AddItem.this))
    {apiCall=getProducts;
        progress.setVisibility(View.VISIBLE);
        controller.getWebApiCall().getDataCommon(Common.getBusinessProductsOffers,controller.getManager().getUserToken(),this);
    }
}
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                if((quantity.getText().length()>0)&&(price.getText().length()>0))
            {BusinessProductModel model=businessproductList.get(product.getSelectedItemPosition()-1);

                controller.addProduct(new BusinessProductModel("product",model.getId(),model.getName(),model.getDescription(),quantity.getText().toString(),price.getText().toString()));
                int count=controller.getQuantity();
                if(count>0)
                {myCartItemCount.setText(Integer.toString(count));
                myCartItemCount.setVisibility(View.VISIBLE);
                }
                Utils.showToast(AddItem.this,"Product added to cart sucessfully");
                price.setText("");
                product.setSelection(0);
                quantity.setText("");
                total.setText("");

            }else{
                    if(quantity.getText().length()==0)
                    {
                        Utils.showToast(AddItem.this,"Please enter quantity");
                    }else{
                        Utils.showToast(AddItem.this,"Please enter product price");
                    }
                }
                break;
            case R.id.mycartView:
                if(controller.getGetMyCart().size()>0) {
                    int pos=locations.getSelectedItemPosition()-1;
                    Intent in=new Intent(this, MyCart.class);
                    in.putExtra("locationId",businessLocationList.get(pos).getId());
                    in.putExtra("customerName",customerName);
                    in.putExtra("customerId",customerId);

                    startActivity(in);
                }else{
                    Utils.showToast(AddItem.this,"You cart is empty!Please add product in cart");
                }
                break;

        }
    }

    @Override
    public void onSucess(final String value) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (apiCall) {
                    case 1:
                        businessLocationList.clear();
                        try {
                            businessLocationNames.add("Select location");
                            JSONObject jsonObject = new JSONObject(value);
                            JSONArray businesslocations_details = jsonObject.getJSONArray("businesslocations_details");
                            if ((businesslocations_details != null) && (businesslocations_details.length() > 0)) {
                                for (int i = 0; i < businesslocations_details.length(); i++) {
                                    Business_locations model=new Business_locations(businesslocations_details.getJSONObject(i));
                                    businessLocationList.add(model);
                                    businessLocationNames.add(model.getLocation_name());

                                }
                                if(businessLocationNames.size()>0)
                                {
                                    locations.setAdapter(new ArrayAdapter<String>(AddItem.this,android.R.layout.simple_list_item_1,businessLocationNames));
                                    mainLayout.setVisibility(View.VISIBLE);
                                }

                            } else {



                            }
                        } catch (Exception ex) {
                            ex.fillInStackTrace();
                        }
                        progress.setVisibility(View.GONE);

                        break;
                    case 2:
                        if(Utils.getStatus(value))
                        {
                            businessproductList.clear();
                            offerproductList.clear();
                            productNames.clear();
                        try {
                            productNames.add("Select");
                            JSONObject jsonObject=new JSONObject(value);
                            JSONArray locationproducts=jsonObject.getJSONArray("locationproducts");
                            JSONArray offerproducts=jsonObject.getJSONArray("locationproductoffers");
                            if ((locationproducts.length() > 0) || (offerproducts.length() > 0)) {
                                  for(int i=0;i<locationproducts.length();i++)
                                  {BusinessProductModel model=new BusinessProductModel(locationproducts.getJSONObject(i));
                                      businessproductList.add(model);
                                      productNames.add(model.getName());
                                  }
                                for(int i=0;i<offerproducts.length();i++)
                                {
                                    offerproductList.add(new BusinessProductModel(offerproducts.getJSONObject(i)));
                                }
                                if(productNames.size()>0)
                                {
                                    product.setAdapter(new ArrayAdapter<String>(AddItem.this,android.R.layout.simple_list_item_1,productNames));
                                }
                                content.setVisibility(View.VISIBLE);
                                  progress2.setVisibility(View.GONE);
                            } else {
                                Utils.showToast(AddItem.this, "No products available for selected location");
                            }

                        }catch (Exception ex) {
                            ex.fillInStackTrace();
                        }}else {
                            Utils.showToast(AddItem.this,Utils.getMessage(value));
                        }
                          progress2.setVisibility(View.GONE);
                            break;


    }}});
        }


        public void getProducts()
        {
            if(Utils.isNetworkAvailable(AddItem.this))
            {apiCall=getProducts;
                progress2.setVisibility(View.VISIBLE);
                int pos=locations.getSelectedItemPosition()-1;
              //  controller.getWebApiCall().postData(Common.getBusinessProductsOffers,controller.getManager().getUserToken(),Common.locationIdKey,new String[]{businessLocationList.get(pos).getId()},this);
            }
        }

    @Override
    public void onError(String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });

    }
}