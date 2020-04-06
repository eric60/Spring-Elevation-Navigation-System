package com.EleNa.graph;

import java.util.ArrayList;

public class GraphNode {

    //Member fields
    protected long id;

    protected double longitude;

    protected double latitude;

    protected double elevation;

    protected ArrayList<GraphEdge> edges;

    //Constructor
    public GraphNode(long id, double latitude, double longitude, double elevation){
        this.id = id;
        this.latitude  = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.edges = new ArrayList<GraphEdge>();
    }

    //public methods

    //Returns this Node's id
    public long getId(){
        return this.id;
    }

    //Returns this Node's longitude
    public double getLongitude(){
        return this.longitude;
    }

    //Returns this Node's latitude
    public double getLatitude(){
        return this.latitude;
    }

    //Returns this Node's elevation
    public double getElevation(){
        return this.elevation;
    }

    //Adds an Edge to this Node
    public void addEdge(GraphEdge edge) throws IllegalArgumentException{
        if(edge == null){
            throw new IllegalArgumentException("ERROR: edge must be non-null");
        }
        this.edges.add(edge);
    }

    //Returns this Node's Edges
    public ArrayList<GraphEdge> getEdges(){
        return this.edges;
    }

    //Uses the Haversine formula to compute the distance between two Nodes (in meters)
    //source: https://www.movable-type.co.uk/scripts/latlong.html
    public static double computeDistance(GraphNode source, GraphNode sink){
        double radius = 6371000;    //In meters

        double phiA = Math.toRadians(source.getLatitude());
        double phiB = Math.toRadians(sink.getLatitude());

        double deltaPhi = Math.toRadians(sink.getLatitude() - source.getLatitude());
        double deltaLambda = Math.toRadians(sink.getLongitude() - source.getLongitude());

        double a = Math.pow(Math.sin(deltaPhi / 2), 2) +
                   Math.cos(phiA) * Math.cos(phiB) *
                   Math.pow(Math.sin(deltaLambda / 2),2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return radius * c;
    }
}
