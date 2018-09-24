package com.hanan.mstg.grand.grandtechtask.network;

import com.hanan.mstg.grand.grandtechtask.models.DirectionsResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GoogleMapsApiService {

    @GET("json")
    Observable<DirectionsResult> getDirections(@Query(value = "origin",encoded = true) String origin,
                                               @Query(value = "destination",encoded = true) String destination,
                                               @Query("key") String key);
}
