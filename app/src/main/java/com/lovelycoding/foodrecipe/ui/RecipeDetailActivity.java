package com.lovelycoding.foodrecipe.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ImageViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lovelycoding.foodrecipe.R;
import com.lovelycoding.foodrecipe.models.Recipe;
import com.lovelycoding.foodrecipe.viewmodels.RecipeViewModel;

public class RecipeDetailActivity extends BaseActivity {

    private static final String TAG = "RecipeDetailActivity";
    //Ui component
    private AppCompatImageView mRecipeImage;
    private TextView mRecipeTitle,mRecipeRank,error;
    private LinearLayout  mRecipeIngredientsContainer;
    private NestedScrollView mScrollView;
    private LinearLayout linearLayout;
    private Toolbar toolbar;


    //
    private RecipeViewModel mRecipeViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        mRecipeViewModel= ViewModelProviders.of(this).get(RecipeViewModel.class);
        initView();
        initToolbar();
        showProgressBar(true);
        error.setVisibility(View.GONE);
        getIncomingIntent();
        subscribeObserver();
    }

    private void initToolbar() {
        toolbar=findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    // binding the view
    private void initView() {
        mRecipeImage = findViewById(R.id.recipe_image);
        mRecipeTitle=findViewById(R.id.recipe_title);
        mRecipeRank=findViewById(R.id.recipe_social_score);
        mRecipeIngredientsContainer=findViewById(R.id.ingredients_container);
        linearLayout=findViewById(R.id.parent);
        error=findViewById(R.id.error);

    }

    private void subscribeObserver() {
        mRecipeViewModel.getRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe)
            {
                if (recipe != null) {
                    if (recipe.getRecipe_id().equals(mRecipeViewModel.getRecipeId())) {
                        setRecipeProperties(recipe);
                        mRecipeViewModel.setNetworkTimeOut(true);

                    }
                }
            }
        });

        mRecipeViewModel.isNetWorkTimeOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                {
                    // set error message here ...........
                    error.setVisibility(View.VISIBLE);
                    error.setText("Data has not fetch please check your internet connection !!!!!!!");
                    showProgressBar(false);
                }
            }
        });
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("recipe")) {
            Recipe recipe=getIntent().getParcelableExtra("recipe");
            mRecipeViewModel.searchRecipeById(recipe.getRecipe_id());
        //    Toast.makeText(this, "recipe is "+recipe.toString(), Toast.LENGTH_SHORT).show();

        }


    }

    private void setRecipeProperties(Recipe recipe) {
        RequestOptions requestOptions=new RequestOptions().placeholder(R.drawable.images_not_found);
        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(recipe.getImage_url())
                .into(mRecipeImage);
        mRecipeTitle.setText(recipe.getTitle());
        toolbar.setTitle(recipe.getTitle());
        mRecipeRank.setText(String.valueOf(Math.round(recipe.getSocial_rank())));
        mRecipeIngredientsContainer.removeAllViews();
        for(String ingredient: recipe.getIngredients())
        {
            TextView textView = new TextView(this);
            textView.setText(ingredient);
            textView.setTextSize(15);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            mRecipeIngredientsContainer.addView(textView);
        }
        showParent();
        showProgressBar(false);
    }

    private void showParent()
    {
        linearLayout.setVisibility(View.VISIBLE);
    }

}
