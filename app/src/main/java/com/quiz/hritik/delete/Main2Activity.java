package com.quiz.hritik.delete;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.eyalbira.loadingdots.LoadingDots;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.quiz.hritik.delete.BrodCastReciiever.AlarmReciever;
import com.quiz.hritik.delete.Model.Common;
import com.quiz.hritik.delete.Model.User;
import com.github.florent37.materialtextfield.MaterialTextField;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    boolean check = false;
    AlertDialog dialog;
    EditText edtNewUser, edtNewFirstName, edtNewLastName, edtNewPassword, edtNewEmail, edtNewPhone, edtNewOTP;  //for sign up
    // MaterialEditText edtUser, edtPassword; // for sign in
    Button logIn, getOTP;
    Button btnSignUp;
    EditText edtUser, edtPassword;
    private String verificationId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    FirebaseDatabase database;
    DatabaseReference users;
    private static final String TAG = "Main2Activity";
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                edtNewOTP.setText(code);
                getOTP.setClickable(false);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Main2Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.log_in: {

                signIn(edtUser.getText().toString(), edtPassword.getText().toString());

                break;
            }
            case R.id.log_up: {

                showSignUpDialog();


                break;
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();
        View decorView = getWindow().getDecorView();
        int uiOption1 = View.SYSTEM_UI_FLAG_FULLSCREEN;
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        decorView.setSystemUiVisibility(uiOption1);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference category = firebaseDatabase.getReference("Category");
        category.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> iterable = dataSnapshot.getChildren().iterator();
                Log.d(TAG, "onDataChange: Total users " + dataSnapshot.getChildrenCount());
                while (iterable.hasNext()) {


                    DataSnapshot tempItem = iterable.next();
                    Common.names.add(tempItem.child("Name").getValue().toString());
                    Common.imageUrl.add(tempItem.child("Image").getValue().toString());
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        Common.new_question_score = database.getReference("Question_Score");
        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPassword);


        btnSignUp = findViewById(R.id.log_up);
        logIn = (Button) findViewById(R.id.log_in);
        logIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);


    }

    private void registerAlarm() {


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 14);  // 9 HOUR
        calendar.set(Calendar.MINUTE, 51);
        calendar.set(Calendar.SECOND, 05);

        Intent intent = new Intent(Main2Activity.this, AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(Main2Activity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


    }

    private void signIn(final String user, final String pwd) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Logging in...");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user).exists()) {

                    if (!user.isEmpty()) {

                        User login = dataSnapshot.child(user).getValue(User.class);

                        if (login.getPassword().equals(pwd)) {

                            Common.currentUser = login;
                            dialog.dismiss();
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

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Main2Activity.this);
        //     alertDialog.setTitle("Sign Up");
        //     alertDialog.setMessage("Please fill full information");
        LayoutInflater inflater = this.getLayoutInflater();
        final View sign_up_layout = inflater.inflate(R.layout.sign_up_layout, null);
        edtNewUser = sign_up_layout.findViewById(R.id.edtNewUserName);
        edtNewPassword = sign_up_layout.findViewById(R.id.edtNewPassword);
        edtNewEmail = sign_up_layout.findViewById(R.id.edtNewEmail);
        edtNewFirstName = sign_up_layout.findViewById(R.id.edtNewFirstName);
        edtNewLastName = sign_up_layout.findViewById(R.id.edtNewLastName);
        edtNewPhone = sign_up_layout.findViewById(R.id.editNewPhone);
        edtNewOTP = sign_up_layout.findViewById(R.id.edtNewOTP);
        progressBar = sign_up_layout.findViewById(R.id.progressBar2);

        edtNewOTP.setVisibility(View.INVISIBLE);
        checkforRegisteredNumber();
        getOTP = sign_up_layout.findViewById(R.id.getOTP);
        alertDialog.setView(sign_up_layout);
        //     alertDialog.setIcon(R.drawable.ic_account);


        getOTP.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                String number = "+91" + edtNewPhone.getText().toString().trim();
                Log.d(TAG, "onClick: " + check);
                for (int i = 0; i < Common.registederPhone.size(); i++) {

                    Log.d(TAG, "onDataChange: Test two..." + Common.registederPhone.get(i) + "---" + Common.registederUsername.get(i));

                    if (edtNewPhone.getText().toString().equals(Common.registederPhone.get(i)) || edtNewUser.getText().toString().equals(Common.registederUsername.get(i))) {
                        check = true;
                    }

                }
                Log.d(TAG, "onClick: " + check);

                if (check == true) {
                    Snackbar.make(sign_up_layout, "Phone Number Or Username Already Registered !", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(Main2Activity.this, "Phone Number Or Username Already Registered !", Toast.LENGTH_SHORT).show();
                    check = false;
                    Common.registederPhone.clear();
                    Common.registederUsername.clear();
                    dialog.dismiss();
                } else if (check == false) {
                    Common.registederPhone.clear();
                    Common.registederUsername.clear();
                    edtNewOTP.setVisibility(View.VISIBLE);
                    sendVerificationCode(number);
                    progressBar.setVisibility(View.VISIBLE);

                }

            }
        });
        dialog = alertDialog.create();
        dialog.show();
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    final User user = new User(edtNewUser.getText().toString(), edtNewPassword.getText().toString(), edtNewEmail.getText().toString()

                            , edtNewFirstName.getText().toString(), edtNewLastName.getText().toString(), edtNewPhone.getText().toString());


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            getOTP.setBackgroundColor(Color.parseColor("#9D405B"));
                            getOTP.setTextColor(Color.WHITE);
                            getOTP.setText("Registered !!!");

                            progressBar.setVisibility(View.INVISIBLE);
                            users.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.child(user.getUserName()).exists()) {
                                        Toast.makeText(Main2Activity.this, "Username Already exists !", Toast.LENGTH_SHORT).show();
                                    } else {
                                        users.child(user.getUserName()).setValue(user);

                                        Toast.makeText(Main2Activity.this, "Registration Successful !", Toast.LENGTH_LONG).show();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                            }
                                        }, 1500);
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {


                                }
                            });


                        } else {
                            Toast.makeText(Main2Activity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void sendVerificationCode(String number) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    private void checkforRegisteredNumber() {

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {


                    Log.d(TAG, "onDataChange: " + dataSnapshot.getChildrenCount());
                    Iterator<DataSnapshot> iterable = dataSnapshot.getChildren().iterator();

                    while (iterable.hasNext()) {


                        DataSnapshot tempItem = iterable.next();
                        Common.registederPhone.add(tempItem.child("phoneNumber").getValue().toString());
                        Common.registederUsername.add(tempItem.child("userName").getValue().toString());
                    }

                } else {
                    check = false;
                }


                for (int i = 0; i < Common.registederPhone.size(); i++) {
                    Log.d(TAG, "onDataChange: Test one...." + Common.registederPhone.get(i) + "-----" + Common.registederUsername.get(i));
                }
                Log.d(TAG, "onDataChange: Test one..." + check);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
