package com.quiz.hritik.techconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.quiz.hritik.techconnect.Model.Common;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    private static final String TAG = "BottomSheetDialog";
    DatabaseReference played;
    private TextView textView;
    private View v;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Common.loadingDots.setVisibility(View.INVISIBLE);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.bottom_sheet, container, false);
        textView = v.findViewById(R.id.understand);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                played.child(String.format("%s_%s", Common.currentUser.getUserName(), Common.categoryId)).setValue("played");
                Intent intent = new Intent(v.getContext(), PLaying.class);
                startActivity(intent);
                getActivity().finish();


            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        played = database.getReference("hasPlayed");
    }
}