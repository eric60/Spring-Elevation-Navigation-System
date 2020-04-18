package com.EleNa.model;

public class FormData {
    private String elevationPref;
    private int withinX;
    private double[] start;
    private double[] end;

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

    public double[] getStart() {
        System.out.println("\n--Start--");
        for(double coord : start) {
            System.out.println(coord);
        }
        return start;
    }

    public void setStart(double[] start) {
        this.start = start;
    }

    public double[] getEnd() {
        System.out.println("\n--End--");
        for(double coord : end) {
            System.out.println(coord);
        }
        return end;
    }

    public void setEnd(double[] end) {
        this.end = end;
    }
}
