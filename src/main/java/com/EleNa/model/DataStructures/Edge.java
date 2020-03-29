package com.EleNa.model.DataStructures;

public class Edge{
    
    //member variables
    protected Node nodeA;
    
    protected Node nodeB;

    protected double weight;

    protected String name;

    //constructor
    public Edge(Node nodeA, Node nodeB, String name) throws IllegalArgumentException{
        //Check if the Nodes are null
        if(nodeA == null || nodeB == null){
            throw new IllegalArgumentException("ERROR: Nodes must be non-null.");
        }

        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.weight = haverSineDistance();
        this.name = name;
    }

    //public methods

    //returns both Nodes this Edge connects
    public Node[] getNodes(){
        return new Node[]{this.nodeA, this.nodeB};
    }

    //returns this Edge's weight
    public double getWeight(){
        return this.weight;
    }

    //returns this Edge's name
    public String getName(){
        return this.name;
    }
    
    //private methods

    /**
     * Calculates the distance between two lat/long points, accounting for height difference
     * Assumes units are in meters
     * Source: https://www.movable-type.co.uk/scripts/latlong.html
    */
    protected double haverSineDistance(){

        //radius of the Earth
        int radius = 6371000;

        double latA = this.nodeA.getLocation().getLatitude();
        double latB = this.nodeB.getLocation().getLatitude();

        double longA = this.nodeA.getLocation().getLongitude();
        double longB = this.nodeB.getLocation().getLongitude();

        double latDist = Math.toRadians(latB - latA);
        double longDist = Math.toRadians(longB - longA);

        double a = Math.pow(Math.sin(latDist / 2), 2) + 
                   Math.cos(Math.toRadians(latA)) * 
                   Math.cos(Math.toRadians(latB)) * 
                   Math.pow(Math.sin(longDist / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return radius * c;
    }
}