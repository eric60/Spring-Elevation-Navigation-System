package com.EleNa.controllers;

import com.EleNa.model.FormData;
import com.EleNa.routing.RouteFinder;
import java.util.ArrayList;

public class BestPathController {
    private ArrayList<ArrayList<Double>> list = new ArrayList<>();
    private RouteFinder routeFinder;

    public double[][] calculateRoute(FormData form) {
        // all edge pairs , [n1,n2] [n2,n4]

        double[] start = form.getStart();
        double[] end = form.getEnd();
        String elevationPref = form.getElevationPref();
        int withinX = form.getWithinX();

        int edgesCnt = 11;
        int edgePair = 2;

        double[][] edges = new double[edgesCnt][edgePair];
        edges = new double[][]{
                {-77.0366048812866, 38.89873175227713},
                {-77.03364372253417, 38.89876515143842},
                {-77.03364372253417, 38.89549195896866},
                {-77.02982425689697, 38.89549195896866},
                {-77.02400922775269, 38.89387200688839},
                {-77.01519012451172, 38.891416957534204},
                {-77.01521158218382, 38.892068305429156},
                {-77.00813055038452, 38.892051604275686},
                {-77.00832366943358, 38.89143365883688},
                {-77.00818419456482, 38.89082405874451},
                {-77.00815200805664, 38.88989712255097}
        };
        return edges;
    }


    public ArrayList<ArrayList<Double>> getList() {
        return list;
    }

    public void setList(ArrayList<ArrayList<Double>> list) {
        this.list = list;
    }
}
