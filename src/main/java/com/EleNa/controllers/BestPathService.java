package com.EleNa.controllers;

import com.EleNa.DataImporter;
import com.EleNa.graph.Graph;
import com.EleNa.graph.GraphNode;
import com.EleNa.model.FormData;
import com.EleNa.routing.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;

@Service
public class BestPathService {
    private ArrayList<ArrayList<Double>> list;
    private RouteFinder routeFinder ;
    private Graph myGraph;

    public BestPathService() throws ClassNotFoundException, IOException{
        //Import the graph from the database
        System.out.println("Loading Graph...");
        this.myGraph = DataImporter.fillGraph();
        System.out.println("Graph Loaded");

        this.routeFinder = new AStarRouteFinder(this.myGraph);
        this.list = new ArrayList<>();
    }

    //Backend coordinates are in the format (longitude,latitude)
    //Frontend coordinates are expected in the format (latitude,longitude)
    public double[][] calculateRoute(FormData form) {

        double[] start = form.getStart();
        double[] end = form.getEnd();
        String elevationPref = form.getElevationPref();
        int withinX = form.getWithinX();

        //Find the source and sink Nodes
        GraphNode source = this.myGraph.getNodeById(DataImporter.getClosestNode(start[1],start[0]));
        GraphNode sink = this.myGraph.getNodeById(DataImporter.getClosestNode(end[1],end[0]));

        System.out.println("Computing Shortest Route...");

        //Calculate the shortest route from source to sink
        this.myGraph.resetNodes();
        Route optimalRoute = routeFinder.shortestPath(source,sink);

        System.out.println("Optimal Route Length: " + optimalRoute.getLength());
        Route output;

        if (elevationPref.equals("No Preference")) {
            output = optimalRoute;
        }
        else if (elevationPref.equals("Min Elevation")) {
            this.myGraph.resetNodes();
            output = routeFinder.minElevationGainPath(source, sink,optimalRoute.getLength() * (100 + withinX) / 100);
            System.out.println("Min. Elevation Route Length: " + output.getLength());
            System.out.println("Min. Elevation Route Elevation Gain: " + output.getElevationGain());
        }
        else {
            this.myGraph.resetNodes();
            output = routeFinder.maxElevationGainPath(source, sink,optimalRoute.getLength() * (100 + withinX) / 100);
            System.out.println("Max. Elevation Route Length: " + output.getLength());
            System.out.println("Max. Elevation Route Elevation Gain: " + output.getElevationGain());
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
