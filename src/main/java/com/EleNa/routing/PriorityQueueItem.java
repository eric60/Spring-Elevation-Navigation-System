package com.EleNa.routing;

import com.EleNa.graph.GraphNode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueItem {

    //member variables
    protected double priority;

    protected double distanceFromSource;

    protected double elevationGainFromSource;

    protected GraphNode node;

    //Constructor
    public PriorityQueueItem(GraphNode node) throws IllegalArgumentException{

        //Check that node != null
        if(node == null){
            throw new IllegalArgumentException("ERROR: node must be non-null");
        }

        this.priority = Double.POSITIVE_INFINITY;
        this.distanceFromSource = Double.POSITIVE_INFINITY;
        this.elevationGainFromSource = Double.POSITIVE_INFINITY;
        this.node = node;
    }

    //public methods

    //Returns the priority of this Item
    public double getPriority(){
        return this.priority;
    }

    //Sets the priority of this Item
    public void setPriority(double priority){
        this.priority = priority;
    }

    //Returns the distance from the source Node this Item's Node
    public double getDistanceFromSource(){
        return this.distanceFromSource;
    }

    //Sets the distance from the source Node this Item's Node
    public void setDistanceFromSource(double distanceFromSource){
        this.distanceFromSource = distanceFromSource;
    }

    //Returns the elevation gain from the source Node to this Item's Node
    public double getElevationGainFromSource(){ return this.elevationGainFromSource; }

    //Sets the elevation gain from the source Node to this Item's Node
    public void setElevationGainFromSource(double elevationGainFromSource){ this.elevationGainFromSource = elevationGainFromSource; }

    //Returns the Node this Item represents
    public GraphNode getNode(){
        return this.node;
    }

    public boolean equals(GraphNode node){
        return this.node == node;
    }
}

