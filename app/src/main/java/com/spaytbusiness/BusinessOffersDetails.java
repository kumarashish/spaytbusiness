package com.spaytbusiness;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import adapter.BusinessLocationAdapter;
import adapter.BusinessProductAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import fragments.Business;
import interfaces.OnListItemSelected;
import interfaces.WebApiResponseCallback;
import models.BusinessProductModel;
import models.Business_Offers;
import models.Business_locations;
import models.CategoryModel;
import utils.Utils;

/**
 * Created by ashish.kumar on 26-02-2019.
 */

@SuppressWarnings("ALL")
public class BusinessOffersDetails extends Activity implements View.OnClickListener, WebApiResponseCallback, OnListItemSelected {
    public static Business_Offers model;
    AppController controller;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.product)
    Spinner product;
    @BindView(R.id.offername)
    EditText offername;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.startDate)
    Button startDate;
    @BindView(R.id.end_date)
    Button end_date;
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
    @BindView(R.id.mode)
    Spinner mode;
    @BindView(R.id.m_mFrom)
    EditText m_mFrom;
    @BindView(R.id.m_mTo)
    EditText m_mTo;
    @BindView(R.id.m_aFrom)
    EditText m_aFrom;
    @BindView(R.id.m_aTo)
    EditText m_aTo;
    @BindView(R.id.add_location)
    ImageView add_location;
    @BindView(R.id.locationList)
    LinearLayout locationList;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.heading)
    TextView heading;
    ArrayList<Business_locations> addedView=new ArrayList<>();
    ArrayList<Business_locations> businessLocationList=new ArrayList<>();
    ArrayList<BusinessProductModel>businessProductsList=new ArrayList<>();
    ArrayList<String>businessProductsName=new ArrayList<>();
    @BindView(R.id.progress_bar2)
    ProgressBar progressBar2;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.mainLayout)
    ScrollView mainView;
    @BindView(R.id.afternoon_mode)
    LinearLayout afternoon_mode;
    @BindView(R.id.morning_mode)
    LinearLayout morning_mode;
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
    Dialog dialog;
    int apiCall;
    OnListItemSelected callback;
    int getProductList=1,addOffer=2,updateOffer=3;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    boolean isStartDateSelected=false;
    String startDateValue="";
    String endDateValue="";
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_product_offers);
        ButterKnife.bind(this);
        controller=(AppController)getApplicationContext();
        callback=this;
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        startDate.setOnClickListener(this);
        end_date.setOnClickListener(this);
        add_location.setOnClickListener(this);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setValue();
    mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {

            case 0:
                m_mFrom.setText("00:00");
                m_mTo.setText("23:59");
                m_aFrom.setText("");
                m_aTo.setText("");
               morning_mode.setVisibility(View.VISIBLE);
                afternoon_mode.setVisibility(View.GONE);


                break;
            case 1:
                m_mFrom.setText("");
                m_mTo.setText("");
                m_aFrom.setText("");
                m_aTo.setText("");
                morning_mode.setVisibility(View.VISIBLE);
                afternoon_mode.setVisibility(View.GONE);
                break;
            case 2:
                m_mFrom.setText("");
                m_mTo.setText("");
                m_aFrom.setText("");
                m_aTo.setText("");
                morning_mode.setVisibility(View.VISIBLE);
                afternoon_mode.setVisibility(View.VISIBLE);
                break;
            case 3:
                m_mFrom.setText("");
                m_mTo.setText("");
                m_aFrom.setText("");
                m_aTo.setText("");
                morning_mode.setVisibility(View.GONE);
                afternoon_mode.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };
    private void showDate(int year, int month, int day) {
        String monthValue=Integer.toString(month);
        String dayValue=Integer.toString(day);

        if(month<10)
        {
            monthValue="0"+month;
        }
        if(day<10)
        {
            dayValue="0"+day;
        }
       if( isStartDateSelected) {


           startDate.setText(new StringBuilder().append(year).append("-")
                   .append(monthValue).append("-").append(dayValue));
           startDateValue=startDate.getText().toString();

       }else {

           end_date.setText(new StringBuilder().append(year).append("-")
                   .append(monthValue).append("-").append(dayValue));
           endDateValue=end_date.getText().toString();
       }
    }
    public void setValue() {
        if (model == null) {
            heading.setText("Add Offer");
        } else {
            //product
            offername.setText(model.getOffer_name());
            description.setText(model.getOffer_description());
            startDate.setText(model.getFrom_date());
            startDateValue=startDate.getText().toString();
            end_date.setText(model.getTo_date());
            endDateValue=end_date.getText().toString();
            total_price.setText(model.getTotal_price());
            per_liter_price.setText(model.getPrice_per_liter());
            parking_fee_per_hou.setText(model.getParking_fee_per_hour());
            minimum_parking_hours.setText(model.getMinimum_parking_hours());
            maximum_parking_fee_perday.setText(model.getMaximum_parking_fee_perday());
            //mode
            m_mFrom.setText(model.getMorning_from());
            m_mTo.setText(model.getMorning_to());
            m_aFrom.setText(model.getAfternoon_from());
            m_aTo.setText(model.getAfternoon_to());
            addedView.addAll(model.getLocations());
            if (Double.parseDouble(model.getTotal_price()) == 0.0) {
                total_price_view.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
            }
            if (Double.parseDouble(model.getPrice_per_liter()) == 0.0) {
                per_liter_price_view.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
            }
            if (Double.parseDouble(model.getParking_fee_per_hour()) == 0.0) {
                per_hour_price_view.setVisibility(View.GONE);
                view3.setVisibility(View.GONE);
            }
            if (Double.parseDouble(model.getMinimum_parking_hours()) == 0) {
                minimum_parking_hours_view.setVisibility(View.GONE);
                view4.setVisibility(View.GONE);
            }
            if (Double.parseDouble(model.getMaximum_parking_fee_perday()) == 0) {
                maximum_parking_fee_perday_view.setVisibility(View.GONE);
                view5.setVisibility(View.GONE);
            }
            addView();
        }
            if (Utils.isNetworkAvailable(BusinessOffersDetails.this)) {
                apiCall = getProductList;
                progressBar2.setVisibility(View.VISIBLE);
                mainView.setVisibility(View.GONE);
                controller.getWebApiCall().getDataCommon(Common.businessLocationUrl, controller.getManager().getUserToken(), this);
            }
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    String result = controller.getWebApiCall().getData(Common.businessProducts,controller.getManager().getUserToken());
                    if (result != null) {
                        if (Utils.getStatus(result) == true) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                JSONArray productList = jsonObject.getJSONArray("businessproducts_details");
                                if ((productList != null) && (productList.length() > 0)) {
                                    for (int i = 0; i < productList.length(); i++) {
                                        BusinessProductModel model = new BusinessProductModel(productList.getJSONObject(i));
                                        businessProductsList.add(model);
                                        businessProductsName.add(model.getName());
                                    }
                                }


                            } catch (Exception ex) {
                                ex.fillInStackTrace();
                            }
                            if (businessProductsList.size() > 0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        product.setAdapter(new ArrayAdapter<String>(BusinessOffersDetails.this,
                                                android.R.layout.simple_spinner_item, businessProductsName));
                                        if (model != null) {
                                            int catIndex=getCategoryIndex();
                                            product.setSelection(catIndex);
                                        }
                                        product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @RequiresApi(api = Build.VERSION_CODES.O)
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                BusinessProductModel model=businessProductsList.get(position);
                                                TextView child = (TextView) parent.getChildAt(0);
                                                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    child.setTextColor(getResources().getColor(R.color.blue, getTheme()));
                                                }else {
                                                    child.setTextColor(getResources().getColor(R.color.blue));
                                                }
                                                child.setTextSize(18);

                                                if (Double.parseDouble(model.getTotal_price())>0.0) {
                                                    total_price_view.setVisibility(View.VISIBLE);
                                                    view1.setVisibility(View.VISIBLE);
                                                    per_liter_price_view.setVisibility(View.GONE);
                                                    view2.setVisibility(View.GONE);
                                                    per_hour_price_view.setVisibility(View.GONE);
                                                    view3.setVisibility(View.GONE);
                                                    minimum_parking_hours_view.setVisibility(View.GONE);
                                                    view4.setVisibility(View.GONE);
                                                    maximum_parking_fee_perday_view.setVisibility(View.GONE);
                                                    view5.setVisibility(View.GONE);
                                                }
                                                else if (Double.parseDouble(model.getPrice_per_liter())> 0.0) {
                                                    total_price_view.setVisibility(View.GONE);
                                                    view1.setVisibility(View.GONE);
                                                    per_liter_price_view.setVisibility(View.VISIBLE);
                                                    view2.setVisibility(View.VISIBLE);
                                                    per_hour_price_view.setVisibility(View.GONE);
                                                    view3.setVisibility(View.GONE);
                                                    minimum_parking_hours_view.setVisibility(View.GONE);
                                                    view4.setVisibility(View.GONE);
                                                    maximum_parking_fee_perday_view.setVisibility(View.GONE);
                                                    view5.setVisibility(View.GONE);
                                                }else{
                                                    total_price_view.setVisibility(View.GONE);
                                                    view1.setVisibility(View.GONE);
                                                    per_liter_price_view.setVisibility(View.GONE);
                                                    view2.setVisibility(View.GONE);
                                                    per_hour_price_view.setVisibility(View.VISIBLE);
                                                    view3.setVisibility(View.VISIBLE);
                                                    minimum_parking_hours_view.setVisibility(View.VISIBLE);
                                                    view4.setVisibility(View.VISIBLE);
                                                    maximum_parking_fee_perday_view.setVisibility(View.VISIBLE);
                                                    view5.setVisibility(View.VISIBLE);
                                                }

                                                //  child.setTypeface(getResources().getFont(R.font.light));
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {

                                            }
                                        });
                                        progressBar2.setVisibility(View.GONE);
                                        mainView.setVisibility(View.VISIBLE);

                                    }

                                });
                            }else{
                                Utils.showToast(BusinessOffersDetails.this,"You dont have any product,please add atleast one product first.");
                            }

                        }

                    }

                }
            });
            t.start();

    }

    public int getCategoryIndex() {
        for (int i = 0; i < businessProductsList.size(); i++) {
            if (model.getProduct_id().equalsIgnoreCase(businessProductsList.get(i).getId())) {
                return i;

            }

        }
        return 0;
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
    public void addItemToList(Business_locations modell) {

        if (isItemPresent(modell)) {
            Utils.showToast(BusinessOffersDetails.this, "Location Already added");
        } else {
            addedView.add(modell);
            addView();

        }

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
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.add_location:
                if(businessLocationList.size()>0) {
                    showAlert();
                }else{
                    Utils.showToast(BusinessOffersDetails.this,"You have not added any business location,Please add business location");
                }
                break;
            case R.id.startDate:
                isStartDateSelected=true;
                showDialog(999);
                break;
            case R.id.end_date:
                isStartDateSelected=false;
                showDialog(999);
                break;
            case R.id.submit:
                if(isFieldsValidated()) {
                    submit.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    String url = Common.updateBusinessOffers;
                    String[] keys = null;
                    if (model == null) {
                        apiCall=addOffer;
                        url = Common.addBusinessOffers;
                        keys = Common.addBusinessOffersKeys;
                    } else {
                        apiCall=updateOffer;
                        keys = Common.updateBusinessOffersKeys;
                    }

                    controller.getWebApiCall().postData(url, controller.getManager().getUserToken(), keys, getRequest(), this);

                }
                break;

        }

    }
    public void showAlert()
    {
        dialog = new Dialog(BusinessOffersDetails.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.choose_location);

        ListView listView = (ListView) dialog.findViewById(R.id.listView);
        listView.setAdapter(new BusinessLocationAdapter( businessLocationList,BusinessOffersDetails.this,callback));

        dialog.show();
    }

    @Override
    public void onSucess(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (apiCall) {
                    case 1:
                        try {
                            businessLocationList.clear();
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
                                Utils.showToast(BusinessOffersDetails.this,"Business Offer added sucessfully");
                                Intent data = new Intent();
                                setResult(RESULT_OK, data);
                                finish();

                        }else{
                            submit.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Utils.showToast(BusinessOffersDetails.this,Utils.getMessage(value));
                        }
                        break;
                    case 3:
                if(Utils.getStatus(value))
                {

                    Utils.showToast(BusinessOffersDetails.this,"Business Offer Updated sucessfully");
                    Intent data = new Intent();
                    setResult(RESULT_OK, data);
                    finish();
                }else{
                    submit.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Utils.showToast(BusinessOffersDetails.this,Utils.getMessage(value));
                }
                        break;
                }
            }
        });

    }

    @Override
    public void onError(String value) {

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
    public void onDeleteCLicked(Business_locations model) {

    }

    public boolean isFieldsValidated()
    {
        if((offername.getText().length()>0)&&(description.getText().length()>0)&&(startDateValue.length()>0)&&(startDateValue.length()>0)&&(endDateValue.length()>0)&&(addedView.size()>0))
        {
            return true;
        }else{
            if(offername.getText().length()==0)
            {
                Utils.showToast(BusinessOffersDetails.this,"Please enter offer name");
            }else if(description.getText().length()==0)
            {
                Utils.showToast(BusinessOffersDetails.this,"Please enter description");
            }
            else if(startDateValue.length()==0)
            {
                Utils.showToast(BusinessOffersDetails.this,"Please select offer start date");
            }
            else if(endDateValue.length()==0)
            {
                Utils.showToast(BusinessOffersDetails.this,"Please select offer end date");
            }else if(addedView.size()==0)
            {
                Utils.showToast(BusinessOffersDetails.this,"Please add business location");
            }
            return false;
        }
    }

    public String[] getRequest() {
        String product_id_Value = getProductId(product.getSelectedItem().toString());
        String business_location_ids = getBusinessId();
        String offer_name_Value = offername.getText().toString();
        String offer_description_Value = description.getText().toString();
        String from_date_Value = startDateValue;
        String to_date_Value = endDateValue;
        String opening_hour_mode = mode.getSelectedItem().toString().substring(0, 1);

        String morning_from = "";
        if (m_mFrom.getText().length() > 0) {
            morning_from = m_mFrom.getText().toString();
        }
        String morning_to_Value = "";
        if (m_mTo.getText().length() > 0) {
            morning_to_Value = m_mTo.getText().toString();
        }
        String afternoon_from_Value = "";
        if (m_aFrom.getText().length() > 0) {
            afternoon_from_Value = m_aFrom.getText().toString();
        }
        String afternoon_to_Value = "";
        if (m_aTo.getText().length() > 0) {
            afternoon_to_Value = m_aTo.getText().toString();
        }
        String total_price_Value = "";
        if (total_price.getText().length() > 0) {
            total_price_Value = total_price.getText().toString();
        }
        String price_per_liter_Value = "";
        if (per_liter_price.getText().length() > 0) {
            price_per_liter_Value = per_liter_price.getText().toString();
        }
        String parking_fee_per_hour_Value = "";
        if (parking_fee_per_hou.getText().length() > 0) {
            parking_fee_per_hour_Value = parking_fee_per_hou.getText().toString();
        }
        String minimum_parking_hours_Value = "";
        if (minimum_parking_hours.getText().length() > 0) {
            minimum_parking_hours_Value = minimum_parking_hours.getText().toString();
        }
        String maximum_parking_fee_perday_Value = "";
        if (maximum_parking_fee_perday.getText().length() > 0) {
            maximum_parking_fee_perday_Value = maximum_parking_fee_perday.getText().toString();
        }
        if (model == null) {
            return new String[]{product_id_Value,
                    business_location_ids,
                    offer_name_Value,
                    offer_description_Value,
                    from_date_Value,
                    to_date_Value,
                    opening_hour_mode,
                    morning_from,
                    morning_to_Value,
                    afternoon_from_Value,
                    afternoon_to_Value,
                    total_price_Value,
                    price_per_liter_Value,
                    parking_fee_per_hour_Value,
                    minimum_parking_hours_Value,
                    maximum_parking_fee_perday_Value

            };
        } else {
            return new String[]{model.getId(), product_id_Value,
                    business_location_ids,
                    offer_name_Value,
                    offer_description_Value,
                    from_date_Value,
                    to_date_Value,
                    opening_hour_mode,
                    morning_from,
                    morning_to_Value,
                    afternoon_from_Value,
                    afternoon_to_Value,
                    total_price_Value,
                    price_per_liter_Value,
                    parking_fee_per_hour_Value,
                    minimum_parking_hours_Value,
                    maximum_parking_fee_perday_Value

            };
        }

    }
    public String getProductId(String categoryName) {
        for (int i = 0; i < businessProductsList.size(); i++) {
            if (categoryName.equalsIgnoreCase(businessProductsList.get(i).getName())) {
                return businessProductsList.get(i).getId();

            }
        }
        return "";
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

    @Override
    protected void onDestroy() {
        model=null;
        super.onDestroy();
    }
}
