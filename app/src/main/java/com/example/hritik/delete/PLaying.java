package com.example.hritik.delete;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hritik.delete.Model.Common;
import com.squareup.picasso.Picasso;

public class PLaying extends AppCompatActivity implements View.OnClickListener{

    final static long INTERVAL=1000;
    final static long TIMEOUT=9000;
    int progressValue=0;
    CountDownTimer mCountDown;

    int index=0,score=0,thisQuestion=0,totalQuestion,correctAnswer;

    //Firebase

//    FirebaseDatabase database;
//    DatabaseReference question;
    ProgressBar progressBar;
    Button btnA,btnB,btnC,btnD;
    TextView textScore,txtQuestionNum,question_text;
    ImageView question_image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        // firebase
//
//        database=FirebaseDatabase.getInstance();
//        question=database.getReference("Questions");

        //views

        textScore=findViewById(R.id.textScore);
        txtQuestionNum=findViewById(R.id.textTotalQuestion);
        question_text=findViewById(R.id.question_text);
        progressBar=findViewById(R.id.progressBar);
        question_image=findViewById(R.id.question_image);
        btnA=findViewById(R.id.btnAnswerA);
        btnB=findViewById(R.id.btnAnswerB);
        btnC=findViewById(R.id.btnAnswerC);
        btnD=findViewById(R.id.btnAnswerD);
        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);






    }

    @Override
    public void onClick(View view) {

        mCountDown.cancel();
        if (index<totalQuestion)  // still have question in list
        {
            Button clickedButton=(Button)view;
            if (clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer())){

                score+=10;
                correctAnswer++;
                showQuestion(++index); // next question

            }else if (!clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer())){
                showQuestion(++index);
            }
//            else {
//                //choose wrong answer
//
//                Intent intent=new Intent(this,PLaying.class);
//                Bundle dataSend=new Bundle();
//                dataSend.putInt("SCORE",score);
//                dataSend.putInt("TOTAL",totalQuestion);
//                dataSend.putInt("CORRECT",correctAnswer);
//                intent.putExtras(dataSend);
//                startActivity(intent);
//                finish();
//
//            }

            textScore.setText(String.format("%d",score));






        }else {

            Intent intent=new Intent(this,PLaying.class);
            Bundle dataSend=new Bundle();
            dataSend.putInt("SCORE",score);
            dataSend.putInt("TOTAL",totalQuestion);
            dataSend.putInt("CORRECT",correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }

    }

    private void showQuestion(int index)    {


        if (index<totalQuestion){

            thisQuestion++;
            txtQuestionNum.setText(String.format("%d / %d",thisQuestion,totalQuestion));
            progressBar.setProgress(0);
            progressValue=0;


            if (Common.questionList.get(index).getIsImageQuestion().equals("true")){


                //If is image
                Picasso.with(getBaseContext()).load(Common.questionList.get(index).getQuestion())
                        .into(question_image);
                question_image.setVisibility(View.VISIBLE);
                question_text.setVisibility(View.INVISIBLE);

            }else {
                question_text.setText(Common.questionList.get(index).getQuestion());
                // IF QUESTION IS JUST TEXT ..SET IMAGE TO INVISIBLE
                question_image.setVisibility(View.INVISIBLE);
                question_text.setVisibility(View.VISIBLE);

            }

            btnA.setText(Common.questionList.get(index).getAnswerA());
            btnB.setText(Common.questionList.get(index).getAnswerB());
            btnC.setText(Common.questionList.get(index).getAnswerC());
            btnD.setText(Common.questionList.get(index).getAnswerD());

            mCountDown.start();


        }
        else {

             // if it is final question

            Intent intent=new Intent(this,Done.class);
            Bundle dataSend=new Bundle();
            dataSend.putInt("SCORE",score);
            dataSend.putInt("TOTAL",totalQuestion);
            dataSend.putInt("CORRECT",correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }






    }


    @Override
    protected void onResume() {
        super.onResume();

        totalQuestion=Common.questionList.size();
        mCountDown=new CountDownTimer(TIMEOUT,INTERVAL) {
            @Override
            public void onTick(long minisec) {

            progressBar.setProgress(progressValue);
            progressValue++;


            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                showQuestion(++index);
            }
        };
        showQuestion(index);



    }
}
