package com.example.hritik.delete.Adapter_ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hritik.delete.Model.Common;
import com.example.hritik.delete.R;
import com.example.hritik.delete.Start;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    private Context context;
    private List<String> names;
    private List<String> imageUrl;

    public CategoryAdapter( Context context,List<String> names, List<String> imageUrl) {
        this.context = context;
        this.names = names;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_display,parent,false);

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
        public ViewHolder(View itemView) {
            super(itemView);
            catImg=itemView.findViewById(R.id.catImage);
            catTxt=itemView.findViewById(R.id.catName);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Clicked on--"+names.get(getAdapterPosition())+" "+getAdapterPosition(), Toast.LENGTH_SHORT).show();

                    Intent startGame=new Intent(context,Start.class);
                    Common.categoryPosition=getAdapterPosition();
                    Common.categoryName=names.get(getAdapterPosition());


                    context.startActivity(startGame);


                }
            });
        }
    }
}
