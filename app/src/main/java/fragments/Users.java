package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spaytbusiness.R;

import common.AppController;
import interfaces.WebApiResponseCallback;

/**
 * Created by ashish.kumar on 19-02-2019.
 */

public class Users extends Fragment implements WebApiResponseCallback{
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
        return inflater.inflate(R.layout.offers, container, false);
    }

    @Override
    public void onSucess(String value) {

    }

    @Override
    public void onError(String value) {

    }
}
