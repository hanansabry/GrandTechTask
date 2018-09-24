package com.hanan.mstg.grand.grandtechtask.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Route {
    @SerializedName("legs")
    ArrayList<Leg> legs;

    public ArrayList<Leg> getLegs() {
        return legs;
    }

    public void setLegs(ArrayList<Leg> legs) {
        this.legs = legs;
    }
}
