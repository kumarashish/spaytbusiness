package com.spaytbusiness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewTransaction extends Activity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.authorise_with_paypal)
    Button authorise_with_paypal;
    @BindView(R.id.existing_customer)
    Button existing_custoemr;

    @BindView(R.id.scan_customer)
    Button scan_customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_transaction);

        ButterKnife.bind(this);
        back.setOnClickListener(this);
        scan_customer.setOnClickListener(this);
        existing_custoemr.setOnClickListener(this);
        authorise_with_paypal.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.scan_customer:
                startActivity(new Intent(NewTransaction.this,ScanActivity.class));
                break;
            case R.id.existing_customer:
              startActivity(new Intent(NewTransaction.this,ChooseCustomer.class));
                break;
            case R.id.authorise_with_paypal:

                break;

        }

    }
    }