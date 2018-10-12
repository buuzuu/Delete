package com.quiz.hritik.delete.Model;


import android.support.constraint.ConstraintLayout;
import android.widget.RelativeLayout;

import com.eyalbira.loadingdots.LoadingDots;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class Common {


    public static  String categoryId,categoryName;
    public static User currentUser;
    public static int categoryPosition;
    public static List<Question> questionList=new ArrayList<>();
    public static List<String> imageUrl = new ArrayList<>();
    public static  List<String> names=new ArrayList<>();
    public static List<String> ranking_names=new ArrayList<>();
    public static final String STR_PUSH="pushNotification";
    public static DatabaseReference new_question_score;
    public static List<String> registederPhone = new ArrayList<>();
    public static List<String> registederUsername = new ArrayList<>();
    public static LoadingDots loadingDots;
    public static LoadingDots loadingDots2;
    public static ConstraintLayout relativeLayout;
    public static int Start_Hour;
    public static int Start_Month;
    public static int Start_Date;
    public static int Start_Minute;
    public static int End_Hour;
    public static int End_Minute;
    public static int End_Date;
    public static int End_Month;
    public static List<String> after_text_questions = new ArrayList<>();
    public static List<String> after_text_answers = new ArrayList<>();
    public static String answerAllowed;

}


