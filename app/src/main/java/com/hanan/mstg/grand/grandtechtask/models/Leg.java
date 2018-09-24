package com.hanan.mstg.grand.grandtechtask.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Leg {
    @SerializedName("steps")
    ArrayList<Step> steps;

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }
}
