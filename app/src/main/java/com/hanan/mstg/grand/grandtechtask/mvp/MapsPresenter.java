package com.hanan.mstg.grand.grandtechtask.mvp;

import com.hanan.mstg.grand.grandtechtask.network.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class MapsPresenter {
    private final Service service;
    private final MapsView view;
    private CompositeSubscription subscriptions;

    public MapsPresenter(Service service, MapsView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getDirectionResult(String origin, String dest, String key){
        view.showWait();

        Subscription subscription = service.getDirections(directionsResult -> {
            view.removeWait();
            view.getDirectionsResultSuccess(directionsResult);
        }, origin, dest, key);

        subscriptions.add(subscription);
    }

    public void onStop(){
        subscriptions.unsubscribe();
    }
}
