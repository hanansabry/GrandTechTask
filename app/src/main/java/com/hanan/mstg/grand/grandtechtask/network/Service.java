package com.hanan.mstg.grand.grandtechtask.network;

import com.hanan.mstg.grand.grandtechtask.models.DirectionsResult;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

//class that will execute our subscriber
public class Service {
    private final GoogleMapsApiService apiService;

    public Service(GoogleMapsApiService apiService) {
        this.apiService = apiService;
    }

    public Subscription getDirections(final GetDirectionsCallback callback, String origin, String dest, String key){
        return apiService.getDirections(origin, dest, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DirectionsResult>>() {
                    @Override
                    public Observable<? extends DirectionsResult> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<DirectionsResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(DirectionsResult directionsResult) {
                        callback.onSuccess(directionsResult);
                    }
                });
    }

    public interface GetDirectionsCallback{
        void onSuccess(DirectionsResult directionsResult);
//        void onError(NetworkError networkError);
    }
}
