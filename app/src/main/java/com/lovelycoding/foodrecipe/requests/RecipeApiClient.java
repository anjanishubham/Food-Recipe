package com.lovelycoding.foodrecipe.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lovelycoding.foodrecipe.background.AppExecutor;
import com.lovelycoding.foodrecipe.models.Recipe;
import com.lovelycoding.foodrecipe.requests.reponse.RecipeSearchResponse;
import com.lovelycoding.foodrecipe.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import okio.Timeout;
import retrofit2.Call;
import retrofit2.Response;

public class RecipeApiClient {
    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> mRecipeMutableLiveData;
    private static final String TAG = "RecipeApiClient";
    private RetrieveRecipesRunnable mRetrieveRecipesRunnable;


    public static RecipeApiClient getRecipeApiClientInstacne() {
        if (instance == null) {
            instance = new RecipeApiClient();
        }
        return instance;
    }

    private RecipeApiClient() {
        mRecipeMutableLiveData=new MutableLiveData<>();
    }
    public LiveData<List<Recipe>> getRecipes()
    {
        return mRecipeMutableLiveData;
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
              //  handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MICROSECONDS);
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
                        mRecipeMutableLiveData.postValue(list);
                    } else {
                        List<Recipe> currentRecipes = mRecipeMutableLiveData.getValue();
                        currentRecipes.addAll(list);
                        mRecipeMutableLiveData.postValue(currentRecipes);
                    }
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

        private Call<RecipeSearchResponse> getRecipes(String query,int page)
        {
            return ServiceGenerator.getRecipeApi().searchRecipe(Constants.API_KEY2,query,String.valueOf(page));
        }

        private void CancelRequest() {
            cancelRequest=true;
        }
    }

}
