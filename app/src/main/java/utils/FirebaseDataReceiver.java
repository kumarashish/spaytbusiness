package utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;

import com.spaytbusiness.Dashboard;
import com.spaytbusiness.Login;
import com.spaytbusiness.R;
import com.spaytbusiness.Splash;

import common.AppController;

public class FirebaseDataReceiver extends BroadcastReceiver {

    private final String TAG = "FirebaseDataReceiver";
    AppController controller;

    public void onReceive(Context context, Intent intent) {
        controller=(AppController)context.getApplicationContext();
        String dataBundle = intent.getStringExtra("data");
        String title=intent.getStringExtra("heading");
        if(dataBundle!=null) {
             Log.d(TAG, dataBundle);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sendNotification(title, dataBundle, context);

            }else {
                showNotification(title, dataBundle, context);
            }
           ;
        }
    }

    private void sendNotification(String title,String message,Context context) {
        int notifyID = 023444455;


        String CHANNEL_ID = "my_channel_01";
        Intent intent;
        if (controller.getManager().isUserLoggedIn()) {
            intent = new Intent(context, Dashboard.class);
        } else {
           intent = new Intent(context, Splash.class);
        }// The id of the channel.

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "01")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.logo)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setChannelId(CHANNEL_ID)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {       // For Oreo and greater than it, we required Notification Channel.
            CharSequence name = "My New Channel";                   // The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name, importance); //Create Notification Channel
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(notifyID /* ID of notification */, notificationBuilder.build());
    }
    public void showNotification(String title, String message, Context context) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent;
        if (controller.getManager().isUserLoggedIn()) {
            notificationIntent = new Intent(context, Dashboard.class);
        } else {
            notificationIntent = new Intent(context, Splash.class);
        }
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 123, notificationIntent, 0);
        Builder mBuilder = new Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(message)
                .setOnlyAlertOnce(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        mBuilder.setAutoCancel(true);
        mBuilder.setLocalOnly(false);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(123, mBuilder.build());

    }
}