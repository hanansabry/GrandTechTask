package com.hanan.mstg.grand.grandtechtask.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Leg {
    @SerializedName("steps")
    ArrayList<Step> steps;
    @SerializedName("start_location")
    Location startLocation;
    @SerializedName("end_location")
    Location endLocation;
    @SerializedName("start_address")
    String startAddress;
    @SerializedName("end_address")
    String endAddress;

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }
}
