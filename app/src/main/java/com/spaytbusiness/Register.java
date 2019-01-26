package com.spaytbusiness;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import models.CategoryModel;
import utils.Utils;

public class Register extends Activity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.signUp)
    Button signup;
    @BindView(R.id.categories)
    Spinner categories;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.mainView)
    LinearLayout mainView;
    AppController controller;
    ArrayList<CategoryModel>categorylist=new ArrayList<>();
    ArrayList<String>categorylistName=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        controller=(AppController)getApplicationContext();
//        if( (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)&&(Build.VERSION.SDK_INT <26) ){
//            Window w = getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }else if(Build.VERSION.SDK_INT >=26){
//            Window w = getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//
//        }
        ButterKnife.bind(this);
        signup.setOnClickListener(this);
        back.setOnClickListener(this);

        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView child=(TextView) parent.getChildAt(0);
                child.setTextColor(Color.WHITE);
                child.setTextSize(18);
                child.setTypeface(getResources().getFont(R.font.light));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(Utils.isNetworkAvailable(Register.this))
        {
            progressBar.setVisibility(View.VISIBLE);

            Thread t=new Thread(new Runnable() {
                @Override
                public void run() {
              String result=controller.getWebApiCall().getData(Common.getCategories);
              if(result!=null)
              {if(Utils.getStatus(result)==true)
                  {
                      JSONArray jsonArray=Utils.getJSONArray(result);
                      for(int i=0;i<jsonArray.length();i++)
                      {try {
                          JSONObject jsonObject = jsonArray.getJSONObject(i);
                          CategoryModel model=new CategoryModel(jsonObject);
                          categorylist.add(model);
                          categorylistName.add(model.getCategoryName());
                      }catch (Exception ex)
                      {
                          ex.fillInStackTrace();
                      }

                      }
                      if(categorylistName.size()>0)
                      {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                categories.setAdapter(new ArrayAdapter<String>(Register.this,
                                        android.R.layout.simple_spinner_item,categorylistName));
                                progressBar.setVisibility(View.GONE);
                                mainView.setVisibility(View.VISIBLE);
                            }
                        });
                      }
                  }

              }

                }
            });
            t.start();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back:
                finish();
                break;


            case R.id.signUp:
                Toast.makeText(Register.this, "Registered Sucessfully", Toast.LENGTH_SHORT).show();

                finish();
                break;
        }

    }
}