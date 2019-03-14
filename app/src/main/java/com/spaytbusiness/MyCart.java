package com.spaytbusiness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import models.BusinessProductModel;
import utils.Utils;

public class MyCart extends Activity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.content)
    LinearLayout content;
AppController controller;
@BindView(R.id.grand_total)
TextView grandTotal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cart);
        controller=(AppController)getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        for (int i=0;i<controller.getGetMyCart().size();i++)
        {
            final BusinessProductModel model=controller.getGetMyCart().get(i);
            View row = getLayoutInflater().inflate(R.layout.my_cart_row, null);

            TextView date=(TextView)row.findViewById(R.id.date) ;
            TextView productName=(TextView)row.findViewById(R.id.productName) ;
            final TextView total_price=(TextView)row.findViewById(R.id.total_price);
          final  EditText quantity=(EditText) row.findViewById(R.id.quantity) ;
           final EditText price=(EditText) row.findViewById(R.id.price) ;

            date.setText(getDate());
            productName.setText(model.getName());
            quantity.setText(Integer.toString(model.getQuantity()));
            price.setText(model.getPrice());
            Double priceValue=Double.parseDouble(model.getPrice())*(model.getQuantity());
            total_price.setText(Double.toString(priceValue)+" £");

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
                        total_price.setText(Double.toString(val)+" £");
                        int index=controller.getIndexOfModel(model);
                        model.setPrice(price.getText().toString());
                        controller.updateModel(model,index);
                        grandTotal.setText(Double.toString(controller.getTotalPrice())+" £");
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
                    {  double val=Double.parseDouble(price.getText().toString())*Integer.parseInt(quantity.getText().toString());
                        total_price.setText(Double.toString(val)+" £");
                        int index=controller.getIndexOfModel(model);
                        model.setQuantity(Integer.parseInt(quantity.getText().toString()));
                        controller.updateModel(model,index);
                        grandTotal.setText(Double.toString(controller.getTotalPrice())+" £");
                    }
                }
            });


            content.addView(row);

        }
        grandTotal.setText(Double.toString(controller.getTotalPrice())+" £");



    }

    public String getDate()
    {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;


        }
    }

    }