package com.lovelycoding.foodrecipe.util;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleViewItemDecorator extends RecyclerView.ItemDecoration {
    private int heightItemDecorator;

    public RecycleViewItemDecorator(int heightItemDecorator) {
        this.heightItemDecorator = heightItemDecorator;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        outRect.top=heightItemDecorator;
    }
}
