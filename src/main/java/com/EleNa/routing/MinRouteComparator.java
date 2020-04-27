package com.EleNa.routing;

import com.EleNa.graph.GraphNode;

import java.util.Comparator;

//Use this Comparator if you want the PriorityQueue to return items with the lowest priority
public class MinRouteComparator implements Comparator<Route> {
    public int compare(Route routeA, Route routeB){
        if(routeA.getLength() > routeB.getLength()){
            return 1;
        }
        else if(routeA.getLength() < routeB.getLength()){
            return -1;
        }
        else{
            return 0;
        }
    }
}
