package com.spaytbusiness;

import android.app.Activity;
import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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
import interfaces.BusinessProductClicked;
import interfaces.BusinessUserClicked;
import interfaces.OnListItemSelected;
import interfaces.WebApiResponseCallback;
import models.BusinessProductModel;
import models.BusinessProfile;
import models.Business_locations;
import models.CategoryModel;
import utils.Utils;

/**
 * Created by ashish.kumar on 26-02-2019.
 */

public class BusinessProductDetails extends Activity implements View.OnClickListener, WebApiResponseCallback, OnListItemSelected {
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.heading)
    TextView heading;
    @BindView(R.id.category)
    Spinner category;
            @BindView(R.id.productname)
    EditText productName;
            @BindView(R.id.description)
            EditText description;
            @BindView(R.id.total_price)
            EditText total_price;
            @BindView(R.id.per_liter_price)
            EditText per_liter_price;
            @BindView(R.id.parking_fee_per_hou)
            EditText parking_fee_per_hou;
            @BindView(R.id.minimum_parking_hours)
            EditText minimum_parking_hours;
            @BindView(R.id.maximum_parking_fee_perday)
            EditText maximum_parking_fee_perday;
            @BindView(R.id.add_location)
            ImageView addLocation;
            @BindView(R.id.submit)
            Button submit;
            @BindView(R.id.progress_bar)
           ProgressBar progressBar;
           @BindView(R.id.progress_bar2)
           ProgressBar progressBar2;
          @BindView(R.id.total_price_view)
           LinearLayout total_price_view;
            @BindView(R.id.per_liter_price_view)
            LinearLayout per_liter_price_view;
            @BindView(R.id.per_hour_price_view)
            LinearLayout per_hour_price_view;
            @BindView(R.id.minimum_parking_hours_view)
            LinearLayout minimum_parking_hours_view;
            @BindView(R.id.maximum_parking_fee_perday_view)
            LinearLayout maximum_parking_fee_perday_view;
            @BindView(R.id.mainLayout)
    ScrollView mainView;
            @BindView(R.id.view1)
            View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.view5)
    View view5;
    @BindView(R.id.locationList)
    LinearLayout locationList;
    public static BusinessProductModel model=null;
    AppController controller;
    ArrayList<CategoryModel> categorylist=new ArrayList<>();
    ArrayList<String>categorylistName=new ArrayList<>();
    ArrayList<Business_locations>addedView=new ArrayList<>();
    ArrayList<Business_locations> businessLocationList=new ArrayList<>();
     OnListItemSelected callback;
      Dialog dialog;
      int apiCall;
      int getBusinessList=1,updateProduct=3,addPRoduct=2;
      int selectedPos=-1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_product_details);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        addLocation.setOnClickListener(this);
        controller=(AppController)getApplicationContext();

        callback=this;
        setData();

    }

public void addView()
{
    locationList.removeAllViews();
    for (int i = 0; i < addedView.size(); i++) {
        Business_locations modelItems = addedView.get(i);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.business_user_row, null);
        TextView address = (TextView) view.findViewById(R.id.email);
        TextView locationname = (TextView) view.findViewById(R.id.name);
        TextView description = (TextView) view.findViewById(R.id.role);
        ImageView delete = (ImageView) view.findViewById(R.id.delete);
        locationname.setText(modelItems.getLocation_name());
        address.setText(modelItems.getStreet_name() + "," + modelItems.getCity() + "," + modelItems.getDoor_no());
        description.setText(modelItems.getDescription());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Business_locations modell=addedView.get(v.getId());
if(modell!=null) {
    removeItemFromList(modell);
}


            }
        });
        delete.setId(i);

        locationList.addView(view);
    }
}
    public void setData()

    {
        if (model == null) {
            heading.setText("Add Product");
        } else {
            //category
            productName.setText(model.getName());
            description.setText(model.getDescription());

            total_price.setText(model.getTotal_price());
            per_liter_price.setText(model.getPrice_per_liter() );
            parking_fee_per_hou.setText(model.getParking_fee_per_hour() );
            minimum_parking_hours.setText(model.getMinimum_parking_hours() );
            maximum_parking_fee_perday.setText(model.getMaximum_parking_fee_perday());
            if (model.getTotal_price().length() == 0) {
                total_price_view.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
            }
            if (model.getPrice_per_liter().length() == 0) {
                per_liter_price_view.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
            }
            if (model.getParking_fee_per_hour().length() == 0) {
                per_hour_price_view.setVisibility(View.GONE);
                view3.setVisibility(View.GONE);
            }
            if (model.getMinimum_parking_hours().length() == 0) {
                minimum_parking_hours_view.setVisibility(View.GONE);
                view4.setVisibility(View.GONE);
            }
            if (model.getMaximum_parking_fee_perday().length() == 0) {
                maximum_parking_fee_perday_view.setVisibility(View.GONE);
                view5.setVisibility(View.GONE);
            }
            addedView.addAll(model.getList());
            addView();

        }
            progressBar2.setVisibility(View.VISIBLE);
            mainView.setVisibility(View.GONE);

        if(Utils.isNetworkAvailable(BusinessProductDetails.this))
        {
              apiCall=getBusinessList;
            controller.getWebApiCall().getDataCommon(Common.businessLocationUrl,controller.getManager().getUserToken(),this);
        }
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {

                String result=controller.getWebApiCall().getData(Common.getBusinessProductCategories,controller.getManager().getUserToken());
                if(result!=null)
                {if(Utils.getStatus(result)==true)
                {
                    JSONArray jsonArray=Utils.getJSONArray(result,"productcategories");
                    for(int i=0;i<jsonArray.length();i++)
                    {try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        CategoryModel model=new CategoryModel(jsonObject);
                        categorylist.add(model);
                        categorylistName.add(model.getCategoryName());
                    }catch (Exception ex)
                    {
                        ex.fillInStackTrace();
                    }

                    }
                    if(categorylistName.size()>0)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                category.setAdapter(new ArrayAdapter<String>(BusinessProductDetails.this,
                                        android.R.layout.simple_spinner_item,categorylistName));
if(model!=null) {
    category.setSelection(getCategoryIndex());
}
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        TextView child=(TextView) parent.getChildAt(0);
                                        //
                                        child.setTextSize(18);
                                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                            child.setTextColor(getResources().getColor(R.color.blue,getTheme()));
                                        } else{

                                            child.setTextColor(getResources().getColor(R.color.blue));
                                        }

                                        CategoryModel model=categorylist.get(position);
                                        if(selectedPos!=-1)
                                        {
                                            clearAll();
                                        }
                                        selectedPos=position;

                                        if(model.getField_names().equalsIgnoreCase("price_per_liter"))
                                        {   per_liter_price_view.setVisibility(View.VISIBLE);
                                            view2.setVisibility(View.VISIBLE);
                                            total_price_view.setVisibility(View.GONE);
                                            view1.setVisibility(View.GONE);
                                            per_hour_price_view.setVisibility(View.GONE);
                                            view3.setVisibility(View.GONE);
                                            minimum_parking_hours_view.setVisibility(View.GONE);
                                            view4.setVisibility(View.GONE);
                                            maximum_parking_fee_perday_view.setVisibility(View.GONE);
                                            view5.setVisibility(View.GONE);
                                        }else if(model.getField_names().equalsIgnoreCase("total_price"))
                                        {
                                            per_liter_price_view.setVisibility(View.GONE);
                                            view2.setVisibility(View.GONE);
                                            total_price_view.setVisibility(View.VISIBLE);
                                            view1.setVisibility(View.VISIBLE);
                                            per_hour_price_view.setVisibility(View.GONE);
                                            view3.setVisibility(View.GONE);
                                            minimum_parking_hours_view.setVisibility(View.GONE);
                                            view4.setVisibility(View.GONE);
                                            maximum_parking_fee_perday_view.setVisibility(View.GONE);
                                            view5.setVisibility(View.GONE);
                                        }else
                                        {
                                            per_liter_price_view.setVisibility(View.GONE);
                                            view2.setVisibility(View.GONE);
                                            total_price_view.setVisibility(View.VISIBLE);
                                            view1.setVisibility(View.VISIBLE);
                                            per_hour_price_view.setVisibility(View.VISIBLE);
                                            view3.setVisibility(View.VISIBLE);
                                            minimum_parking_hours_view.setVisibility(View.VISIBLE);
                                            view4.setVisibility(View.VISIBLE);
                                            maximum_parking_fee_perday_view.setVisibility(View.VISIBLE);
                                            view5.setVisibility(View.VISIBLE);
                                        }


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                                progressBar2.setVisibility(View.GONE);
                                mainView.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }

                }

            }
        });
        t.start();
    }

    public void clearAll() {
        per_liter_price.setText("");
        total_price.setText("");
        parking_fee_per_hou.setText("");
        minimum_parking_hours.setText("");
        maximum_parking_fee_perday.setText("");
    }
public int getCategoryIndex()
{
    for(int i=0;i<categorylist.size();i++)
    {
        if(model.getProduct_category_id().equalsIgnoreCase(categorylist.get(i).getCategoryId()))
        {
            return i;

        }

    }
    return 0;
}
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.add_location:
                showAlert();
                break;
            case R.id.submit:
                if(isFieldsValidated())
                {
                    if (Utils.isNetworkAvailable(BusinessProductDetails.this)){

                        submit.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        if(model==null) {
                            apiCall=addPRoduct;
                            controller.getWebApiCall().postData(Common.addBusinessProducts,controller.getManager().getUserToken(),Common.addBusinessProduct,getRequestArray(),this);
                        }else {
                            apiCall=updateProduct;
                            controller.getWebApiCall().postData(Common.updateBusinessProducts,controller.getManager().getUserToken(),Common.updateBusinessProduct,getRequestArray(),this);

                        }
                    }
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
                    try {

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
                    case 2:
                        if(Utils.getStatus(value))
                        {
                            Intent data = new Intent();
                            setResult(RESULT_OK, data);
                            finish();
                            Toast.makeText(BusinessProductDetails.this,"Product added sucessfully",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        if(Utils.getStatus(value))
                        {
                            Toast.makeText(BusinessProductDetails.this,"Product updated sucessfully",Toast.LENGTH_SHORT).show();
                            Intent data = new Intent();
                            setResult(RESULT_OK, data);
                            finish();
                        }
                        break;
                }

            }
        });

    }

    @Override
    public void onError(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (apiCall) {
                    case 1:
                    progressBar2.setVisibility(View.GONE);
                    mainView.setVisibility(View.VISIBLE);
                    break;
                    case 2:
                    case 3:
                        progressBar.setVisibility(View.GONE);
                        submit.setVisibility(View.VISIBLE);
                        break;
                }
                Utils.showToast(BusinessProductDetails.this,Utils.getMessage(value));
            }
        });
    }
    public void showAlert()
    {
         dialog = new Dialog(BusinessProductDetails.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.choose_location);

        ListView listView = (ListView) dialog.findViewById(R.id.listView);
        listView.setAdapter(new BusinessLocationAdapter( businessLocationList,BusinessProductDetails.this,callback));

        dialog.show();
    }

    public void addItemToList(Business_locations modell) {

    if (isItemPresent(modell)) {
        Utils.showToast(BusinessProductDetails.this, "Location Already added");
    } else {
        addedView.add(modell);
        addView();

    }

    }


public boolean isItemPresent(Business_locations modell) {
    boolean status = false;
    for (int i = 0; i < addedView.size(); i++) {
        Business_locations presentModel = addedView.get(i);
        if (presentModel.getId().equalsIgnoreCase(modell.getId())) {
            status = true;
            break;
        }
    }
    return status;
}
    public void removeItemFromList(Business_locations modell)
    {
        if(isItemPresent(modell))
        {
            addedView.remove(modell);
            addView();
        }
    }
    @Override
    public void onBusinessLocationSlected(final Business_locations modell) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addItemToList(modell);
                dialog.cancel();

            }
        });
    }

    @Override
    public void onDeleteCLicked(final Business_locations model) {
          runOnUiThread(new Runnable() {
              @Override
              public void run() {
                         }
          });
    }

    @Override
    protected void onDestroy() {
        model=null;
        super.onDestroy();

    }

    public boolean isFieldsValidated()
    {
        if((productName.getText().length()>0)&&(description.getText().length()>0)&&(addedView.size()>0))
        {

            return true;
        }else{
            if(productName.getText().length()==0)
            {
                Utils.showToast(BusinessProductDetails.this,"Please enter product name");
            }else if(description.getText().length()==0)
            { Utils.showToast(BusinessProductDetails.this,"Please enter product description");

            }else if(addedView.size()==0)
            { Utils.showToast(BusinessProductDetails.this,"Please add business location");

            }
            return false;
        }
    }

    public String[] getRequestArray() {
        String product_category_id = getCategoryId(category.getSelectedItem().toString());
        String business_location_ids = getBusinessId();
        String name = productName.getText().toString();
        String descriptionValue = description.getText().toString();
        String total_priceValue = "";


        if (total_price.getText().length() > 0) {
            total_priceValue = total_price.getText().toString();
        }
        String price_per_liter = "";
        if (per_liter_price.getText().length() > 0) {
            price_per_liter = per_liter_price.getText().toString();
        }
        String parking_fee_per_hour = "";
        if (parking_fee_per_hou.getText().length() > 0) {
            parking_fee_per_hour = parking_fee_per_hou.getText().toString();
        }
        String minimum_parking_hoursValue = "";
        if (minimum_parking_hours.getText().length() > 0) {
            minimum_parking_hoursValue = minimum_parking_hours.getText().toString();
        }
        String maximum_parking_fee_perdayValue = "";
        if (maximum_parking_fee_perday.getText().length() > 0) {
            maximum_parking_fee_perdayValue = maximum_parking_fee_perday.getText().toString();
        }
        if (model == null) {
            return new String[]{product_category_id, business_location_ids, name, descriptionValue, total_priceValue, price_per_liter, parking_fee_per_hour, minimum_parking_hoursValue, maximum_parking_fee_perdayValue};
        } else {
            return new String[]{model.getId(), product_category_id, business_location_ids, name, descriptionValue, total_priceValue, price_per_liter, parking_fee_per_hour, minimum_parking_hoursValue, maximum_parking_fee_perdayValue};

        }
    }


     public String getBusinessId()
     {
         String id="";
         for(int i=0;i<addedView.size();i++)
         {
             if(i==0)
             {
                 id=addedView.get(i).getId();
             }else{
                 id+=","+addedView.get(i).getId();
             }
         }
         return id;
     }
    public String getCategoryId(String categoryName) {
        for (int i = 0; i < categorylist.size(); i++) {
            if (categoryName.equalsIgnoreCase(categorylist.get(i).getCategoryName())) {
                return categorylist.get(i).getCategoryId();

            }
        }
        return "";
    }

}