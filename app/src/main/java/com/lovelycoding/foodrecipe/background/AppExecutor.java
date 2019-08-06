package com.lovelycoding.foodrecipe.background;

import com.google.gson.annotations.Expose;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutor {

    private static AppExecutor mAppExecutor;

    public static AppExecutor getAppExecutorInstance() {
        if (mAppExecutor == null) {
            mAppExecutor=new AppExecutor();
        }
        return mAppExecutor;
    }

    private AppExecutor() {

    }
    private final ScheduledExecutorService mNetwrokId= Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService getmNetwrokId() {
        return mNetwrokId;
    }
}
