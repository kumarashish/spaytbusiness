package com.spaytbusiness;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.SSLEngineResult;

import adapter.CustomerListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import models.BusinessProductModel;
import models.ExistingCustomerModel;
import utils.Utils;

public class MyCart extends Activity implements View.OnClickListener , WebApiResponseCallback {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.content)
    LinearLayout content;
    AppController controller;
    @BindView(R.id.grand_total)
    TextView grandTotal;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.customer_name)
    TextView customer_name;
   Dialog pd;
    String locationId="";
    int apiCall;
    int createOrder=1,submitOrder=2,getCustomerFromCode=3,selectExistingCustomer=4;
    String orderId="";
String customerName="";
String customerId="";
@BindView(R.id.progressbar2)
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cart);
        controller=(AppController)getApplicationContext();
        ButterKnife.bind(this);

        locationId=getIntent().getStringExtra("locationId");

        back.setOnClickListener(this);
        submit.setOnClickListener(this);
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pd=Utils.getProgressDailog(MyCart.this);
        }

        for (int i=0;i<controller.getGetMyCart().size();i++)
        {
            final BusinessProductModel model=controller.getGetMyCart().get(i);
            View row = getLayoutInflater().inflate(R.layout.my_cart_row, null);

            TextView productName=(TextView)row.findViewById(R.id.productName) ;
            final TextView total_price=(TextView)row.findViewById(R.id.total_price);
          final  EditText quantity=(EditText) row.findViewById(R.id.quantity) ;
           final EditText price=(EditText) row.findViewById(R.id.price) ;

            productName.setText(model.getName());
            quantity.setText(Integer.toString(model.getQuantity()));
            price.setText(Utils.getFormattedAmount(model.getPrice()));
            Double priceValue=Double.parseDouble(model.getPrice())*(model.getQuantity());
            total_price.setText(Utils.getFormattedAmount(Double.toString(priceValue))+" €");

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
                        total_price.setText(Utils.getFormattedAmount(Double.toString(val))+" €");
                        int index=controller.getIndexOfModel(model);
                        model.setPrice(price.getText().toString());
                        controller.updateModel(model,index);
                        grandTotal.setText(Utils.getFormattedAmount(Double.toString(controller.getTotalPrice()))+" €");
                    }

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
                    {   double val=Double.parseDouble(price.getText().toString().trim())*Integer.parseInt(quantity.getText().toString().trim());
                        total_price.setText(Utils.getFormattedAmount(Double.toString(val))+" €");
                        int index=controller.getIndexOfModel(model);
                        model.setQuantity(Integer.parseInt(quantity.getText().toString()));
                        controller.updateModel(model,index);
                        grandTotal.setText(Utils.getFormattedAmount(Double.toString(controller.getTotalPrice()))+" €");
                    }
                }
            });
            content.addView(row);

        }
        grandTotal.setText(Utils.getFormattedAmount(Double.toString(controller.getTotalPrice()))+" €");

    }

    public String getDate()
    {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        return formattedDate;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                if(controller.getTotalPrice()>0.0)
                if (Utils.isNetworkAvailable(MyCart.this)) {
                    showChooseCustomerAlert();

                }
                break;


        }
    }
    public void getExisistingCustomer()
    {
        apiCall = selectExistingCustomer;
        pd.show();
        controller.getWebApiCall().getDataCommon(Common.getExistingCustomer, controller.getManager().getUserToken(), MyCart.this);

    }
    public void submitOrder()
    {
        if (orderId.length() > 0) {
            submitOrder(orderId);
        } else {
            apiCall = createOrder;
            pd.show();
            controller.getWebApiCall().postData(Common.getCreateOrderUrl, controller.getManager().getUserToken(), getCreateOrderJson(customerId, locationId).toString(), MyCart.this);
        }
    }

    public void submitOrder(String orderId) {
        pd.show();
        apiCall = submitOrder;
        controller.getWebApiCall().postData(Common.getSubmitOrderUrl, controller.getManager().getUserToken(), Common.id, new String[]{orderId}, MyCart.this);

    }

    public JSONObject getCreateOrderJson(String consumerId, String locationId) {
        JSONObject jsonObject = new JSONObject();
        JSONArray productsArray = new JSONArray();
        try {
            jsonObject.put("consumer_id", consumerId);
            jsonObject.put("location_id", locationId);
            for (int i = 0; i < controller.getGetMyCart().size(); i++) {
                BusinessProductModel model = controller.getGetMyCart().get(i);
                JSONObject products = new JSONObject();
                products.put("type", model.getType());
                products.put("id", model.getId());
                products.put("name", model.getName());
                products.put("description", model.getType());
                products.put("quantity", model.getQuantity());
                products.put("price", model.getPrice());
                productsArray.put(i, products);
            }
            jsonObject.put("order_items", productsArray);

        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onSucess(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(pd!=null)
                {
                    pd.cancel();
                }
                if(Utils.getStatus(value)==true)
                {
                    switch (apiCall)
                    {
                        case 1:
                            orderId=getOrderId(value);
                            submitOrder(orderId);
                            break;
                        case 2:
                            controller.getGetMyCart().clear();
                            Utils.sucessAlert(MyCart.this,orderId);

                            break;
                        case 3:
                        if(Utils.getStatus(value))
                        {
                            JSONObject jsonObject=Utils.getJSONObject(value,"consumer_details");
                            try {
                                customerName = jsonObject.getString("first_name") + " " + jsonObject.getString("last_name");
                                customerId=jsonObject.getString("consumer_id");
                                customer_name.setText(customerName);
                                progressBar.setVisibility(View.GONE);
                                submit.setVisibility(View.VISIBLE);
                                submitOrder();

                            }catch (Exception ex)
                            {
                                ex.fillInStackTrace();
                                Utils.showToast(MyCart.this,Utils.getMessage(value));
                                progressBar.setVisibility(View.GONE);
                                startActivityForResult(new Intent(MyCart.this,ScanActivity.class),2);

                            }
                        } else{
                            Utils.showToast(MyCart.this,Utils.getMessage(value));
                            invalidQRCodeAlert(Utils.getMessage(value));
                            progressBar.setVisibility(View.GONE);
                            submit.setVisibility(View.VISIBLE);
                        }
                        break;
                        case 4:
                            if(Utils.getStatus(value)) {
                                ExistingCustomerModel model=new Gson().fromJson(value,ExistingCustomerModel.class);
                                if(model.getExistingConsumers().size()>0)
                                {
                                    selectCustomerAlert(model.getExistingConsumers());
                                }else{
                                    Utils.showToast(MyCart.this,"No Existing Customer found,Please Use Scan Option.");
                                }
                            }else{
                                if(pd!=null)
                                {
                                    pd.cancel();
                                }
                                Utils.showToast(MyCart.this,Utils.getMessage(value));
                            }

                            break;

                    }

                }else{
                    if(pd!=null)
                    {
                        pd.cancel();
                    }
                    Utils.showToast(MyCart.this,Utils.getMessage(value));
                    progressBar.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    public void invalidQRCodeAlert(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);;
        //Setting message manually and performing action on button click
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivityForResult(new Intent(MyCart.this,ScanActivity.class),2);
                        finish();

                    }
                })
                .setNegativeButton("exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        finish();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Alert");
        alert.show();
    }

    public void selectCustomerAlert(final List<ExistingCustomerModel.ExistingConsumer> list) {
        final Dialog dialog = new Dialog(MyCart.this);
        dialog.setCancelable(false); // if you want user to wait for some process to finish,
        dialog.setContentView(R.layout.choose_customer);

        final ListView custmerlist = (ListView) dialog.findViewById(R.id.custmerlist);
        custmerlist.setAdapter(new CustomerListAdapter(MyCart.this,list));
        Button cancel=(Button)dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        custmerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                customerId=list.get(i).getConsumerId();
                submitOrder();
                dialog.cancel();
            }

        });

        dialog.show();
    }

    public void showChooseCustomerAlert()
    {
        final Dialog dialog = new Dialog(MyCart.this);

        dialog.setCancelable(false); // if you want user to wait for some process to finish,
        dialog.setContentView(R.layout.new_transaction);

        Button existing_customer=(Button)dialog.findViewById(R.id.existing_customer);
        Button scanView=(Button)dialog.findViewById(R.id.scan_customer);
        existing_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getExisistingCustomer();
                dialog.cancel();
            }
        });
        scanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MyCart.this,ScanActivity.class),2);
                dialog.cancel();
            }
        });
        dialog.show();
    }
public String getOrderId(String value)
{
    try{
        JSONObject jsonObject=new JSONObject(value);
        JSONArray orderData=jsonObject.getJSONArray("order_data");
        return  orderData.getJSONObject(0).getString("id");
    }catch (Exception ex)
    {
        ex.fillInStackTrace();
    }
    return "";
}
    @Override
    public void onError(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utils.showToast(MyCart.this,Utils.getMessage(value));
                if(progressBar.getVisibility()==View.VISIBLE)
                {
                    progressBar.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                }
                if(pd!=null)
                {
                    pd.cancel();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==2)&&(resultCode==RESULT_OK))
        {String customerCode=data.getStringExtra("code");
            if(Utils.isNetworkAvailable(MyCart.this))
            {apiCall=getCustomerFromCode;
                progressBar.setVisibility(View.VISIBLE);
                submit.setVisibility(View.GONE);
                controller.getWebApiCall().postData(Common.getCustomerFromQRCodeUrl,controller.getManager().getUserToken(),Common.qrCodeKey,new String[]{customerCode},this);
            }
        }
    }
}

