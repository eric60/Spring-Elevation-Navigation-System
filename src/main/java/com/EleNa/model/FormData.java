package com.EleNa.model;

import org.springframework.web.bind.annotation.RequestParam;

public class FormData {
    private String elevationPref;
    private int withinX;
    private String starting;
    private String destination;

    public String getElevationPref() {
        return elevationPref;
    }

    public void setElevationPref(String elevationPref) {
        this.elevationPref = elevationPref;
    }

    public int getWithinX() {
        return withinX;
    }

    public void setWithinX(int withinX) {
        this.withinX = withinX;
    }

    public String getStarting() {
        return starting;
    }

    public void setStarting(String starting) {
        this.starting = starting;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
