package com.lovelycoding.foodrecipe.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.lovelycoding.foodrecipe.models.Recipe;
import com.lovelycoding.foodrecipe.repository.RecipeRepository;

import java.util.List;

public class RecipeListViewModels extends ViewModel {
   // private MutableLiveData<List<Recipe>> mReciptList=new MutableLiveData<>();

    private RecipeRepository mRecipeRepository;
    public static boolean mViewRecipeCategory;
   // private
    public RecipeListViewModels() {
        mRecipeRepository=RecipeRepository.getRecipeRepositoryInstance();
      //  mViewRecipeCategory =true;
    }

    public LiveData<List<Recipe>> getRecipes()
    {
        return mRecipeRepository.getRecipes();
    }
    public void searchRecipeApi(String query,int page )
    {
        mRecipeRepository.searchRecipeApi(query,page);
    }

    public void searchNextPageRecipe() {
        if(!mViewRecipeCategory)
        {
            mRecipeRepository.searchNextPage();
        }
    }






}
