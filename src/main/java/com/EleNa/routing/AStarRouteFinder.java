package com.EleNa.routing;

import com.EleNa.graph.*;

public class AStarRouteFinder implements RouteFinder{

    //Member fields
    protected PriorityQueue pQueue;

    //Constructor
    public AStarRouteFinder(Graph graph){
        this.pQueue = new PriorityQueue(new MinPriorityComparator());
    }

    //public methods

    //Computes the shortest path from source to sink using A* search
    public Route shortestPath(GraphNode source, GraphNode sink){
        //Initialization
        PriorityQueueItem start = new PriorityQueueItem(source);
        start.setDistanceFromSource(0.0);
        start.setPriority(GraphNode.computeDistance(source,sink));
        pQueue.add(start);

        //Main loop
        while(!pQueue.isEmpty()){
            //Get the lowest priority node
            PriorityQueueItem current = pQueue.poll();

            //If we've reached the goal, we're done
            if(current.getNode() == sink){
                return Route.reconstructRoute(source, sink);
            }

            //Look at each Node's neighbor
            for(GraphNode neighbor : current.getNode().getNeighbors()){
                double distanceFromSource = current.getDistanceFromSource() + GraphNode.computeDistance(current.getNode(), neighbor);

                //If neighbor is not in the pQueue, it has never been seen before, so we know we can update safely
                if(!pQueue.contains(neighbor)){
                    PriorityQueueItem neighborItem = new PriorityQueueItem(neighbor);

                    neighbor.setPrevNode(current.getNode());
                    neighborItem.setDistanceFromSource(distanceFromSource);
                    neighborItem.setPriority(distanceFromSource + GraphNode.computeDistance(neighbor, sink));

                    pQueue.add(neighborItem);
                }
                else{
                    PriorityQueueItem neighborItem = pQueue.remove(neighbor);

                    if(distanceFromSource < neighborItem.getDistanceFromSource()){
                        neighborItem.getNode().setPrevNode(current.getNode());
                        neighborItem.setDistanceFromSource(distanceFromSource);
                        neighborItem.setPriority(distanceFromSource + GraphNode.computeDistance(neighbor,sink));

                        pQueue.add(neighborItem);
                    }
                }
            }
        }

        //TODO
        return new Route();
    }

    /*
     *Computes the path with the min. elevation gain from source to sink
     * That is within the specified maxDistance
     */
    public Route minElevationGainPath(GraphNode source, GraphNode sink, double maxDistance){
        Route route = new Route();

        //TODO
        return route;
    }

    /*
     *Computes the path with the max. elevation gain from source to sink
     * That is within the specified maxDistance
     */
    public Route maxElevationGainPath(GraphNode source, GraphNode sink, double maxDistance){
        Route route = new Route();

        //TODO
        return route;
    }

}
