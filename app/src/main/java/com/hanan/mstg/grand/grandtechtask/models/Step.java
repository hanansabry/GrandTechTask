package com.hanan.mstg.grand.grandtechtask.models;

import com.google.gson.annotations.SerializedName;

public class Step {
    @SerializedName("start_location")
    Location startLocation;
    @SerializedName("end_location")
    Location endLocation;
    @SerializedName("polyline")
    PolyLine polyLine;
    @SerializedName("html_instructions")
    String htmlInstructions;

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

    public PolyLine getPolyLine() {
        return polyLine;
    }

    public void setPolyLine(PolyLine polyLine) {
        this.polyLine = polyLine;
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public void setHtmlInstructions(String htmlInstructions) {
        this.htmlInstructions = htmlInstructions;
    }
}
