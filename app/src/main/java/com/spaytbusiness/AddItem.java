package com.spaytbusiness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddItem extends Activity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.mycart)
    View myCart;
    @BindView(R.id.customer_name)
    TextView customer_name;
    String customerCode="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        ButterKnife.bind(this);
        back.setOnClickListener(this);
        myCart.setOnClickListener(this);
        customerCode=getIntent().getStringExtra("code");
        customer_name.setText(customerCode);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.mycart:
               startActivity(new Intent(this,MyCart.class));
                break;

        }
    }
}