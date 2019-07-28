package com.spaytbusiness;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import adapter.PayoutAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.PayoutClickListners;
import interfaces.WebApiResponseCallback;
import models.PayoutModel;
import okhttp3.internal.Util;
import utils.Utils;

public class Payout extends Activity implements WebApiResponseCallback, View.OnClickListener, PayoutClickListners {
    AppController controller;
    @BindView(R.id.listView)
    ListView list;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.count)
    TextView count;

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
@BindView(R.id.create)
        ImageView createPayoutbutton;
    Dialog pd;
    int apiCall;
    int getPayout=1,createPayout=2,deletePayout=3;
    PayoutModel model;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payout);
        ButterKnife.bind(this);
        controller=(AppController)getApplicationContext();
        back.setOnClickListener(this);
        createPayoutbutton.setOnClickListener(this);
        pd= Utils.getProgressDailog(Payout.this);
        getOutStandingPayout();
    }

    public void getOutStandingPayout() {
        if (Utils.isNetworkAvailable(Payout.this)) {
            progressBar.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
            apiCall = getPayout;
            controller.getWebApiCall().getDataCommon(Common.getPayout, controller.getManager().getUserToken(), this);
        }
    }

    public void createPayout() {
        apiCall = createPayout;
        controller.getWebApiCall().getDataCommon(Common.createPayout, controller.getManager().getUserToken(), this);
        pd.show();
    }

    public void deletePayoutPayout(String id) {
        apiCall = deletePayout;
        controller.getWebApiCall().postData(Common.deletePayout, controller.getManager().getUserToken(), Common.payout_id, new String[]{id}, this);
        pd.show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.create:
                createPayout();
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
                                model = new Gson().fromJson(value, PayoutModel.class);
                                if (model.getPayoutsDetails().size() > 0) {
                                    progressBar.setVisibility(View.GONE);
                                    nodata.setVisibility(View.GONE);
                                    count.setText(Integer.toString(model.getPayoutsDetails().size()));
                                    list.setAdapter(new PayoutAdapter(Payout.this,model.getPayoutsDetails()));
                                    list.setVisibility(View.VISIBLE);
                                } else {
                                    count.setText("0");
                                    progressBar.setVisibility(View.GONE);
                                    nodata.setVisibility(View.VISIBLE);
                                    list.setVisibility(View.GONE);

                                }
                            } else {
                                progressBar.setVisibility(View.GONE);
                                nodata.setVisibility(View.VISIBLE);
                                list.setVisibility(View.GONE);
                                count.setText("0");
                                Utils.showToast(Payout.this, Utils.getMessage(value));
                            }
                            break;
                        case 2:
                            if (Utils.getStatus(value)) {
                                model = new Gson().fromJson(value, PayoutModel.class);
                                model.updatePayoutsDetails(new PayoutModel.PayoutsDetail(Utils.getJSONObject(value,"payout_details")));
                                if (model.getPayoutsDetails().size() > 0) {
                                    if (pd != null) {
                                        pd.cancel();
                                    }
                                    nodata.setVisibility(View.GONE);
                                    count.setText(Integer.toString(model.getPayoutsDetails().size()));
                                    list.setAdapter(new PayoutAdapter(Payout.this,model.getPayoutsDetails()));
                                    list.setVisibility(View.VISIBLE);
                                    Utils.showToast(Payout.this,"Payout Created sucessfully.");
                                } else {
                                    if (pd != null) {
                                        pd.cancel();
                                    }
                                    nodata.setVisibility(View.VISIBLE);
                                    list.setVisibility(View.GONE);
                                    Utils.showToast(Payout.this, Utils.getMessage(value));
                                }
                            } else {
                                if (pd != null) {
                                    pd.cancel();
                                }

                                Utils.showToast(Payout.this, Utils.getMessage(value));
                            }
                            break;
                        case 3:
                            if(Utils.getStatus(value))
                            {
                                if (pd != null) {
                                    pd.cancel();
                                }

                                Utils.showToast(Payout.this,"Payout deleted sucessfully.");
                                getOutStandingPayout();
                            }else{
                                if (pd != null) {
                                    pd.cancel();
                                }

                                Utils.showToast(Payout.this, Utils.getMessage(value));
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
                Utils.showToast(Payout.this,Utils.getMessage(value));
            }
        });
    }

    @Override
    public void onDeleteClick(final String orderId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                deletePayoutPayout(orderId);
            }
        });
    }

    @Override
    public void onDetailsClick(final List<PayoutModel.PayoutConsumerOrder> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PayoutOrders.list = list;
                startActivity(new Intent(Payout.this, PayoutOrders.class));
            }
        });

    }
}
