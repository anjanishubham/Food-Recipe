package com.lovelycoding.foodrecipe.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lovelycoding.foodrecipe.models.Recipe;
import com.lovelycoding.foodrecipe.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {
    private static RecipeRepository instance;
    private RecipeApiClient mRecipeClient;
    private RecipeRepository()
    {
        mRecipeClient=RecipeApiClient.getRecipeApiClientInstacne();;
    }
    public static RecipeRepository getRecipeRepositoryInstance() {
        if (instance == null) {
            instance=new RecipeRepository();
        }
        return instance;
    }

    public LiveData<List<Recipe>> getRecipes()
    {
        return mRecipeClient.getRecipes();
    }
    public void searchRecipeApi(String query,int page )
    {
        mRecipeClient.searchRecipesApi(query,page);
    }
}
