package com.EleNa.graph;

import java.io.Serializable;
import java.util.ArrayList;
import com.EleNa.routing.PriorityQueueItem;

public class GraphNode implements Serializable {

    //Member fields
    private static final long serialVersionUID = 0L;

    protected long id;

    protected double longitude;

    protected double latitude;

    protected double elevation;

    protected ArrayList<GraphNode> neighbors;

    protected GraphNode prevNode;

    //Constructor
    public GraphNode(long id, double latitude, double longitude, double elevation){
        this.id = id;
        this.latitude  = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.neighbors = new ArrayList<GraphNode>();
        this.prevNode = null;
    }

    //Constructor
    public GraphNode(long id){
        this.id = id;
        this.latitude  = 200;   // Set latitude and longitude as impossible numbers
        this.longitude = 200;
        this.elevation = 0;
        this.neighbors = new ArrayList<GraphNode>();
        this.prevNode = null;
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

    //Adds a neighbor Node to this Node
    public void addNeighbor(GraphNode node) throws IllegalArgumentException{
        if(node == null){
            throw new IllegalArgumentException("ERROR: node must be non-null");
        }
        if(!this.neighbors.contains(node)) {
            this.neighbors.add(node);
        }
    }

    //Returns this Node's Edges
    public ArrayList<GraphNode> getNeighbors(){
        return this.neighbors;
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

    public static double computeElevationGain(GraphNode source, GraphNode sink){
        if(sink.getElevation() > source.getElevation()){
            return sink.getElevation() - source.getElevation();
        }
        else{
            return 0.0;
        }
    }

    /*
     * The following two methods are used in RouteFinders to retrace the optimal route once the goal Node is found
     */

    //Returns the Node this Node was reached from
    public GraphNode getPrevNode(){
        return this.prevNode;
    }

    //Sets this Node's previous Node to the specified node
    public void setPrevNode(GraphNode prevNode) throws IllegalArgumentException{

        //Check that input != null
        if(prevNode == null){
            throw new IllegalArgumentException("ERROR: node must be non-null");
        }

        this.prevNode = prevNode;
    }

    public boolean equals(PriorityQueueItem item){
        return item.getNode().equals(this);
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }
}
