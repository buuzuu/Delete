package com.example.hritik.delete;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hritik.delete.BrodCastReciiever.AlarmReciever;
import com.example.hritik.delete.Model.Common;
import com.example.hritik.delete.Model.User;
import com.github.florent37.materialtextfield.MaterialTextField;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;
import java.util.Iterator;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{

    MaterialEditText edtNewUser, edtNewPassword, edtNewEmail;  //for sign up
   // MaterialEditText edtUser, edtPassword; // for sign in
    Button  logIn;
    TextView btnSignUp;
    MaterialTextField edtUser,edtPassword;

    FirebaseDatabase database;
    DatabaseReference users;
    private static final String TAG = "Main2Activity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // Firebase
//        if(getIntent().getExtras()!=null){
//            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
//        }
      //  registerAlarm();


        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        final DatabaseReference category=firebaseDatabase.getReference("Category");
        category.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> iterable=dataSnapshot.getChildren().iterator();
                Log.d(TAG, "onDataChange: Total users "+dataSnapshot.getChildrenCount());
                while (iterable.hasNext()){


                    DataSnapshot tempItem=iterable.next();
                    Common.names.add(tempItem.child("Name").getValue().toString());
                    Common.imageUrl.add(tempItem.child("Image").getValue().toString());
                }

              //  Log.d(TAG, "onDataChange: "+names.toString());
//
//                for (String c:names){
//
//                    Log.d(TAG, "onDataChange: "+c.toString());
//
//                }
//                for (String c:imageUrl){
//
//                    Log.d(TAG, "onDataChange: "+c.toString());
//
//                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPassword);


        btnSignUp = findViewById(R.id.log_up);
        logIn = (Button) findViewById(R.id.log_in);
        logIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
;


//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showSignUpDialog();
//            }
//        });
//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn(edtUser.getText().toString(), edtPassword.getText().toString());
//            }
//        });


    }

    private void registerAlarm() {


        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,14);  // 9 HOUR
        calendar.set(Calendar.MINUTE,51);
        calendar.set(Calendar.SECOND,05);

        Intent intent=new Intent(Main2Activity.this, AlarmReciever.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(Main2Activity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager=(AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);


    }

    private void signIn(final String user, final String pwd) {

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user).exists()) {

                    if (!user.isEmpty()) {

                        User login = dataSnapshot.child(user).getValue(User.class);

                        if (login.getPassword().equals(pwd)) {
                            Toast.makeText(Main2Activity.this, "Login Ok !", Toast.LENGTH_SHORT).show();
                            Common.currentUser=login;
                            startActivity(new Intent(Main2Activity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(Main2Activity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(Main2Activity.this, "Please enter your user name", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(Main2Activity.this, "User does not exist ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void showSignUpDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Main2Activity.this);
        alertDialog.setTitle("Sign Up");
        alertDialog.setMessage("Please fill full information");
        LayoutInflater inflater = this.getLayoutInflater();
        View sign_up_layout = inflater.inflate(R.layout.sign_up_layout, null);
        edtNewUser = sign_up_layout.findViewById(R.id.edtNewUserName);
        edtNewPassword = sign_up_layout.findViewById(R.id.edtNewPassword);
        edtNewEmail = sign_up_layout.findViewById(R.id.edtNewEmail);
        alertDialog.setView(sign_up_layout);
        alertDialog.setIcon(R.drawable.ic_account);
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final User user = new User(edtNewUser.getText().toString(), edtNewPassword.getText().toString(), edtNewEmail.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(user.getUserName()).exists()) {
                            Toast.makeText(Main2Activity.this, "User Already exists !", Toast.LENGTH_SHORT).show();
                        } else {
                            users.child(user.getUserName()).setValue(user);
                            Toast.makeText(Main2Activity.this, "Registration Successful !", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });
                dialogInterface.dismiss();
            }
        });


        alertDialog.show();


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.log_in:{


                signIn(edtUser.getEditText().getText().toString(), edtPassword.getEditText().getText().toString());

                break;
            }
            case R.id.log_up:{

                showSignUpDialog();


                break;
            }

        }

    }
//    public List<String> getMyNames() {
//        return Common.names;
//    }
//
//    public List<String> getImageUrl() {
//        return Common.imageUrl;
//    }
}
