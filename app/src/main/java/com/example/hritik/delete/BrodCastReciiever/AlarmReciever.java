package com.example.hritik.delete.BrodCastReciiever;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.media.app.NotificationCompat;

import com.example.hritik.delete.Main2Activity;
import com.example.hritik.delete.MainActivity;
import com.example.hritik.delete.R;

public class AlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notifyIntent=new Intent(context, Main2Activity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        android.support.v4.app.NotificationCompat.Builder builder=new android.support.v4.app.NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Online Quiz App")
                .setContentText("Hey ! Try solving questions today.")
                .setAutoCancel(true)
                .setWhen(when)

                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000,1000,1000,1000,1000});

        notificationManager.notify(0,builder.build());

    }
}
