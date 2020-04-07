package com.EleNa.routing;

import java.util.ArrayList;
import com.EleNa.graph.GraphEdge;

public class Route {

    //Member variables

    /*
     *The route is represented as an ArrayList of GraphEdges
     *The start of the route is the source of the first element in the ArrayList
     *The end of the route is the sink of the last element in the ArrayList
     */
    protected ArrayList<GraphEdge> route;

    //Constructor
    public Route(){
        this.route = new ArrayList<GraphEdge>();
    }

    //public methods

    //Returns the route this object represents
    public ArrayList<GraphEdge> getRoute(){
        return this.route;
    }

    //Returns the total distance of the route
    public double getLength(){
        double distance = 0.0;

        for(GraphEdge edge : route){
            distance += edge.getWeight();
        }

        return distance;
    }

    //Returns the elevation gain along this route
    public double getElevationGain(){
        double elevationGain = 0.0;

        for(GraphEdge edge : route){
            if(edge.getSource().getElevation() < edge.getSink().getElevation()){
                elevationGain += edge.getSink().getElevation() - edge.getSource().getElevation();
            }
        }

        return elevationGain;
    }

    //Adds an Edge to this route
    public void addEdge(GraphEdge edge) throws IllegalArgumentException{

        //Check that edge != null
        if(edge == null){
            throw new IllegalArgumentException("ERROR: edge mus tbe non-null");
        }

        route.add(edge);
    }
}
