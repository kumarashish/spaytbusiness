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

import com.spaytbusiness.NewTransaction;
import com.spaytbusiness.R;

import common.AppController;

public class Transactions extends Fragment {
    Button new_transaction;
    AppController controller;
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
        new_transaction=(Button)v.findViewById(R.id.new_transaction);
        new_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),NewTransaction.class));
            }
        });


        return v;
    }
}
