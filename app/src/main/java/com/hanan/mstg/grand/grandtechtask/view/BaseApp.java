package com.hanan.mstg.grand.grandtechtask.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.hanan.mstg.grand.grandtechtask.di.ActivityComponent;
import com.hanan.mstg.grand.grandtechtask.di.ActivityModule;
import com.hanan.mstg.grand.grandtechtask.di.DaggerActivityComponent;

public class BaseApp extends FragmentActivity {
    ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent.builder().activityModule(new ActivityModule()).build();
    }

    public ActivityComponent getActivityComponent(){
        return activityComponent;
    }
}
