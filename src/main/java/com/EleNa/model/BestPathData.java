package com.EleNa.model;

import com.EleNa.routing.RouteFinder;

import java.text.Normalizer;
import java.util.ArrayList;

public class BestPathData {
    private ArrayList<ArrayList<Double>> list = new ArrayList<>();
    private RouteFinder routeFinder;

    public double[][] calculateRoute(FormData form) {
        // all edge pairs , [n1,n2] [n2,n4]
        int edgesCnt = 3;
        int edgePair = 2;
        double[][] arr = new double[edgesCnt][edgePair];
        arr[0][0] = 72.33332;
        arr[0][1] = 22.22222;
        return arr;
    }


    public ArrayList<ArrayList<Double>> getList() {
        return list;
    }

    public void setList(ArrayList<ArrayList<Double>> list) {
        this.list = list;
    }
}
