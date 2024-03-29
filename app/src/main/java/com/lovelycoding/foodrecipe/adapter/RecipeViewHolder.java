package com.lovelycoding.foodrecipe.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.lovelycoding.foodrecipe.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    OnRecipeListener onRecipeListener;
    TextView title,publisher,socialScore;
    AppCompatImageView image;
    public RecipeViewHolder(@NonNull View itemView,OnRecipeListener onRecipeListener) {
        super(itemView);
        this.onRecipeListener=onRecipeListener;
        title=itemView.findViewById(R.id.recipe_title);
        publisher=itemView.findViewById(R.id.recipe_publisher);
        socialScore=itemView.findViewById(R.id.recipe_social_score);
        image=itemView.findViewById(R.id.recipe_image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        onRecipeListener.onRecipeClick(getAdapterPosition());
    }
}
