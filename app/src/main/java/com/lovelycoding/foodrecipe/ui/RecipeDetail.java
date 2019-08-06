package com.lovelycoding.foodrecipe.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.ImageViewCompat;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lovelycoding.foodrecipe.R;
import com.lovelycoding.foodrecipe.models.Recipe;

public class RecipeDetail extends BaseActivity {

    private AppCompatImageView mRecipeImage;
    private TextView mRecipeTitle,mRecipeRank;
    private LinearLayout  mRecipeIngredientsContainer;
    private NestedScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        initView();
    }

    private void initView() {
        mRecipeImage = findViewById(R.id.recipe_image);
        mRecipeTitle=findViewById(R.id.recipe_title);
        mRecipeRank=findViewById(R.id.recipe_social_score);
        mRecipeIngredientsContainer=findViewById(R.id.ingredients_container);
        mScrollView=findViewById(R.id.parent);
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("recipe")) {
            Recipe recipe=getIntent().getParcelableExtra("recipe");

        }

    }
}
