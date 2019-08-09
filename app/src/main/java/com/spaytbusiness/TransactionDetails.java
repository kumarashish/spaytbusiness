package com.spaytbusiness;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import models.BusinessProductModel;
import models.OrderDetailsModel;
import models.OutstandingOrder;
import okhttp3.internal.Util;
import utils.FileDownloader;
import utils.Utils;

import static android.support.constraint.Constraints.TAG;

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
                handleDownload();

                break;
            case R.id.email:
              emailInvoice();
                break;
        }
    }

    public void handleDownload() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                downloadInvoice();
                //File write logic here

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 22);
            }
        } else {
            downloadInvoice();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            downloadInvoice();
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
                        if (Utils.getStatus(value)) {
                            Utils.showToast(TransactionDetails.this, "Email has been send to registered Email Id.");
                        } else {
                            Utils.showToast(TransactionDetails.this, Utils.getMessage(value));
                        }
                        break;
                    case 3:
                        if(pd!=null)
                        {
                            pd.cancel();
                        }
                        if(Utils.getStatus(value))
                        {
                     String pdfPath=Utils.getString(value,"pdf_path");
                            pd.show();
                            new DownloadFile().execute(pdfPath, Utils.getFileName(controller.getProfile().getFirst_name()));
                        }else {
                            Utils.showToast(TransactionDetails.this, Utils.getMessage(value));
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
                if (pd != null) {
                    pd.cancel();
                }
              progressBar.setVisibility(View.GONE);
              Utils.showToast(TransactionDetails.this,Utils.getMessage(value));
            }
        });
    }

    public void setValue() {
        Double grandTotalValue = 0.0;
        if (model.getOrderDetailsData() != null) {
            for (int i = 0; i < model.getOrderDetailsData().size(); i++) {
                final OrderDetailsModel.OrderDetailsDatum modell = model.getOrderDetailsData().get(i);
                View row = getLayoutInflater().inflate(R.layout.my_cart_row, null);
                TextView productName = (TextView) row.findViewById(R.id.productName);
                final TextView total_price = (TextView) row.findViewById(R.id.total_price);
                final EditText quantity = (EditText) row.findViewById(R.id.quantity);
                final EditText price = (EditText) row.findViewById(R.id.price);
                productName.setText(modell.getName());
                quantity.setText(modell.getQuantity());
                price.setText(Utils.getFormattedAmount(modell.getNetAmount()));
                Double priceValue = (Double.parseDouble(modell.getNetAmount()) * Double.parseDouble(modell.getQuantity()));
                total_price.setText(Utils.getFormattedAmount(Double.toString(priceValue)) + " €");
                price.setEnabled(false);
                quantity.setEnabled(false);
                if (i == 0) {
                    grandTotalValue = priceValue;
                } else {
                    grandTotalValue += priceValue;
                }
                content.addView(row);

            }
            grandTotal.setText(Utils.getFormattedAmount(Double.toString(grandTotalValue)) + " €");
            progressBar.setVisibility(View.GONE);
            contentView.setVisibility(View.VISIBLE);
        } else {
            Utils.showToast(TransactionDetails.this, "Order details not available...");
            progressBar.setVisibility(View.GONE);
        }
    }

    private class DownloadFile extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "SpaytBusinessInvoices");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return FileDownloader.downloadFile(fileUrl, pdfFile, TransactionDetails.this);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.length() > 0) {
                openPdf(s);
            }
           pd.cancel();
        }
    }

    public void openPdf(String fileName) {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/SpaytBusinessInvoices/" + fileName);  // -> filename = maven.pdf
        if (pdfFile .exists()) {

            Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(TransactionDetails.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }else{
            Toast.makeText(TransactionDetails.this, "File Doesnot Exists.", Toast.LENGTH_SHORT).show();
        }
        }
    }

