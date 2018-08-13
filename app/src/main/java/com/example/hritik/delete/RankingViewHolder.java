package com.example.hritik.delete;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hritik.delete.Model.Common;

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView txt_name,txt_score;


    public RankingViewHolder(View itemView) {
        super(itemView);
        txt_name=itemView.findViewById(R.id.txt_name);
        txt_score=itemView.findViewById(R.id.txt_score);
        itemView.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(itemView.getContext(),"Clicked on "+ Common.ranking_names.get(getAdapterPosition()),Toast.LENGTH_SHORT).show();

                Intent scoreDetail=new Intent(itemView.getContext(),ScoreDetail.class);
                String usrname=Common.ranking_names.get(getAdapterPosition());
                scoreDetail.putExtra("viewUser",usrname);
                itemView.getContext().startActivity(scoreDetail);


            }
        });


    }
}
