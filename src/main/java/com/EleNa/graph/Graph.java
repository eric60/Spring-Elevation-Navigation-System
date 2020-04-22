package com.EleNa.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Graph implements Serializable {

    //member fields

    private static final long serialVersionUID = 1L;

    //Maps the id of a Node to the Node itself
    protected HashMap<Long,GraphNode> nodes;

    //Constructor
    public Graph(){
        this.nodes = new HashMap<Long,GraphNode>();
    }

    //public methods

    //Returns the number of Nodes in the Graph
    public int getNumNodes(){
        return this.nodes.size();
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

    public GraphNode getNodeById(long id){
        return this.nodes.get(new Long(id));
    }

    public void printGraph() {
        for(Long key : nodes.keySet()) {
            System.out.print("Node id: "+key+" has neighbors: ");
            ArrayList<GraphNode> neighbors = nodes.get(key).getNeighbors();
            for(int i=0; i<neighbors.size(); i++) {
                System.out.print(neighbors.get(i).getId());
                if(i+1 < neighbors.size()) {
                    System.out.print(", ");
                }
            }
            System.out.println("");
        }
    }

}
