package com.quiz.hritik.delete.Adapter_ViewHolders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quiz.hritik.delete.Model.QuestionScore;
import com.quiz.hritik.delete.R;

import java.util.List;

public class ScoreDetailAdapter extends RecyclerView.Adapter<ScoreDetailAdapter.ViewHolder> {


    private Context context;
    private List<QuestionScore> questionScoreList;

    public ScoreDetailAdapter(Context context, List<QuestionScore> questionScoreList) {
        this.context = context;
        this.questionScoreList = questionScoreList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_detail_layout, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_name.setText(questionScoreList.get(position).getCategoryName());
        holder.txt_score.setText(questionScoreList.get(position).getScore());


    }

    @Override
    public int getItemCount() {
        return questionScoreList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name, txt_score;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name2);
            txt_score = itemView.findViewById(R.id.txt_score2);
        }
    }
}
