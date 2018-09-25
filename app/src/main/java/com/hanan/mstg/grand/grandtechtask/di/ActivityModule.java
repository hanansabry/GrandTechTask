package com.hanan.mstg.grand.grandtechtask.di;

import com.hanan.mstg.grand.grandtechtask.BuildConfig;
import com.hanan.mstg.grand.grandtechtask.network.GoogleMapsApiService;
import com.hanan.mstg.grand.grandtechtask.network.Service;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ActivityModule {

    @Provides
    @Singleton
    Retrofit provideCall(){
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASEURL)
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public GoogleMapsApiService providesNetworkService(
            Retrofit retrofit) {
        return retrofit.create(GoogleMapsApiService.class);
    }
    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public Service providesService(
            GoogleMapsApiService networkService) {
        return new Service(networkService);
    }

}
