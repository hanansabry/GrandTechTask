package com.hanan.mstg.grand.grandtechtask.mvp;

import com.hanan.mstg.grand.grandtechtask.models.DirectionsResult;

public interface MapsView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getDirectionsResultSuccess(DirectionsResult directionsResult);
}
