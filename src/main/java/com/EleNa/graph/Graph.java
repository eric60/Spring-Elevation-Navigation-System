package com.EleNa.graph;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
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

    public ArrayList<Long> getAllNodeIds() { return new ArrayList<Long>(this.nodes.keySet()); }

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

    public int numEdges() {
        int edgeCount = 0;
        for(Long key : nodes.keySet()) {
            edgeCount += nodes.get(key).getNeighbors().size();
        }
        return edgeCount;
    }

    public void resetNodes(double val){
        this.nodes.forEach((k,v) ->{
            v.setFScore(val);
            v.setGScore(val);
            v.setDistanceFromSource(val);
        });
    }

    public static void main(String[] args) {
        Graph graph = new Graph();

        GraphNode node = new GraphNode(0,0,0,0);

        graph.addNode(node);

        System.out.println(node.getFScore());
        graph.resetNodes(666);
        System.out.println(node.getFScore());
    }
}
