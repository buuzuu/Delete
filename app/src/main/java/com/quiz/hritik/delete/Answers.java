package com.quiz.hritik.delete;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quiz.hritik.delete.Adapter_ViewHolders.AfterQuizAnswerAdapter;
import com.quiz.hritik.delete.Model.Common;

public class Answers extends Fragment {
    private static final String TAG = "Answers";
    private View view;
    LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private TextView textView;
    private FirebaseDatabase database;
    private DatabaseReference questions;
    private AfterQuizAnswerAdapter adapter;
    public Answers() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.answers_fragment, container, false);
        recyclerView = view.findViewById(R.id.answersRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);

        textView = view.findViewById(R.id.msg);
        if (Common.answerAllowed.equals("true")) {
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.INVISIBLE);
        } else if (Common.answerAllowed.equals("false")) {
            recyclerView.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
        }

        adapter = new AfterQuizAnswerAdapter(getActivity(), Common.after_text_questions, Common.after_text_answers);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);






        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");

    }



}
