package com.EleNa.routing;

import com.EleNa.graph.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class AStarRouteFinder implements RouteFinder {

    //Member fields
    protected PriorityQueue<GraphNode> pQueue;

    protected Graph graph;

    public AStarRouteFinder(Graph graph){
        this.graph = graph;
    }

    /*
     *Computes the shortest path from source to sink using A* search
     * Expects this.comparator to be a MinPriorityComparator
     */
    public Route shortestPath(GraphNode source, GraphNode sink) {
        //Initialization
        this.pQueue = new PriorityQueue<>(new MinComparator());

        source.setGScore(0);
        source.setFScore(GraphNode.computeDistance(source, sink));
        pQueue.add(source);

        //Main loop
        while (!pQueue.isEmpty()) {
            //Get the lowest priority node
            GraphNode current = pQueue.poll();

            //If we've reached the goal, we're done
            if (current.getId() == sink.getId()) {
                return Route.reconstructRoute(source, sink);
            }

            //Look at each Node's neighbor
            for (GraphNode neighbor : current.getNeighbors()) {
                double gScore = current.getGScore() + GraphNode.computeDistance(current, neighbor);

                if (gScore < neighbor.getGScore()) {

                    if (pQueue.contains(neighbor)) {
                        pQueue.remove(neighbor);
                    }

                    neighbor.setPrevNode(current);
                    neighbor.setGScore(gScore);
                    neighbor.setFScore(gScore + GraphNode.computeDistance(neighbor, sink));

                    if (!pQueue.contains(neighbor))
                        pQueue.add(neighbor);
                }
            }
        }

        //If no route is found, return an empty Route
        return new Route();
    }

    /*
     *Computes the path with the min. elevation gain from source to sink
     * That is within the specified maxDistance
     * Expects this.comparator to be a MinPriorityComparator
     */
    public Route minElevationGainPath(GraphNode source, GraphNode sink, double maxDistance) {
        ArrayList<Route> routes = YensVariant(graph,source.getId(), sink.getId(), maxDistance);

        Route optimal = routes.get(0);
        int i = 0;
        for(Route route : routes){
            if(route.getElevationGain() < optimal.getElevationGain()){
                optimal = route;
            }
            else if(route.getElevationGain() == optimal.getElevationGain()){
                if(route.getLength() < optimal.getLength()){
                    optimal = route;
                }
            }
        }

        return optimal;
    }

    /*
     *Computes the path with the max. elevation gain from source to sink
     * That is within the specified maxDistance
     */
    public Route maxElevationGainPath(GraphNode source, GraphNode sink, double maxDistance) {
        ArrayList<Route> routes = YensVariant(graph, source.getId(), sink.getId(), maxDistance);

        Route optimal = routes.get(0);
        int i = 0;
        for(Route route : routes){
            if(route.getElevationGain() > optimal.getElevationGain()){
                optimal = route;
            }
            else if(route.getElevationGain() == optimal.getElevationGain()){
                if(route.getLength() < optimal.getLength()){
                    optimal = route;
                }
            }
        }

        return optimal;
    }

    //Returns an ArrayList of the shortest paths that are less than maxDistance in length
    //Source: https://en.wikipedia.org/wiki/Yen%27s_algorithm
    public ArrayList<Route> YensVariant(Graph graph, Long sourceId, Long sinkId, double maxDistance) {
        //Initialization
        ArrayList<Route> A = new ArrayList<>();

        HashMap<Long, ArrayList<Long>> removedEdges = new HashMap<>();

        // Determine the shortest path from the source to the sink.
        A.add(this.shortestPath(graph.getNodeById(sourceId), graph.getNodeById(sinkId)));
        graph.resetNodes();

        // Initialize the set to store the potential kth shortest path.
        PriorityQueue<Route> B = new PriorityQueue<>(new MinRouteComparator());

        for (int k = 1; k < (int) Double.POSITIVE_INFINITY; k++) {

            // The spur node ranges from the first node to the next to last node in the previous k-shortest path.
            for (int i = 0; i <= A.get(k - 1).getRoute().size() - 2; i++) {

                // Spur node is retrieved from the previous k-shortest path, k − 1.
                GraphNode spurNode = A.get(k - 1).getNode(i);

                // The sequence of nodes from the source to the spur node of the previous k-shortest path.
                ArrayList<GraphNode> rootPath = A.get(k - 1).getNodes(0, i);

                for (Route route : A) {
                    if (i < route.size() && Route.equals(rootPath, route.getNodes(0, i))) {
                        // Remove the links that are part of the previous shortest paths which share the same root path.
                        Long id1 = route.getRoute().get(i).getId();
                        Long id2 = route.getRoute().get(i + 1).getId();

                        if (removedEdges.get(id1) == null) {
                            removedEdges.put(id1, new ArrayList<>());
                        }

                        removedEdges.get(id1).add(id2);

                        graph.getNodeById(id1).removeNeighbor(id2);
                    }
                }

                for (GraphNode rootPathNode : rootPath) {
                    if (rootPathNode.getId() != spurNode.getId()) {
                        for (GraphNode neighbor : graph.getNodeById(rootPathNode.getId()).getNeighbors()) {

                            if (neighbor.hasNeighbor(rootPathNode.getId())) {
                                if (removedEdges.get(neighbor.getId()) == null) {
                                    removedEdges.put(neighbor.getId(), new ArrayList<>());
                                }
                                removedEdges.get(neighbor.getId()).add(rootPathNode.getId());

                                neighbor.removeNeighbor(rootPathNode.getId());
                            }
                        }
                    }
                }

                // Calculate the spur path from the spur node to the sink.
                Route spurPath = this.shortestPath(graph.getNodeById(spurNode.getId()), graph.getNodeById(sinkId));

                graph.resetNodes();

                // Entire path is made up of the root path and spur path.
                Route totalPath = Route.concat(rootPath, spurPath.getRoute());

                // Add the potential k-shortest path to the heap.
                if(!B.contains(totalPath) && spurPath.size() > 0){
                    B.add(totalPath);
                }

                // Add back the edges and nodes that were removed from the graph.
                removedEdges.forEach((key,value)->{
                    GraphNode node = graph.getNodeById(key);
                    for(Long id : value){
                        node.addNeighbor(graph.getNodeById(id));

                    }
                    removedEdges.get(key).clear();
                });
            }

            if(B.isEmpty()) {
                // This handles the case of there being no spur paths, or no spur paths left.
                // This could happen if the spur paths have already been exhausted (added to A),
                // or there are no spur paths at all - such as when both the source and sink vertices
                // lie along a "dead end".
                break;
            }

            // Add the lowest cost path becomes the k-shortest path.
            Route shortest = B.poll();

            if(shortest.getLength() <= maxDistance){
                A.add(shortest);
                System.out.println("Potential Route added.");
            }
            else{
                return A;
            }
        }
        return A;
    }
}
