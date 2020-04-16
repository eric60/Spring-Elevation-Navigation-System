package com.EleNa.routing;

import com.EleNa.graph.*;

import java.util.Comparator;

public class AStarRouteFinder implements RouteFinder{

    //Member fields
    protected PriorityQueue pQueue;

    protected Comparator<? super PriorityQueueItem> comparator;

    protected Graph graph;

    //Constructor
    public AStarRouteFinder(Graph graph, Comparator<? super PriorityQueueItem> comparator){
        this.comparator = comparator;
        this.pQueue = new PriorityQueue(this.comparator);
        this.graph = graph;
    }

    //public methods
    public void setComparator(Comparator<? super PriorityQueueItem> comparator){
        this.comparator = comparator;
        this.pQueue = new PriorityQueue(this.comparator);
    }

    public Comparator<? super PriorityQueueItem> getComparator(){
        return this.comparator;
    }

    /*
     *Computes the shortest path from source to sink using A* search
     * Expects this.comparator to be a MinPriorityComparator
    */
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
                pQueue.clear();
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

        //If no route is found, return an empty Route
        return new Route();
    }

    /*
     *Computes the path with the min. elevation gain from source to sink
     * That is within the specified maxDistance
     * Expects this.comparator to be a MinPriorityComparator
     */
    public Route minElevationGainPath(GraphNode source, GraphNode sink, double maxDistance){
        //Initialization
        PriorityQueueItem start = new PriorityQueueItem(source);
        start.setDistanceFromSource(0.0);
        start.setElevationGainFromSource(0.0);
        start.setPriority(GraphNode.computeElevationGain(source,sink));
        pQueue.add(start);

        //Main loop
        while(!pQueue.isEmpty()){
            //Get the lowest priority node
            PriorityQueueItem current = pQueue.poll();

            //If we've reached the goal, we're done
            if(current.getNode() == sink){
                pQueue.clear();
                return Route.reconstructRoute(source, sink);
            }

            //Look at each Node's neighbor
            for(GraphNode neighbor : current.getNode().getNeighbors()){
                double distanceFromSource = current.getDistanceFromSource() + GraphNode.computeDistance(current.getNode(), neighbor);
                double elevationGainFromSource = current.getElevationGainFromSource() + GraphNode.computeElevationGain(current.getNode(),neighbor);

                if(distanceFromSource >= maxDistance){
                    continue;
                }

                //If neighbor is not in the pQueue, it has never been seen before, so we know we can update safely
                if(!pQueue.contains(neighbor)){
                    PriorityQueueItem neighborItem = new PriorityQueueItem(neighbor);

                    neighbor.setPrevNode(current.getNode());
                    neighborItem.setDistanceFromSource(distanceFromSource);
                    neighborItem.setElevationGainFromSource(elevationGainFromSource);
                    neighborItem.setPriority(elevationGainFromSource + GraphNode.computeElevationGain(neighbor, sink));

                    pQueue.add(neighborItem);
                }
                else{
                    PriorityQueueItem neighborItem = pQueue.remove(neighbor);

                    if(elevationGainFromSource < neighborItem.getElevationGainFromSource()){
                        neighborItem.getNode().setPrevNode(current.getNode());
                        neighborItem.setDistanceFromSource(distanceFromSource);
                        neighborItem.setElevationGainFromSource(elevationGainFromSource);
                        neighborItem.setPriority(distanceFromSource + GraphNode.computeDistance(neighbor,sink));
                        neighborItem.setPriority(elevationGainFromSource + GraphNode.computeElevationGain(neighbor, sink));

                        pQueue.add(neighborItem);
                    }
                }
            }
        }

        //If no route is found, return an empty Route
        return new Route();
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
