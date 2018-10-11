package com.quiz.hritik.delete.Adapter_ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.quiz.hritik.delete.Model.Common;
import com.quiz.hritik.delete.R;
import com.quiz.hritik.delete.Start;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private static final String TAG = "CategoryAdapter";
    private List<String> names;
    private List<String> imageUrl;

    public CategoryAdapter(Context context, List<String> names, List<String> imageUrl) {
        this.context = context;
        this.names = names;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_display, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.catTxt.setText(names.get(position));
        Picasso.with(context)
                .load(imageUrl.get(position))
                .into(holder.catImg);

        //     Glide.with(context).load(imageUrl.get(position)).into(holder.catImg);


    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView catImg;
        TextView catTxt;

        public ViewHolder(final View itemView) {
            super(itemView);
            catImg = itemView.findViewById(R.id.catImage);
            catTxt = itemView.findViewById(R.id.catName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String currentDateandTime = sdf.format(new Date());
                    StringBuilder stringBuilder = new StringBuilder(currentDateandTime);
                    int month = Integer.parseInt(stringBuilder.substring(4, 6));
                    int date = Integer.parseInt(stringBuilder.substring(6, 8));
                    int hour = Integer.parseInt(stringBuilder.substring(8, 10));
                    int minute = Integer.parseInt(stringBuilder.substring(10, 12));
                    if (Common.Start_Month == month && Common.Start_Date == date && hour == Common.Start_Hour && (Common.Start_Minute == minute || minute > Common.Start_Minute)) {

                        if (minute < Common.End_Minute) {
                            Intent startGame = new Intent(context, Start.class);
                            Common.categoryPosition = getAdapterPosition();
                            Common.categoryName = names.get(getAdapterPosition());
                            context.startActivity(startGame);
                        } else {
                            Toast.makeText(context, "Times Up !!", Toast.LENGTH_LONG).show();

                        }


                    } else {
                        Toast.makeText(context, "You must be late or early." + Common.Start_Minute, Toast.LENGTH_LONG).show();

                    }


                }
            });


        }

        @Override
        public void onClick(View view) {


        }
    }
}
