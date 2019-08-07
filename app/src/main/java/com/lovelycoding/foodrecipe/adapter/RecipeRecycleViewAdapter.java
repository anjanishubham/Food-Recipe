package com.lovelycoding.foodrecipe.adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lovelycoding.foodrecipe.R;
import com.lovelycoding.foodrecipe.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "RecipeRecycleViewAdapter";
    List<Recipe> mRecipeList;
    private static final int RECIPE_TYPE = 1;
    private static final int RECIPE_LOADING_TYPE = 2;
    private static final int RECIPE_CATEGORY_TYPE=3;
    private static final int SEARCH_EXHAUSTED_TYPE=4;
    OnRecipeListener mRecipeListener;

    public RecipeRecycleViewAdapter(OnRecipeListener mRecipeListener) {

        this.mRecipeListener = mRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        switch (viewType) {
            case RECIPE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
                return new RecipeViewHolder(view, mRecipeListener);
            case RECIPE_LOADING_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_list_item, parent, false);
                return new LoadingViewHolder(view);
                case SEARCH_EXHAUSTED_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_query_exhausted, parent, false);
                return new SearchExhaustedViewHolder(view);
            case RECIPE_CATEGORY_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_list, parent, false);
                return new CategoryViewHolder(view,mRecipeListener);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_list, parent, false);
                return new CategoryViewHolder(view,mRecipeListener);


        }


    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType == RECIPE_TYPE) {
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
            ((RecipeViewHolder) holder).title.setText(mRecipeList.get(position).getTitle());
            ((RecipeViewHolder) holder).publisher.setText(mRecipeList.get(position).getPublisher());
            ((RecipeViewHolder) holder).socialScore.setText(String.valueOf(Math.round(mRecipeList.get(position).getSocial_rank())));
            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(mRecipeList.get(position).getImage_url())
                    .into(((RecipeViewHolder) holder).image);
        } else if (itemViewType == RECIPE_CATEGORY_TYPE) {

            try {
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.images_not_found);
               // Uri path1 = Uri.parse("android.resource://com.codingwithmitch.foodrecipes/drawable/" + mRecipes.get(i).getImage_url());
                Uri path=Uri.parse("android.resource://com.lovelycoding.foodrecipe/drawable/"+ mRecipeList.get(position).getImage_url());
                Glide.with(holder.itemView.getContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(path)
                        .into(((CategoryViewHolder)holder).circleImageView);

                ((CategoryViewHolder) holder).categoryTitle.setText(mRecipeList.get(position).getTitle());
            }catch (ClassCastException e)
            {
                Log.d(TAG, "onBindViewHolder: "+e.getLocalizedMessage());
            }
           // (CategoryViewHolder)((CategoryViewHolder) holder).circleImageView.setImageDrawable(R.drawable.ic_launcher_background);
        }

       // Log.d(TAG, "onBindViewHolder: " + mRecipeList.get(position).getImage_url());


    }

    @Override
    public int getItemViewType(int position) {
      if(mRecipeList.get(position).getSocial_rank()==-1){
          return RECIPE_CATEGORY_TYPE;
      } else if (position == mRecipeList.size() - 1 && mRecipeList.size() != 0 &&
              !mRecipeList.get(position).getTitle().equals("EXHAUSTED..")) {
          return RECIPE_LOADING_TYPE;
      } else if (mRecipeList.get(position).getTitle().equals("LOADING..."))
          return RECIPE_LOADING_TYPE;
      else if (mRecipeList.get(position).getTitle().equals("EXHAUSTED..."))
          return SEARCH_EXHAUSTED_TYPE;
      else
          return RECIPE_TYPE;
    }

    public void displayLoading() {
        if(!isLoading())
        {
            Recipe recipe = new Recipe();
            recipe.setTitle("LOADING...");
            List<Recipe> loadinglist = new ArrayList<>();
            loadinglist.add(recipe);
            mRecipeList=loadinglist;
            notifyDataSetChanged();
        }
    }

    public void setSearchExhausted() {
        hideLoading();
        Recipe recipe = new Recipe();
        recipe.setTitle("EXHAUSTED...");
        mRecipeList.add(recipe);
        notifyDataSetChanged();

    }

    private void hideLoading() {
        {
            for (Recipe r : mRecipeList) {
                if (r.getTitle().equals("LOADING...")) {
                    mRecipeList.remove(r);
                    break;

                }
            }
            notifyDataSetChanged();
        }
    }
    private boolean isLoading() {
        if(mRecipeList!=null)
        {
            if(mRecipeList.size()>0)
            {
                if(mRecipeList.get(mRecipeList.size()-1).getTitle().equals("LOADING..."))
                {
                    return true;

                }
            }
        }
        return false;

    }

    @Override
    public int getItemCount() {
        if (mRecipeList != null)
            return mRecipeList.size();
        else
            return 0;
    }

    public void setmRecipes(List<Recipe> recipes) {
        mRecipeList = recipes;
        notifyDataSetChanged();
    }
    public void setRecipeCategoryType(List<Recipe> recipes)
    {
        mRecipeList=recipes;
        notifyDataSetChanged();

    }

    public Recipe getSelectedRecipe(int position) {
        if (mRecipeList != null) {
            if (mRecipeList.size() > 0) {
                return mRecipeList.get(position);
            }
        }
        return null;
    }
}
