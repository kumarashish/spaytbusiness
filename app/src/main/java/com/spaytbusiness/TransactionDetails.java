package com.spaytbusiness;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import models.BusinessProductModel;
import models.OrderDetailsModel;
import models.OutstandingOrder;
import okhttp3.internal.Util;
import utils.Utils;

public class TransactionDetails extends Activity implements WebApiResponseCallback, View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.content)
    LinearLayout content;
    AppController controller;
    @BindView(R.id.grand_total)
    TextView grandTotal;
    @BindView(R.id.download)
    Button download;
    @BindView(R.id.email)
    Button email;


    Dialog pd;

    int apiCall;
    int getOrderDetails=1,emailInvoice=2,downloadInvoice=3;
    String orderId="";
    String customerName="";
    String customerId="";
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.contentView)
    RelativeLayout contentView;
    OrderDetailsModel model=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_details);
        orderId=getIntent().getStringExtra("orderId");
        controller=(AppController)getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        email.setOnClickListener(this);
        download.setOnClickListener(this);
        if (Utils.isNetworkAvailable(TransactionDetails.this)) {
            progressBar.setVisibility(View.VISIBLE);
            contentView.setVisibility(View.GONE);
            apiCall=getOrderDetails;
            controller.getWebApiCall().postData(Common.getBusinessOrderDetails, controller.getManager().getUserToken(), Common.id, new String[]{orderId}, this);
        }
    }

    public void emailInvoice() {
        apiCall = emailInvoice;
        pd = Utils.getProgressDailog(TransactionDetails.this);
        controller.getWebApiCall().postData(Common.sendOrderPdfEmail, controller.getManager().getUserToken(), Common.id, new String[]{orderId}, this);
        pd.show();
    }

    public void downloadInvoice() {
        apiCall = downloadInvoice;
        pd = Utils.getProgressDailog(TransactionDetails.this);
        controller.getWebApiCall().postData(Common.downloadOrderPdfInvoice, controller.getManager().getUserToken(), Common.id, new String[]{orderId}, this);
        pd.show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.download:
                downloadInvoice();
                break;
            case R.id.email:
              emailInvoice();
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
                    if (Utils.getStatus(value)) {
                        model = new Gson().fromJson(value, OrderDetailsModel.class);
                        setValue();
                    } else {
                        Utils.showToast(TransactionDetails.this, Utils.getMessage(value));
                        progressBar.setVisibility(View.GONE);
                    }
                    break;
                    case 2:
                     if(pd!=null)
                     {
                         pd.cancel();
                     }
                        Utils.showToast(TransactionDetails.this, Utils.getMessage(value));
                        break;
                    case 3:
                        if(pd!=null)
                        {
                            pd.cancel();
                        }
                        Utils.showToast(TransactionDetails.this, Utils.getMessage(value));
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
                if (pd != null) {
                    pd.cancel();
                }
              progressBar.setVisibility(View.GONE);
              Utils.showToast(TransactionDetails.this,Utils.getMessage(value));
            }
        });
    }

    public void setValue()
    {Double grandTotalValue=0.0;
        for (int i=0;i<model.getOrderDetailsData().size();i++)
        {
            final OrderDetailsModel.OrderDetailsDatum modell=model.getOrderDetailsData().get(i);
            View row = getLayoutInflater().inflate(R.layout.my_cart_row, null);
            TextView productName=(TextView)row.findViewById(R.id.productName) ;
            final TextView total_price=(TextView)row.findViewById(R.id.total_price);
            final EditText quantity=(EditText) row.findViewById(R.id.quantity) ;
            final EditText price=(EditText) row.findViewById(R.id.price) ;
            productName.setText(modell.getName());
            quantity.setText(modell.getQuantity());
            price.setText(modell.getNetAmount());
            Double priceValue=(Double.parseDouble(modell.getNetAmount())*Double.parseDouble(modell.getQuantity()));
            total_price.setText(priceValue+" €");
            price.setEnabled(false);
            quantity.setEnabled(false);
            if(i==0)
            {
                grandTotalValue=priceValue;
            }else{
                grandTotalValue+=priceValue;
            }
            content.addView(row);

        }
        grandTotal.setText(grandTotalValue+" €");
        progressBar.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);

    }

}
