package com.hanan.mstg.grand.grandtechtask.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DirectionsResult {
    @SerializedName("routes")
    ArrayList<Route> routes;

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }
}
