package com.lovelycoding.foodrecipe.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.EditText;

import com.lovelycoding.foodrecipe.R;
import com.lovelycoding.foodrecipe.adapter.OnRecipeListener;
import com.lovelycoding.foodrecipe.adapter.RecipeRecycleViewAdapter;
import com.lovelycoding.foodrecipe.models.Recipe;
import com.lovelycoding.foodrecipe.util.Constants;
import com.lovelycoding.foodrecipe.util.RecycleViewItemDecorator;
import com.lovelycoding.foodrecipe.viewmodels.RecipeListViewModels;

import java.util.ArrayList;
import java.util.List;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

    //view
    private RecipeListViewModels mRecipeListViewModels;
    private RecyclerView mRecyclerView;
    private RecipeRecycleViewAdapter mAdapter;
    private Toolbar toolbar;

    // var
    private static final String TAG = "RecipeListActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipelist);

        mRecipeListViewModels = ViewModelProviders.of(this).get(RecipeListViewModels.class);
        initToolbar();
        initRecycleView();
        getRecipeCategory();
        initSearchView();
        subscribeObserver();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Food Recipe");
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private void getRecipeCategory() {
        List<Recipe> list = new ArrayList();
        for (int i = 0; i < Constants.DEFAULT_SEARCH_CATEGORY.length; i++) {
            Recipe ob = new Recipe();
            ob.setTitle(Constants.DEFAULT_SEARCH_CATEGORY[i]);
            ob.setSocial_rank(-1);
            list.add(ob);
        }
        if (mAdapter != null) {
            mAdapter.setRecipeCategoryType(list);
            mAdapter.notifyDataSetChanged();
            Log.d(TAG, "getRecipeCategory: " + "setRecipeCategoryType called");
        }
    }

    private void initRecycleView() {
        mRecyclerView = findViewById(R.id.recycle_view_list);
        RecycleViewItemDecorator itemDecorator = new RecycleViewItemDecorator(20);
        mRecyclerView.addItemDecoration(itemDecorator);
        mAdapter = new RecipeRecycleViewAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!mRecyclerView.canScrollVertically(1)) {
                    //next page
                    mRecipeListViewModels.searchNextPageRecipe();

                }
            }
        });
    }

    private void subscribeObserver() {

        mRecipeListViewModels.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {

                if (recipes != null) {
                    if (RecipeListViewModels.mViewRecipeCategory)
                        getRecipeCategory();
                    else
                        mAdapter.setmRecipes(recipes);
                }
            }
        });
    }

    private void initSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onCategoryClick(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onBackPressed() {
        if (RecipeListViewModels.mViewRecipeCategory) {
            super.onBackPressed();
        } else {
            RecipeListViewModels.mViewRecipeCategory = true;
            getRecipeCategory();
        }
    }

    @Override
    public void onCategoryClick(String category) {
        mAdapter.displayLoading();
        RecipeListViewModels.mViewRecipeCategory = false;
        mRecipeListViewModels.searchRecipeApi(category, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.recipe_search_menu,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {

        if(item.getItemId()==R.id.action_categories)
        {
            getRecipeCategory();
        }
        return super.onOptionsItemSelected(item);
    }
}
