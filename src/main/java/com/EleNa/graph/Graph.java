package com.EleNa.graph;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {

    //member fields
    protected HashMap<Long,GraphEdge> edges;

    //Maps the id of a Node to the Node itself
    protected HashMap<Long,GraphNode> nodes;

    //Constructor
    public Graph(){
        this.edges = new HashMap<Long,GraphEdge>();
        this.nodes = new HashMap<Long,GraphNode>();
    }

    //public methods

    //Returns the number of Nodes in the Graph
    public int getNumNodes(){
        return this.nodes.size();
    }

    //Returns the number of Edges in the Graph
    public int getNumEdges(){
        return this.edges.size();
    }

    //Adds a new Node to the Graph
    public void addNode(GraphNode node) throws IllegalArgumentException{

        //Check node != null
        if(node == null){
            throw new IllegalArgumentException("ERROR: node must be non-null");
        }

        //Check if the node doesn't already exists
        if(!nodes.containsKey(node.getId())) {
            nodes.put(node.getId(), node);
        }

        //Otherwise, don't do anything
    }

    //Adds a new Edge
    public void addEdge(GraphEdge edge)throws IllegalArgumentException {

        //Check edge != null
        if(edge == null){
            throw new IllegalArgumentException("ERROR: edge must be non-null");
        }

        //Both ids are guaranteed to be unique, so their string concatenation should be unique as well
        long id = Long.parseLong(edge.getSource().getId() + "" + edge.getSource().getId());

        //Check if the edge doesn't already exists
        if(!edges.containsKey(id)){
            edges.put(id, edge);

            //Add an Edge's Nodes also
            this.addNode(edge.getSource());
            this.addNode(edge.getSink());
        }

        //Otherwise, don't do anything
    }

    public GraphNode getNodeById(long id){
        return this.nodes.get(new Long(id));
    }

}
