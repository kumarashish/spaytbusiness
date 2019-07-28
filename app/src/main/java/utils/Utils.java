package utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.spaytbusiness.Dashboard;
import com.spaytbusiness.Login;
import com.spaytbusiness.MyCart;
import com.spaytbusiness.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;




/**
 * Created by ashish.kumar on 22-10-2018.
 */

public class Utils {
    public static boolean isNetworkAvailable(Activity act) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if ((activeNetworkInfo != null) && (activeNetworkInfo.isConnected())) {
            return true;
        } else {
            Toast.makeText(act, "Internet Unavailable", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static String getDeviceID(Activity act) {
        String deviceId = "";
        deviceId = Settings.Secure.getString(act.getContentResolver(), Settings.Secure.ANDROID_ID);
        return deviceId;
    }
    public static boolean getStatus(String data) {
        boolean status = false;
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("code");
            if (code == 200) {
                return true;
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return status;
    }
    public static void showToast(final Activity act, final String message) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(act, message, Toast.LENGTH_LONG).show();
            }
        });

    }
    public static String getMessage(String data) {
        String message = "";
        try {
            JSONObject jsonObject = new JSONObject(data);
            message = jsonObject.getString("msg");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return message;
    }

    public static JSONArray getJSONArray(String value)
    {
        try{
            JSONObject jsonObject=new JSONObject(value);
            JSONArray jsonArray=jsonObject.isNull("businesscategories")?jsonObject.getJSONArray("productcategories"):jsonObject.getJSONArray("businesscategories");

            return jsonArray;
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        return null;
    }
    public static JSONArray getJSONArray(String value,String key)
    {
        try{
            JSONObject jsonObject=new JSONObject(value);
            JSONArray jsonArray=jsonObject.getJSONArray(key);
            return jsonArray;
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        return null;
    }



public static JSONArray getJSonArray(String value)
{
    try{
        JSONArray jsonArray=new JSONArray(value);
        return jsonArray;
    }catch (Exception ex)
    {
        ex.fillInStackTrace();
    }
    return null;
}


    public static JSONObject getJSONObject(String value,String key)
    {
        try{
            JSONObject jsonObject=new JSONObject(value);

            return jsonObject.getJSONObject(key);
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        return null;
    }

    public static void logout(final Activity act) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(act, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                act.startActivity(intent);
            }

        });

    }


    public static AlertDialog getProgressDailog(Activity act)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.progress_dialog);
        AlertDialog dialog = builder.create();
        return dialog;
    }
    public  static void sucessAlert(final Activity act,final String orderId)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        LayoutInflater inflater = act.getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.sucess_alert, null);
        TextView caseId_TV=(TextView)dialogLayout.findViewById(R.id.caseId);
        caseId_TV.setText("Order Id | "+orderId+" ");
        builder.setView(dialogLayout);
        final Button done=(Button) dialogLayout.findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(act, Dashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                act.startActivity(intent);
                act.finish();

            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
