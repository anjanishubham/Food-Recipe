package com.lovelycoding.foodrecipe.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public  class Recipe implements Parcelable {

        private String title;
        private float social_rank;
        private String image_url;
        private String recipe_id;
        private String ingredients[];
        private String publisher;

    public Recipe(String title, float social_rank, String image_url, String recipe_id, String[] ingredients, String publisher) {
        this.title = title;
        this.social_rank = social_rank;
        this.image_url = image_url;
        this.recipe_id = recipe_id;
        this.ingredients = ingredients;
        this.publisher = publisher;
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        title = in.readString();
        social_rank = in.readFloat();
        image_url = in.readString();
        recipe_id = in.readString();
        ingredients = in.createStringArray();
        publisher = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeFloat(social_rank);
        dest.writeString(image_url);
        dest.writeString(recipe_id);
        dest.writeStringArray(ingredients);
        dest.writeString(publisher);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getSocial_rank() {
        return social_rank;
    }

    public void setSocial_rank(float social_rank) {
        this.social_rank = social_rank;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
