package com.quiz.hritik.delete;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;

import com.quiz.hritik.delete.Interface.ItemClickListener;
import com.quiz.hritik.delete.Interface.RankingCallBack;
import com.quiz.hritik.delete.Model.Common;
import com.quiz.hritik.delete.Model.QuestionScore;
import com.quiz.hritik.delete.Model.Ranking;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeFragment extends Fragment {

    //private ListView mylistView;
    private static final String TAG = "HomeFragment";
    private View myfragment;

    Toolbar toolbar;
    RecyclerView rankingList;
    LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<Ranking, RankingViewHolder> adapter;
    private FirebaseDatabase database;
    int sum = 0;
    private DatabaseReference questionScore, rankingTable;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        questionScore = database.getReference("Question_Score");
        rankingTable = database.getReference("Ranking");

        // setSupportActionBar(toolbar);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myfragment = inflater.inflate(R.layout.fragment_home, container, false);
        toolbar = myfragment.findViewById(R.id.toolbar);
        rankingList = myfragment.findViewById(R.id.rankingList);
        Common.loadingDots2 = myfragment.findViewById(R.id.dots2);
        layoutManager = new LinearLayoutManager(getActivity());
        rankingList.setHasFixedSize(true);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rankingList.setLayoutManager(layoutManager);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);



        updateScore(Common.currentUser.getUserName(), new RankingCallBack<Ranking>() {
            @Override
            public void callBack(Ranking ranking) {
                rankingTable.child(ranking.getUserName()).setValue(ranking);
                //  showRanking();
            }
        }, Common.currentUser.getFirstname());


        adapter = new FirebaseRecyclerAdapter<Ranking, RankingViewHolder>(
                Ranking.class, R.layout.layout_ranking, RankingViewHolder.class, rankingTable.orderByChild("score")
        ) {
            @Override
            protected void populateViewHolder(RankingViewHolder viewHolder, Ranking model, int position) {
                viewHolder.txt_name.setText("(" + model.getFirstname() + ") " + model.getUserName());
                viewHolder.txt_score.setText(String.valueOf(model.getScore()));
            }
        };
        adapter.notifyDataSetChanged();
        rankingList.setAdapter(adapter);

        return myfragment;
    }

    private void showRanking() {


        rankingTable.orderByChild("score").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    Ranking local = data.getValue(Ranking.class);
                    Log.d(TAG, "onDataChange: " + local.getUserName());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void updateScore(final String userName, final RankingCallBack<Ranking> callBack, final String firstname) {

        questionScore.orderByChild("user").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    QuestionScore ques = data.getValue(QuestionScore.class);
                    sum += Integer.parseInt(ques.getScore());
                }
                Ranking ranking = new Ranking(userName, sum, firstname);
                callBack.callBack(ranking);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



}
