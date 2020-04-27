package com.EleNa.routing;

import com.EleNa.graph.*;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStarRouteFinder implements RouteFinder{

    //Member fields
    protected PriorityQueue<GraphNode> pQueue;

    protected Graph graph;

    /*
     *Computes the shortest path from source to sink using A* search
     * Expects this.comparator to be a MinPriorityComparator
    */
    public Route shortestPath(GraphNode source, GraphNode sink){
        //Initialization
        this.pQueue = new PriorityQueue<>(new MinComparator());

        source.setGScore(0);
        source.setFScore(GraphNode.computeDistance(source, sink));
        pQueue.add(source);

        //Main loop
        while(!pQueue.isEmpty()){
            //Get the lowest priority node
            GraphNode current = pQueue.poll();

            //If we've reached the goal, we're done
            if(current.getId() == sink.getId()){
                System.out.println("Route Found!");
                pQueue.clear();
                return Route.reconstructRoute(source, sink);
            }

            //Look at each Node's neighbor
            for(GraphNode neighbor : current.getNeighbors()){
                double gScore = current.getGScore() + GraphNode.computeDistance(current, neighbor);

                if(gScore < neighbor.getGScore()){

                    if(pQueue.contains(neighbor)){
                        pQueue.remove(neighbor);
                    }

                    neighbor.setPrevNode(current);
                    neighbor.setGScore(gScore);
                    neighbor.setFScore(gScore + GraphNode.computeDistance(neighbor,sink));

                    if(!pQueue.contains(neighbor))
                    pQueue.add(neighbor);
                }
            }
        }

        //If no route is found, return an empty Route
        System.out.println("Route not Found.");
        return new Route();
    }

    /*
     *Computes the path with the min. elevation gain from source to sink
     * That is within the specified maxDistance
     * Expects this.comparator to be a MinPriorityComparator
     */
    public Route minElevationGainPath(GraphNode source, GraphNode sink, double maxDistance){
        //Initialization
        this.pQueue = new PriorityQueue<>(new MinComparator());

        ArrayList<Long> explored = new ArrayList<>();

        source.setGScore(0);
        source.setFScore(GraphNode.computeElevationGain(source, sink));
        source.setDistanceFromSource(0);
        pQueue.add(source);

        //Main loop
        while(!pQueue.isEmpty()){
            //Get the lowest priority node
            GraphNode current = pQueue.poll();

            explored.add(current.getId());

            //If we've reached the goal, we're done
            if(current.getId() == sink.getId()){
                System.out.println("Route Found!");
                pQueue.clear();
                return Route.reconstructRoute(source, sink);
            }

            //Look at each Node's neighbor
            for(GraphNode neighbor : current.getNeighbors()){
                double distanceFromSource = current.getDistanceFromSource() + GraphNode.computeDistance(current, neighbor);

                if(distanceFromSource > maxDistance){
                    continue;
                }

                double gScore = current.getGScore() + GraphNode.computeElevationGain(current, neighbor);

                if(gScore < neighbor.getGScore()){

                    if(pQueue.contains(neighbor) || explored.contains(neighbor.getId())){
                        GraphNode copy = new GraphNode(neighbor.getId(),neighbor.getLatitude(),neighbor.getLongitude(),neighbor.getElevation());
                        copy.setPrevNode(current);
                        copy.setGScore(gScore);
                        copy.setFScore(gScore + GraphNode.computeElevationGain(neighbor,sink));
                        copy.setDistanceFromSource(distanceFromSource);

                        pQueue.add(copy);
                    }
                    else {
                        neighbor.setPrevNode(current);
                        neighbor.setGScore(gScore);
                        neighbor.setFScore(gScore + GraphNode.computeElevationGain(neighbor, sink));
                        neighbor.setDistanceFromSource(distanceFromSource);

                        pQueue.add(neighbor);
                    }
                }
            }
        }

        //If no route is found, return an empty Route
        System.out.println("Route not Found.");
        return new Route();
    }

    /*
     *Computes the path with the max. elevation gain from source to sink
     * That is within the specified maxDistance
     */
    public Route maxElevationGainPath(GraphNode source, GraphNode sink, double maxDistance){
        //Initialization
        this.pQueue = new PriorityQueue<>(new MaxComparator());

        ArrayList<Long> explored = new ArrayList<>();

        source.setGScore(0);
        source.setFScore(GraphNode.computeElevationGain(source, sink));
        source.setDistanceFromSource(0);
        pQueue.add(source);

        //Main loop
        while(!pQueue.isEmpty()){
            //Get the highest priority node
            GraphNode current = pQueue.poll();

            explored.add(current.getId());

            //If we've reached the goal, we're done
            if(current.getId() == sink.getId()){
                System.out.println("Route Found!");
                pQueue.clear();
                return Route.reconstructRoute(source, sink);
            }

            //Look at each Node's neighbor
            for(GraphNode neighbor : current.getNeighbors()){

                double distanceFromSource = current.getDistanceFromSource() + GraphNode.computeDistance(current, neighbor);

                if(distanceFromSource > maxDistance){
                    continue;
                }

                double gScore = current.getGScore() + GraphNode.computeElevationGain(current, neighbor);

                if(gScore > neighbor.getGScore() ){

                    if(pQueue.contains(neighbor)|| explored.contains(neighbor.getId())){
                        GraphNode copy = new GraphNode(neighbor.getId(),neighbor.getLatitude(),neighbor.getLongitude(),neighbor.getElevation());
                        copy.setPrevNode(current);
                        copy.setGScore(gScore);
                        copy.setFScore(gScore + GraphNode.computeElevationGain(neighbor,sink));
                        copy.setDistanceFromSource(distanceFromSource);

                        pQueue.add(copy);
                    }
                    else {
                        neighbor.setPrevNode(current);
                        neighbor.setGScore(gScore);
                        neighbor.setFScore(gScore + GraphNode.computeElevationGain(neighbor, sink));
                        neighbor.setDistanceFromSource(distanceFromSource);

                        pQueue.add(neighbor);
                    }
                }
            }
        }

        //If no route is found, return an empty Route
        System.out.println("Route not Found.");
        return new Route();
    }
}
