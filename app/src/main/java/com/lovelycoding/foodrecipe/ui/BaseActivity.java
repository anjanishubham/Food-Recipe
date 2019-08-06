package com.lovelycoding.foodrecipe.ui;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.lovelycoding.foodrecipe.R;

public abstract class BaseActivity extends AppCompatActivity {
    public ProgressBar mProgressBar;

    @Override
    public void setContentView(int layoutResID) {
        ConstraintLayout constraintLayout=(ConstraintLayout)getLayoutInflater().inflate(R.layout.activity_base,null);
        FrameLayout frameLayout=constraintLayout.findViewById(R.id.fm_activity_content);
        mProgressBar=constraintLayout.findViewById(R.id.progress_bar);
        getLayoutInflater().inflate(layoutResID,frameLayout,true);

        super.setContentView(constraintLayout);
    }

    public void showProgressBar(boolean visibility) {

        if(visibility)
        {
            mProgressBar.setVisibility(View.VISIBLE);
        }
        else
            mProgressBar.setVisibility(View.INVISIBLE);

        //mProgressBar.setVisibility(visibility? View.VISIBLE:View.INVISIBLE);

    }
}
