package com.EleNa.routing;

import com.EleNa.graph.GraphNode;

import java.util.Comparator;

//Use this Comparator if you want the PriorityQueue to return items with the lowest priority
public class MinComparator implements Comparator<GraphNode> {
    public int compare(GraphNode nodeA, GraphNode nodeB){
        if(nodeA.getFScore() > nodeB.getFScore()){
            return 1;
        }
        else if(nodeA.getFScore() < nodeB.getFScore()){
            return -1;
        }
        else{
            return 0;
        }
    }
}
