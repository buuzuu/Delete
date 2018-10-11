
package com.quiz.hritik.delete;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.quiz.hritik.delete.Model.Common;
import com.squareup.picasso.Picasso;

import java.util.Collections;

public class PLaying extends AppCompatActivity implements View.OnClickListener {

    final static long INTERVAL = 1000;
    private static final String TAG = "PLaying";
    static long TIMEOUT = 2000 + 59000;
    int progressValue = 0;
    CountDownTimer mCountDown;
    int index = 0, score = 0, thisQuestion = 0, totalQuestion, correctAnswer;

    ProgressBar progressBar;
    Button btnA, btnB, btnC, btnD;
    TextView textScore, txtQuestionNum, question_text;
    PhotoView question_image;
    Button clickedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Collections.shuffle(Common.questionList);
        setContentView(R.layout.activity_playing);
        //views
        textScore = findViewById(R.id.textScore);
        txtQuestionNum = findViewById(R.id.textTotalQuestion);
        question_text = findViewById(R.id.question_text);
        progressBar = findViewById(R.id.progressBar);
        question_image = findViewById(R.id.question_image);
        btnA = findViewById(R.id.btnAnswerA);
        btnB = findViewById(R.id.btnAnswerB);
        btnC = findViewById(R.id.btnAnswerC);
        btnD = findViewById(R.id.btnAnswerD);
        question_text.setMovementMethod(new ScrollingMovementMethod());
        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
        //        textScore.setVisibility(View.INVISIBLE);
        Log.d(TAG, "onCreate: ");

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        totalQuestion = Common.questionList.size();
        progressBar.setMax(totalQuestion);
        mCountDown = new CountDownTimer(TIMEOUT, INTERVAL) {
            @Override
            public void onTick(long minisec) {

                textScore.setText(String.valueOf(progressValue));
                // progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                Log.d(TAG, "onFinish: ");
                showQuestion(++index);
            }
        };
        Log.d(TAG, "onResume: B");
        showQuestion(index);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        thisQuestion = thisQuestion - 1;


    }

    @Override
    public void onBackPressed() {


        Toast.makeText(this, "You Can't Quit !", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View view) {
        mCountDown.cancel();
        if (index < totalQuestion)  // still have question in list
        {
            clickedButton = (Button) view;
            if (clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer())) {
                score += 1;
                correctAnswer++;
                Log.d(TAG, "onClick: " + clickedButton.getText());
                showQuestion(++index); // next question

            } else if (!clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer())) {
                showQuestion(++index);
            }
            //            textScore.setText(String.format("%d",score));

        }
        //        else {
        //
        //            Intent intent=new Intent(this,PLaying.class);
        //            Bundle dataSend=new Bundle();
        //            dataSend.putInt("SCORE",score);
        //            dataSend.putInt("TOTAL",totalQuestion);
        //            dataSend.putInt("CORRECT",correctAnswer);
        //            intent.putExtras(dataSend);
        //            startActivity(intent);
        //            finish();
        //        }


    }

    private void showQuestion(int index) {


        if (index < totalQuestion) {

            thisQuestion++;
            txtQuestionNum.setText(String.format("%d / %d", thisQuestion, totalQuestion));
            progressBar.setProgress(thisQuestion);
            // progressBar.setProgress(0);
            textScore.setText("0");
            progressValue = 0;


            if (Common.questionList.get(index).getIsImageQuestion().equals("true")) {


                //If is image
                Picasso.with(getBaseContext()).load(Common.questionList.get(index).getQuestion())
                        .into(question_image);
                question_image.setVisibility(View.VISIBLE);
                question_text.setVisibility(View.INVISIBLE);

            } else {
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


        } else {


            // if it is final question


            Intent intent = new Intent(this, Done.class);
            Bundle dataSend = new Bundle();
            mCountDown.cancel();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQuestion);
            dataSend.putInt("CORRECT", correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }

    }

}
