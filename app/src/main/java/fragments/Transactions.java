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
import android.widget.ProgressBar;

import com.spaytbusiness.NewTransaction;
import com.spaytbusiness.R;

import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import utils.Utils;

public class Transactions extends Fragment  implements WebApiResponseCallback{
    Button new_transaction;
    AppController controller;
    ProgressBar progress_bar,progress_bar2;
    int apiCall;
    int getApiCall=1;
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
        progress_bar=(ProgressBar)v.findViewById(R.id.progress_bar);

        new_transaction=(Button)v.findViewById(R.id.new_transaction);
        new_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),NewTransaction.class));
            }
        });
        ButterKnife.bind(getActivity());
        if(Utils.isNetworkAvailable(getActivity()))
        {
            apiCall=getApiCall;
            progress_bar.setVisibility(View.VISIBLE);
            controller.getWebApiCall().getDataCommon(Common.getBusinessTransactions,controller.getManager().getUserToken(),this);
        }

        return v;
    }

    @Override
    public void onSucess(final String value) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
              if( Utils.getStatus(value))
              {
                  Utils.showToast(getActivity(),Utils.getMessage(value));
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
}
