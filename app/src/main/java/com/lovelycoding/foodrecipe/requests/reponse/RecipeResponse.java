package com.lovelycoding.foodrecipe.requests.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lovelycoding.foodrecipe.models.Recipe;

public class RecipeResponse {
   @SerializedName("recipe")
   @Expose
    private Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "recipe=" + recipe +
                '}';
    }
}
