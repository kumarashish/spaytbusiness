package com.spaytbusiness;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapter.PayoutOrderAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.OnDeleteOrderClickListner;
import interfaces.WebApiResponseCallback;
import models.PayoutModel;
import okhttp3.internal.Util;
import utils.Utils;

public class PayoutOrders extends Activity implements View.OnClickListener, OnDeleteOrderClickListner, WebApiResponseCallback {
    public static List<PayoutModel.PayoutConsumerOrder> list;
    public  List<PayoutModel.PayoutConsumerOrder> templist=new ArrayList<>();
    public static String payoutId="";
    @BindView(R.id.back)
Button back;
@BindView(R.id.listView)
ListView orderlist;
    PayoutOrderAdapter adapter;
    @BindView(R.id.update)
    Button update;
    Dialog pd;
    AppController controller;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payout_order_list);
        ButterKnife.bind(this);
        controller=(AppController) getApplicationContext();
        back.setOnClickListener(this);
        update.setOnClickListener(this);
        pd= Utils.getProgressDailog(PayoutOrders.this);
        if (list != null) {
            templist.clear();
            templist.addAll(list);
            adapter=new PayoutOrderAdapter(PayoutOrders.this, templist);
            orderlist.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
               onBackPressed();
                break;
            case R.id.update:
               pd.show();
                updatePayout();
                break;
        }
    }

    public String getPayoutOrders()
    {
        String payoutorder=templist.get(0).getConsumerOrderId();
        if(templist.size()>1) {
            for (int i=1;i<templist.size();i++)
            {
                payoutorder+=","+templist.get(i).getConsumerOrderId();
            }
        }
        return payoutorder;
    }
    public void updatePayout() {

        controller.getWebApiCall().postData(Common.updatePayout, controller.getManager().getUserToken(),Common.updatePayoutKeys, new String[]{payoutId,getPayoutOrders()}, this);
        pd.show();
    }
    @Override
    public void onDeleteClick(final PayoutModel.PayoutConsumerOrder model) {
runOnUiThread(new Runnable() {
    @Override
    public void run() {
        if(templist.size()>1) {
            templist.remove(model);
            adapter.notifyDataSetChanged();
        }else{
            Utils.showToast(PayoutOrders.this,"This is the Last order ,If you want to delete this then delete payaout");
        }
    }
});
    }

    @Override
    public void onBackPressed() {
        if (templist.size() == list.size()) {
            finish();
        } else {
            Intent data = new Intent();
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    public void onSucess(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (pd != null)
                    pd.cancel();
                if (Utils.getStatus(value)) {
                    Utils.showToast(PayoutOrders.this, "Payout Updated Sucessfully.");
                } else {
                    Utils.showToast(PayoutOrders.this, Utils.getMessage(value));
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
                Utils.showToast(PayoutOrders.this, Utils.getMessage(value));
            }
        });
    }
}
