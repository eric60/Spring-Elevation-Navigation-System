package com.EleNa.controllers;

import com.EleNa.DataImporter;
import com.EleNa.graph.Graph;
import com.EleNa.graph.GraphNode;
import com.EleNa.model.FormData;
import com.EleNa.routing.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;

@Service
public class BestPathService {
    private ArrayList<ArrayList<Double>> list;
    private RouteFinder routeFinder ;
    private Graph myGraph;
    final private String filePath = "./src/main/resources/graph.bin";
    public BestPathService() throws IOException, ClassNotFoundException{
        //Import the graph from the database
        File graphBin = new File(filePath);

        if(graphBin.exists()){
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            this.myGraph = (Graph) objectInputStream.readObject();
            objectInputStream.close();
        }
        else{
            this.myGraph = DataImporter.fillGraph();
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(this.myGraph);
            objectOutputStream.flush();
            objectOutputStream.close();
        }

        this.routeFinder = new AStarRouteFinder(new MinPriorityComparator());
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

        System.out.println("Source Coords: ("+source.getLongitude()+","+source.getLatitude()+")");
        System.out.println("source neighbors: " + source.getNeighbors().size());
        System.out.println("Sink Coords: ("+sink.getLongitude()+","+sink.getLatitude()+")");
        System.out.println("sink neighbors: " + sink.getNeighbors().size());

        //Calculate the shortest route from source to sink
        Route optimalRoute = routeFinder.shortestPath(source,sink);

        System.out.println("Optimal Route Length: " + optimalRoute.size());
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

        System.out.println(output.size());
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
