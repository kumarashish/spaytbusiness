package com.spaytbusiness;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import common.AppController;

public class Splash  extends Activity {
    private static int SPLASH_TIME_OUT = 1500;
    AppController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        controller=(AppController)getApplicationContext();
        if( (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)&&(Build.VERSION.SDK_INT <26) ){
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else if(Build.VERSION.SDK_INT >=26){
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }
        runThread();
    }

    public void runThread()
    {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                if(controller.getManager().isUserLoggedIn())
                {
                    startActivity(new Intent(Splash.this, Dashboard.class));
                }else {

                    startActivity(new Intent(Splash.this, Login.class));
                }
                    finish();

            }
        }, SPLASH_TIME_OUT);
    }
}