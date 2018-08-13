package com.example.hritik.delete;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hritik.delete.Model.Common;
import com.example.hritik.delete.Model.Question;
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
        database=FirebaseDatabase.getInstance();
        questions=database.getReference("Questions");
       // category=database.getReference("Category").child("0"+String.valueOf(Common.categoryPosition+1)).getRef();
        Common.categoryId= database.getReference("Category").child("0"+String.valueOf(Common.categoryPosition+1)).getKey();
        Log.d(TAG, "onCreate: "+Common.categoryId);
        loadQuestions(Common.categoryId);

        btnPLay=findViewById(R.id.btnPlay);
        btnPLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Start.this,PLaying.class);
                startActivity(intent);
                finish();
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
