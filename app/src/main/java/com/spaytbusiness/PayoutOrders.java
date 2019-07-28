package com.spaytbusiness;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import adapter.PayoutOrderAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import models.PayoutModel;

public class PayoutOrders extends Activity implements View.OnClickListener {
    public static List<PayoutModel.PayoutConsumerOrder> list;
    @BindView(R.id.back)
Button back;
@BindView(R.id.listView)
ListView orderlist;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payout_order_list);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        if (list != null) {
            orderlist.setAdapter(new PayoutOrderAdapter(PayoutOrders.this, list));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
