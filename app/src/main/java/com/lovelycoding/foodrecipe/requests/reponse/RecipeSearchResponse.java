package com.lovelycoding.foodrecipe.requests.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lovelycoding.foodrecipe.models.Recipe;

import java.util.List;

public class RecipeSearchResponse {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("recipes")
    @Expose
    private List<Recipe> list;

    public int getCount() {
        return count;
    }

    public List<Recipe> getRecipeResponse() {
        return list;
    }

    @Override
    public String toString() {
        return "RecipeSearchResponse{" +
                "count=" + count +
                ", list=" + list +
                '}';
    }
}
