package com.EleNa.controllers;

import com.EleNa.DataImporter;
import com.EleNa.graph.Graph;
import com.EleNa.graph.GraphNode;
import com.EleNa.model.FormData;
import com.EleNa.routing.*;

import javax.validation.constraints.Max;
import java.util.ArrayList;

public class BestPathController {
    private ArrayList<ArrayList<Double>> list = new ArrayList<>();
    private RouteFinder routeFinder = new AStarRouteFinder(new MinPriorityComparator());

    //Backend coordinates are in the format (longitude,latitude)
    //Frontend coordinates are expected in the format (latitude,longitude)
    public double[][] calculateRoute(FormData form) {

        double[] start = form.getStart();
        double[] end = form.getEnd();
        String elevationPref = form.getElevationPref();
        int withinX = form.getWithinX();

        //Import the graph from the database
        Graph myGraph = DataImporter.fillGraph();

        //Find the source and sink Nodes
        GraphNode source = myGraph.getNodeById(DataImporter.getClosestNode(start[1],start[0]));
        GraphNode sink = myGraph.getNodeById(DataImporter.getClosestNode(end[1],end[0]));

        //Calculate the shortest route from source to sink
        Route optimalRoute = routeFinder.shortestPath(source,sink);
        Route output;

        if(elevationPref == "No elevation"){
            output = optimalRoute;
        }
        else if(elevationPref == "Min Elevation"){
            output = routeFinder.minElevationGainPath(source, sink,optimalRoute.getLength() * withinX / 100);
        }
        else{
            routeFinder.setComparator(new MaxPriorityComparator());
            output = routeFinder.maxElevationGainPath(source, sink,optimalRoute.getLength() * withinX / 100);
        }

        int nodesCnt = output.size();
        int nodePair = 2;
        double[][] nodes = new double[nodesCnt][nodePair];

        for(int i = 0; i < nodesCnt; i++){
            nodes[i][0] = output.getNode(i).getLatitude();
            nodes[i][1] = output.getNode(i).getLongitude();
        }
        return nodes;
    }


    public ArrayList<ArrayList<Double>> getList() {
        return list;
    }

    public void setList(ArrayList<ArrayList<Double>> list) {
        this.list = list;
    }
}
