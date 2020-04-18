package com.EleNa.routing;

import com.EleNa.graph.Graph;
import com.EleNa.graph.GraphNode;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Route finding algorithm that uses Dijkstra's to find the shortest path between a source and a sink node in
 * a graph.
 */
public class DijkstraRouteFinder implements RouteFinder {
    protected Graph graph;
    protected PriorityQueue pQueue;

    public DijkstraRouteFinder(Graph graph) throws IllegalArgumentException {
        if (graph == null) {
            throw new IllegalArgumentException("ERROR: graph must be a non-null");
        }
        this.graph = graph;
        this.pQueue = new PriorityQueue(new MinPriorityComparator());
    }

    //Stub so everything compiles
    public void setComparator(Comparator<? super PriorityQueueItem> comparator){
        //does nothing for now
        //Implement if needed
    }

    /**
     * Initializes the priority queue so that all distances are set to infinity, expect the source node, which has its
     * distance initialized to zero.
     * @param source The source node that the search starts at.
     * @return A reference to the priority queue.
     */
    private PriorityQueue initializeDistancesToInfinity(GraphNode source) {
        int numNodes = this.graph.getNumNodes();
        for(int id = 0; id < numNodes; ++id) {
            if (id != source.getId()) {
                PriorityQueueItem item = new PriorityQueueItem(this.graph.getNodeById(id));
                this.pQueue.add(item);
            }
        }
        PriorityQueueItem sourceItem = new PriorityQueueItem(source, 0.0, 0.0);
        this.pQueue.add(sourceItem);
        return this.pQueue;
    }

    /**
     * Construct a route from the provided start and end nodes and the "previous" nodes. It starts from the sink node
     * and works its way backwords
     * @param source The start of the route
     * @param sink The end of the route
     * @param prevNode The ids of nodes along the shortest path.
     * @return
     */
    private
    Route routeFromIds(GraphNode source, GraphNode sink, HashMap<GraphNode, GraphNode> prevNode) throws IllegalArgumentException {
        if(prevNode.get(source) != source) {
            throw new IllegalArgumentException("Error: routeFromIds expects the prev of source to be source.");
        }
        Route route = new Route();
        GraphNode currentNode = sink;
        while(prevNode.get(currentNode) != currentNode) {
            route.prependNode(currentNode);
            currentNode = prevNode.get(currentNode);
        }
        route.prependNode(source);
        return route;
    }

    /**
     * Find the exact shortest path between source and sink.
     * @param source The starting node
     * @param sink The target node.
     * @return Shortest Route between source and sink.
     */
    public Route shortestPath(GraphNode source, GraphNode sink) {
        HashMap<GraphNode, GraphNode> prevNode = new HashMap<GraphNode, GraphNode>();

        initializeDistancesToInfinity(source);

        Boolean earlyStop = false;
        while(!pQueue.isEmpty() && !earlyStop) {
            PriorityQueueItem current = pQueue.poll();

            // when the sink is polled from the queue, it is at its minimum. The shortest path to it will not
            // change. So, we can stop early.
            if (current.getNode() == sink) {
                earlyStop = true;
            }

            for (GraphNode neighbor : current.getNode().getNeighbors()) {
                // removing and reinserting into the pqueue may be needlessly slow?
                PriorityQueueItem neighborPQueueItem = this.pQueue.remove(neighbor);
                if (neighborPQueueItem == null) {
                    // if the neighbor is no longer in the priority queue, its shortest path has already been found.
                    continue;
                }
                Double currentToNeighbor = GraphNode.computeDistance(current.getNode(), neighbor);
                Double altDistance = current.getDistanceFromSource() + currentToNeighbor;
                if (altDistance < neighborPQueueItem.getDistanceFromSource()) {
                    // update the previous node in shortest path to node neighbor.
                    prevNode.put(neighbor, current.getNode());
                }
                // neighbor needs to be replaced in the pqueue whether or not the priority was changed because we
                // removed it.
                Double newDist = Math.min(altDistance, neighborPQueueItem.getDistanceFromSource());
                PriorityQueueItem maybeUpdatedNeighbor = new PriorityQueueItem(neighbor, newDist, newDist);
                this.pQueue.offer(maybeUpdatedNeighbor);
            }
        }
        this.pQueue.clear();
        // make the previous node of source be itself. This is the stopping condition for the route construction
        // function.
        prevNode.put(source, source);
        return routeFromIds(source, sink, prevNode);
    }

    /**
     * Computes the path with the min. elevation gain from source to sink
     * That is within the specified maxDistance
     */
    public Route minElevationGainPath(GraphNode source, GraphNode sink, double maxDistance){
        Route route = new Route();

        //TODO
        return route;
    }

    /**
     * Computes the path with the max. elevation gain from source to sink
     * That is within the specified maxDistance
     */
    public Route maxElevationGainPath(GraphNode source, GraphNode sink, double maxDistance){
        Route route = new Route();

        //TODO
        return route;
    }

}
