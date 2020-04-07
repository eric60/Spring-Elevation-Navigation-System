package com.EleNa.routing;

import com.EleNa.graph.GraphNode;

import java.util.Comparator;

public class PriorityQueueItem {

    //member variables
    protected double priority;

    protected double distanceFromSource;

    protected GraphNode node;

    //Constructor
    public PriorityQueueItem(double priority, double distanceFromSource, GraphNode node) throws IllegalArgumentException{

        //Check that node != null
        if(node == null){
            throw new IllegalArgumentException("ERROR: node must be non-null");
        }

        this.priority = priority;
        this.distanceFromSource = distanceFromSource;
        this.node = node;
    }

    //public methods

    //Returns the priority of this Item
    public double getPriority(){
        return this.priority;
    }

    //Returns the distance from the source Node this Item's Node is
    public double getDistanceFromSource(){
        return this.distanceFromSource;
    }

    //Returns the Node this Item represents
    public GraphNode getNode(){
        return this.node;
    }
}

