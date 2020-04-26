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

        this.routeFinder = new AStarRouteFinder();
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
        GraphNode source = myGraph.getNodeById(DataImporter.getClosestNode(start[1],start[0]));
        GraphNode sink = myGraph.getNodeById(DataImporter.getClosestNode(end[1],end[0]));

        System.out.println("start Coords: ("+start[1]+","+start[0]+")");
        System.out.println("end Coords: ("+end[1]+","+end[0]+")");
        System.out.println("Source ID: " +source.getId());
        System.out.println("Sink ID: " +sink.getId());
        System.out.println("Source Coords: ("+source.getLongitude()+","+source.getLatitude()+")");
        System.out.println("source neighbors: " + source.getNeighbors().size());
        System.out.println("Sink Coords: ("+sink.getLongitude()+","+sink.getLatitude()+")");
        System.out.println("sink neighbors: " + sink.getNeighbors().size());

        System.out.println("Computing Shortest Route...");
        //Calculate the shortest route from source to sink
        Route optimalRoute = routeFinder.shortestPath(source,sink);

        System.out.println("Optimal Route Length: " + optimalRoute.size());
        Route output;

        if (elevationPref.equals("No Preference")) {
            this.myGraph.resetNodes(Double.POSITIVE_INFINITY);
            output = optimalRoute;
        }
        else if (elevationPref.equals("Min Elevation")) {
            this.myGraph.resetNodes(Double.POSITIVE_INFINITY);
            output = routeFinder.minElevationGainPath(source, sink,optimalRoute.getLength() * withinX / 100);
        }
        else {
            this.myGraph.resetNodes(Double.NEGATIVE_INFINITY);
            output = routeFinder.maxElevationGainPath(source, sink,optimalRoute.getLength() * withinX / 100);
        }

        System.out.println("Number of coordinates: " + output.size());
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
