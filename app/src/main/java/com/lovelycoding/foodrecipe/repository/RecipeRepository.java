package com.lovelycoding.foodrecipe.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.lovelycoding.foodrecipe.models.Recipe;
import com.lovelycoding.foodrecipe.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {
    private static RecipeRepository instance;
    private RecipeApiClient mRecipeClient;
    private String mQuery;
    private int mPageNumber;
    private MutableLiveData<Boolean> mIsQueryExhausted=new MutableLiveData<>();
    private MediatorLiveData<List<Recipe>> mRecipeMediatorLiveData=new MediatorLiveData<>();

    private RecipeRepository()
    {
        mRecipeClient=RecipeApiClient.getRecipeApiClientInstacne();
        initMediators();
    }
    public static RecipeRepository getRecipeRepositoryInstance() {
        if (instance == null) {
            instance=new RecipeRepository();
        }
        return instance;
    }

    private void initMediators() {
        LiveData<List<Recipe>> recipeListApiSource=mRecipeClient.getRecipes();
        mRecipeMediatorLiveData.addSource(recipeListApiSource, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if(recipes!=null)
                {
                    mRecipeMediatorLiveData.setValue(recipes);
                    doneQuery(recipes);
                }
            }
        });
    }

    private void doneQuery(List<Recipe> recipes) {
        if (recipes != null) {
            if (recipes.size() < 30) {
                mIsQueryExhausted.setValue(true);

            }
        } else {
                mIsQueryExhausted.setValue(true);
        }

    }

    public LiveData<Boolean> isQueryExhausted() {
        return mIsQueryExhausted;
    }
    public LiveData<List<Recipe>> getRecipes()
    {
        return mRecipeMediatorLiveData;
    }
    public LiveData<Boolean> isNetWorkTimeOut() {
        return mRecipeClient.isNetworkTimeOut();
    }

    public void searchRecipeApi(String query,int page )
    {
        if(page==0)
        {
            page=1;
        }
        mQuery=query;
        mPageNumber=page;
        mIsQueryExhausted.setValue(false);
        mRecipeClient.searchRecipesApi(query,page);
    }
    public void searchNextPage()
    {
        searchRecipeApi(mQuery,mPageNumber+1);
    }

    public LiveData<Recipe> getRecipe() {
        return mRecipeClient.getRecipe();
    }

    public void searchRecipeById(String recipeId) {
        mRecipeClient.searchRecipeById(recipeId);
    }


}
