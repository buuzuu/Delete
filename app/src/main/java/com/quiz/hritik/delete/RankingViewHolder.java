package com.quiz.hritik.delete;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.quiz.hritik.delete.Adapter_ViewHolders.ScoreDetailAdapter;
import com.quiz.hritik.delete.Adapter_ViewHolders.ScoreDetailViewHolder;
import com.quiz.hritik.delete.Model.Common;
import com.quiz.hritik.delete.Model.QuestionScore;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "RankingViewHolder";
    public String name[];
    TextView txt_name, txt_score;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    ScoreDetailAdapter scoreDetailAdapter;
    RecyclerView recyclerView;


    List<QuestionScore> questionScoreList = new ArrayList<>();

    public RankingViewHolder(final View itemView) {
        super(itemView);
        txt_name = itemView.findViewById(R.id.txt_name);
        txt_score = itemView.findViewById(R.id.txt_score);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.loadingDots2.setVisibility(View.VISIBLE);
                final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                String usrname = Common.ranking_names.get(getAdapterPosition());
                Common.new_question_score.orderByChild("user").equalTo(usrname).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                            questionScoreList.add(dataSnapshot1.getValue(QuestionScore.class));
                        }
                        name = new String[questionScoreList.size()];
                        for (int i = 0; i < questionScoreList.size(); i++) {
                            name[i] = questionScoreList.get(i).getCategoryName();
                        }
//

                        createPopup(questionScoreList);
                        progressDialog.dismiss();
//                        Toast.makeText(itemView.getContext(), "Size is-"+questionScoreList.size(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                questionScoreList.clear();

            }
        });

    }

    private void createPopup(List<QuestionScore> questionScoreList) {

        builder = new AlertDialog.Builder(itemView.getContext());
        View view = LayoutInflater.from(itemView.getContext()).inflate(R.layout.score_detail_popup, null);
        builder.setView(view);
        recyclerView = view.findViewById(R.id.scoreDetailrv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        scoreDetailAdapter = new ScoreDetailAdapter(itemView.getContext(), questionScoreList);

        recyclerView.setAdapter(scoreDetailAdapter);
        scoreDetailAdapter.notifyDataSetChanged();


        // listView=view.findViewById(R.id.lv);
//        arrayAdapter=new ArrayAdapter(itemView.getContext(), android.R.layout.simple_list_item_1,
//                                android.R.id.text1, name);
//                        listView.setAdapter(arrayAdapter);
        alertDialog = builder.create();
        Common.loadingDots2.setVisibility(View.INVISIBLE);
        alertDialog.show();


    }


    @Override
    public void onClick(View view) {


//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//              //  Toast.makeText(itemView.getContext(),"Clicked on "+ Common.ranking_names.get(getAdapterPosition()),Toast.LENGTH_SHORT).show();
//                String usrname=Common.ranking_names.get(getAdapterPosition());
//
//
//
//                Common.new_question_score.orderByChild("user").equalTo(usrname).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
//
//                                questionScoreList.add(dataSnapshot1.getValue(QuestionScore.class));
//                            }
//
//                        Toast.makeText(itemView.getContext(), "Size is-"+questionScoreList.size(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                questionScoreList.clear();
//
//
//
//
////                Intent scoreDetail=new Intent(itemView.getContext(),ScoreDetail.class);
////
////                scoreDetail.putExtra("viewUser",usrname);
////                itemView.getContext().startActivity(scoreDetail);
//
//
//            }
//        });


    }
}
