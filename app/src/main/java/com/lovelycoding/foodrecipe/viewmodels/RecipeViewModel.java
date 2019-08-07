package com.lovelycoding.foodrecipe.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.lovelycoding.foodrecipe.models.Recipe;
import com.lovelycoding.foodrecipe.repository.RecipeRepository;

public class RecipeViewModel extends ViewModel {
    private RecipeRepository mRecipeRepository;
    private String mRecipeId;
    private boolean isNetworkTimeOut;

    public RecipeViewModel() {
        mRecipeRepository=RecipeRepository.getRecipeRepositoryInstance();
        isNetworkTimeOut=false;
    }

    public LiveData<Recipe> getRecipe() {
        return mRecipeRepository.getRecipe();
    }
    public void searchRecipeById(String recipeId)
    {
        mRecipeId=recipeId;
        mRecipeRepository.searchRecipeById(recipeId);
    }

    public String getRecipeId() {
        return mRecipeId;
    }
    public LiveData<Boolean> isNetWorkTimeOut()

    {
        return mRecipeRepository.isNetWorkTimeOut();
    }

    public RecipeViewModel(boolean isNetworkTimeOut) {
        this.isNetworkTimeOut = isNetworkTimeOut;
    }


    public boolean getNetworkTimeOut() {
        return isNetworkTimeOut;
    }

    public void setNetworkTimeOut(boolean networkTimeOut) {
        isNetworkTimeOut = networkTimeOut;
    }
}
