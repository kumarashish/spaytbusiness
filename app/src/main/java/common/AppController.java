package common;

import android.app.Application;

import utils.WebApiCall;

public class AppController extends Application {
    AppController controller;
    WebApiCall webApiCall;
    @Override
    public void onCreate() {
        super.onCreate();
        controller=this;
        webApiCall=new WebApiCall(getApplicationContext());
    }

    public AppController getController() {
        return controller;
    }

    public WebApiCall getWebApiCall() {
        return webApiCall;
    }
}
