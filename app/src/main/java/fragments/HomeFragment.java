package fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.spaytbusiness.BusinessOffersDetails;
import com.spaytbusiness.R;

import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import utils.Utils;

public class HomeFragment extends Fragment implements WebApiResponseCallback, View.OnClickListener {

AppController controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller=(AppController)getActivity().getApplicationContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.offers, container, false);


        return v;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSucess(String value) {

    }

    @Override
    public void onError(String value) {

    }
}
