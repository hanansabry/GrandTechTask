package com.hanan.mstg.grand.grandtechtask.di;

import com.hanan.mstg.grand.grandtechtask.MapsActivity;
import com.hanan.mstg.grand.grandtechtask.network.NetworkModule;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface ActivityComponent {
    void inject(MapsActivity mapsActivity);
}

