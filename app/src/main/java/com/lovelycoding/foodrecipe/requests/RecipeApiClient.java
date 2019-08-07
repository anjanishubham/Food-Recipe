package com.lovelycoding.foodrecipe.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.lovelycoding.foodrecipe.background.AppExecutor;
import com.lovelycoding.foodrecipe.models.Recipe;
import com.lovelycoding.foodrecipe.requests.reponse.RecipeResponse;
import com.lovelycoding.foodrecipe.requests.reponse.RecipeSearchResponse;
import com.lovelycoding.foodrecipe.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class RecipeApiClient {
    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> mRecipesMutableLiveData;// list of recipes
    private MutableLiveData<Recipe> mRecipeMutableLiveData;//single recipe
    private static final String TAG = "RecipeApiClient";
    private RetrieveRecipesRunnable mRetrieveRecipesRunnable;
    private RetrieveRecipeRunnable mRetrieveRecipeRunnable;
    private MutableLiveData<Boolean> isNetworkTimeOut;



    public static RecipeApiClient getRecipeApiClientInstacne() {
        if (instance == null) {
            instance = new RecipeApiClient();
        }
        return instance;
    }

    private RecipeApiClient() {
        mRecipesMutableLiveData =new MutableLiveData<>();
        mRecipeMutableLiveData=new MutableLiveData<>();
        isNetworkTimeOut =new MutableLiveData<>();
        isNetworkTimeOut.setValue(false);
    }
    public LiveData<List<Recipe>> getRecipes()
    {
        return mRecipesMutableLiveData;
    }

    // getting single recipe object
    public LiveData<Recipe> getRecipe()
    {
        return mRecipeMutableLiveData;
    }
    public LiveData<Boolean> isNetworkTimeOut()
    {
        return isNetworkTimeOut;
    }
    public void searchRecipesApi( String query,int page )
    {
        if(mRetrieveRecipesRunnable!=null)
        {
            mRetrieveRecipesRunnable=null;
        }
        mRetrieveRecipesRunnable=new RetrieveRecipesRunnable(query,page);
        final Future handler= AppExecutor.getAppExecutorInstance().getmNetwrokId().submit(mRetrieveRecipesRunnable);

        AppExecutor.getAppExecutorInstance().getmNetwrokId().schedule(new Runnable() {
            @Override
            public void run() {
                // system time out
               handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public void searchRecipeById(String recipeId) {
        if (mRetrieveRecipeRunnable != null) {
            mRetrieveRecipeRunnable = null;
        }
        mRetrieveRecipeRunnable=new RetrieveRecipeRunnable(recipeId);
        final Future handler=AppExecutor.getAppExecutorInstance().getmNetwrokId().submit(mRetrieveRecipeRunnable);
        AppExecutor.getAppExecutorInstance().getmNetwrokId().schedule(new Runnable() {
            @Override
            public void run() {
                // network time out
                isNetworkTimeOut.postValue(true);
                handler.cancel(true);
            }
        },Constants.NETWORK_TIMEOUT,TimeUnit.MILLISECONDS);
    }



    private class RetrieveRecipesRunnable implements Runnable{

        private String query;
        private int page ;
        private boolean cancelRequest;

        public RetrieveRecipesRunnable(String query, int page) {
            this.query = query;
            this.page = page;
        }


        @Override
        public void run() {
            try {
                Response response=getRecipes(query,page).execute();
                if(cancelRequest)
                    return;
                if (response.code() == 200) {
                    List<Recipe> list = new ArrayList<>(((RecipeSearchResponse) response.body()).getRecipeResponse());
                    if (page == 1) {
                        mRecipesMutableLiveData.postValue(list);
                    } else {
                        List<Recipe> currentRecipes = mRecipesMutableLiveData.getValue();
                        currentRecipes.addAll(list);
                        mRecipesMutableLiveData.postValue(currentRecipes);
                    }
                } else {
                    String error=response.errorBody().string();
                    Log.e(TAG, "run: "+error );
                    mRecipesMutableLiveData.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mRecipesMutableLiveData.postValue(null);
            }


        }

        private Call<RecipeSearchResponse> getRecipes(String query,int page)
        {
            return ServiceGenerator.getRecipeApi().searchRecipe(Constants.API_KEY2,query,String.valueOf(page));
        }

        private void CancelRequest() {
            cancelRequest=true;
        }
    }




    private class RetrieveRecipeRunnable implements Runnable{

        private String recipeId;
        private boolean cancelRequest;

        public RetrieveRecipeRunnable(String  recipeId) {
            this.recipeId=recipeId;
            cancelRequest=false;
        }


        @Override
        public void run() {
            try {
                Response response=getRecipe(recipeId).execute();
                if(cancelRequest)
                    return;
                if (response.code() == 200) {
                    Recipe recipe=((RecipeResponse)response.body()).getRecipe();
                    mRecipeMutableLiveData.postValue(recipe);
                } else {
                    String error=response.errorBody().string();
                    Log.e(TAG, "run: "+error );
                    mRecipeMutableLiveData.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mRecipeMutableLiveData.postValue(null);
            }


        }

        private Call<RecipeResponse> getRecipe(String recipeId)
        {
            return ServiceGenerator.getRecipeApi().getRecipe(Constants.API_KEY2,recipeId);
        }

        private void CancelRequest() {
            cancelRequest=true;
        }
    }

}
