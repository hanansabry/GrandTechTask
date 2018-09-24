package com.hanan.mstg.grand.grandtechtask.models;

import com.google.gson.annotations.SerializedName;

public class PolyLine {
    @SerializedName("points")
    String points;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
