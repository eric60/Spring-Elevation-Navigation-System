package com.EleNa.routing;

import com.EleNa.graph.GraphNode;

public interface RouteFinder {

    //public methods

    public Route shortestPath(GraphNode source, GraphNode sink);

    public Route minElevationGainPath(GraphNode source, GraphNode sink, double maxDistance);

    public Route maxElevationGainPath(GraphNode source, GraphNode sink, double maxDistance);
}
