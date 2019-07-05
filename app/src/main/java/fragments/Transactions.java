package fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.spaytbusiness.AddItem;
import com.spaytbusiness.NewTransaction;
import com.spaytbusiness.R;

import javax.net.ssl.SSLEngineResult;

import adapter.TransactionAdapter;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import models.OutstandingOrder;
import utils.Utils;

public class Transactions extends Fragment  implements WebApiResponseCallback{
    Button new_transaction;
    AppController controller;
    ProgressBar progress_bar,progress_bar2;
    int apiCall;
    int getApiCall=1;
    ListView transactions;
    TextView noTransactions;
    OutstandingOrder model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        controller=(AppController)getActivity().getApplicationContext();
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.transactions, container, false);
        progress_bar=(ProgressBar)v.findViewById(R.id.progressbar);
        new_transaction=(Button)v.findViewById(R.id.new_transaction);
        transactions=(ListView)v.findViewById(R.id.listView);
        noTransactions=(TextView)v.findViewById(R.id.no_transactionView);
        new_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), AddItem.class),2);
            }
        });
        ButterKnife.bind(getActivity());
        refreshData();

        return v;
    }

    @Override
    public void onSucess(final String value) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
              if( Utils.getStatus(value))
              {

                  model= new Gson().fromJson(value,  OutstandingOrder.class);
                  setValue();
              }else{
              Utils.showToast(getActivity(),Utils.getMessage(value));
              }
                progress_bar.setVisibility(View.GONE);
            }
            });
            }


    @Override
    public void onError(String value) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress_bar.setVisibility(View.GONE);
            }
        });
    }

    public void setValue()
    {
       if(model.getOrderData().size()>0)
       {
           noTransactions.setVisibility(View.GONE);
           transactions.setVisibility(View.VISIBLE);
           transactions.setAdapter(new TransactionAdapter(getActivity(),model.getOrderData()));
       }
    }
public void refreshData()
{
    if(Utils.isNetworkAvailable(getActivity()))
    {
        apiCall=getApiCall;
        progress_bar.setVisibility(View.VISIBLE);
        controller.getWebApiCall().getDataCommon(Common.getBusinessOrders,controller.getManager().getUserToken(),this);
    }

}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==2)&&(resultCode== -1))
        {
            refreshData();

        }
    }
}
