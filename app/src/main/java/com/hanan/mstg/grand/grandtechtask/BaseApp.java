package com.hanan.mstg.grand.grandtechtask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.hanan.mstg.grand.grandtechtask.di.ActivityComponent;
import com.hanan.mstg.grand.grandtechtask.di.DaggerActivityComponent;
import com.hanan.mstg.grand.grandtechtask.network.NetworkModule;

public class BaseApp extends FragmentActivity {
    ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent.builder().networkModule(new NetworkModule()).build();
    }

    public ActivityComponent getActivityComponent(){
        return activityComponent;
    }
}
