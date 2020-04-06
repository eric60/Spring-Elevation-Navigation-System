package com.EleNa.graph;

public class GraphEdge {

    //Member fields
    protected GraphNode source;

    protected GraphNode sink;

    //distance in meters
    protected double weight;

    //Constructor
    public GraphEdge(GraphNode source, GraphNode sink){
        this.source = source;
        this.sink = sink;
        this.weight = GraphNode.computeDistance(source, sink);
    }

    //public methods

    //Returns this Edge's source Node
    public GraphNode getSource(){
        return this.source;
    }

    //Returns this Edge's sink Node
    public GraphNode getSink(){
        return this.sink;
    }

    //Return's this Edge's weight
    //(distance in meters between source and sink)
    public double getWeight(){
        return this.weight;
    }
}
