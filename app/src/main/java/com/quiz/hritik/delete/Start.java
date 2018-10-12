package com.quiz.hritik.delete;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.eyalbira.loadingdots.LoadingDots;
import com.quiz.hritik.delete.Model.Common;
import com.quiz.hritik.delete.Model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class Start extends AppCompatActivity {

    private static final String TAG = "Start";
    Button btnPLay;

    FirebaseDatabase database;
    DatabaseReference questions,category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Common.loadingDots = findViewById(R.id.dots);
        Common.relativeLayout = findViewById(R.id.rView);
        database=FirebaseDatabase.getInstance();
        questions=database.getReference("Questions");
       // category=database.getReference("Category").child("0"+String.valueOf(Common.categoryPosition+1)).getRef();


        Common.categoryId= database.getReference("Category").child("0"+String.valueOf(Common.categoryPosition+1)).getKey();


        Log.d(TAG, "onCreate: "+Common.categoryId);
        loadQuestions(Common.categoryId);

        btnPLay=findViewById(R.id.btnPlay);
        btnPLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Common.loadingDots.setVisibility(View.VISIBLE);
//                Common.relativeLayout.setBackgroundColor(getResources().getColor(android.R.color.background_dark));

                DatabaseReference isQuestionScore = database.getReference();
                final DatabaseReference playedStatus = database.getReference("hasPlayed");

                isQuestionScore.child("hasPlayed").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            playedStatus.child(String.format("%s_%s", Common.currentUser.getUserName(), Common.categoryId))
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {

                                                Snackbar.make(view, "Already Played the Quiz !", Snackbar.LENGTH_SHORT).show();
                                                Common.loadingDots.setVisibility(View.INVISIBLE);

                                            } else {

                                                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                                                bottomSheetDialog.show(getSupportFragmentManager(), "exampleBSheet");

                                                //        loadingDots.setVisibility(View.INVISIBLE);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                        } else {
                            Common.loadingDots.setVisibility(View.INVISIBLE);

                            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                            bottomSheetDialog.show(getSupportFragmentManager(), "exampleBSheet");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });




    }

    private void loadQuestions(String categoryId) {


        // first clear list if any data present

        if (Common.questionList.size()>0){
            Common.questionList.clear();
        }



        questions.orderByChild("categoryId").equalTo(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){

                    Question ques=postSnapshot.getValue(Question.class);
                    Common.questionList.add(ques);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //Random List

        Collections.shuffle(Common.questionList);


    }
}
