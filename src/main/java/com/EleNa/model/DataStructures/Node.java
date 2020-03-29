package com.EleNa.model.DataStructures;

import java.util.ArrayList;

public class Node{

    //Member variables
    
    //This node's unique identifier
    protected int id;

    //The location this Node represents
    protected Location location;
    
    //Edges connected to this Node
    protected ArrayList<Edge> edges;  

    //Constructor
    //NOTE: elevation is expected in meters
    public Node(int id, double longitude, double latitude, double elevation){
        this.id = id;
        this.location = new Location(longitude, latitude, elevation);
        this.edges = new ArrayList<Edge>();
    }

    //public methods

    //returns the Node's id
    public int getId(){
        return this.id;
    }

    //returns the Location this Node represents
    public Location getLocation(){
        return this.location;
    }

    //returns the Edges this Node is a part of
    public ArrayList<Edge> getEdges(){
        return this.edges;
    }

    //adds a new Edge to this Node
    public void addEdge(Edge edge) throws IllegalArgumentException{
        if(edge == null){
            throw new IllegalArgumentException("Error: Edge must be non-null.");
        }
        this.edges.add(edge);
    }
}