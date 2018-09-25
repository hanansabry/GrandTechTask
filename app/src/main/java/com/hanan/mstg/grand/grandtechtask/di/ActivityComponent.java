package com.hanan.mstg.grand.grandtechtask.di;

import com.hanan.mstg.grand.grandtechtask.view.MapsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(MapsActivity mapsActivity);
}

