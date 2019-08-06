package com.lovelycoding.foodrecipe.requests;

import com.lovelycoding.foodrecipe.requests.reponse.RecipeResponse;
import com.lovelycoding.foodrecipe.requests.reponse.RecipeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApi {


    //Search Recipes
    @GET("api/search")
    Call<RecipeSearchResponse> searchRecipe(
            @Query("key") String key,
            @Query("q") String q,
            @Query("page") String page
    );
    // get Recipe Request
    @GET("api/get")
    Call<RecipeResponse> getRecipe(
            @Query("key") String key,
            @Query("rId") String recipe_id
    );
}
