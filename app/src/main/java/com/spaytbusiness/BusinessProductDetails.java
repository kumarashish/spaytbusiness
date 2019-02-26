package com.spaytbusiness;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import interfaces.BusinessProductClicked;
import interfaces.BusinessUserClicked;
import models.BusinessProductModel;
import models.BusinessProfile;
import models.Business_locations;

/**
 * Created by ashish.kumar on 26-02-2019.
 */

public class BusinessProductDetails extends Activity implements View.OnClickListener{
    @BindView(R.id.back)
    Button back;
    public static BusinessProductModel model=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_product_details);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
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