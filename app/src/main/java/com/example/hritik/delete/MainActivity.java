package com.example.hritik.delete;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hritik.delete.Model.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity-Dick Head";
    BroadcastReceiver mRegistrationBroadcastReciever;

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReciever);
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReciever,new IntentFilter("registrationComplete"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReciever,new IntentFilter(Common.STR_PUSH));

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    break;


                case R.id.navigation_dashboard:

                    selectedFragment = new DashBoard();
                    break;


            }
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.sliding_in_left, R.anim.sliding_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right )
                    .replace(R.id.frame_layout,selectedFragment).commit();
        return  true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerNotification();




        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        final DatabaseReference ranking=firebaseDatabase.getReference("Ranking");
        ranking.orderByChild("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> iterable=dataSnapshot.getChildren().iterator();
                Log.d(TAG, "onDataChange: Total users "+dataSnapshot.getChildrenCount());
                Common.ranking_names.clear();
                while (iterable.hasNext()){


                    DataSnapshot tempItem=iterable.next();

                    Common.ranking_names.add(tempItem.child("userName").getValue().toString());

                }

                Log.d(TAG, "onDataChange: "+Common.ranking_names.toString());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new DashBoard()).commit();



    }

    private void registerNotification() {

        mRegistrationBroadcastReciever=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Common.STR_PUSH)){

                    String msg=intent.getStringExtra("message");
                    showNotification("Quiz",msg);

                }

            }
        };
    }

    private void showNotification(String title, String msg) {


        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent contectIntent=PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(msg)
                .setContentIntent(contectIntent);

        NotificationManager notificationManager=(NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(new Random().nextInt(),builder.build());








    }

}
