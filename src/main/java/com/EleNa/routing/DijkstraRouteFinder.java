package com.EleNa.routing;
import java.util.Comparator;
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
    protected Comparator<? super PriorityQueueItem> comparator;

    public DijkstraRouteFinder(Graph graph, Comparator<? super PriorityQueueItem> comparator) throws IllegalArgumentException {
        if (graph == null) {
            throw new IllegalArgumentException("ERROR: graph must be a non-null");
        }
        this.graph = graph;
        this.comparator = comparator;
        this.pQueue = new PriorityQueue(comparator);
    }

    public void setComparator(Comparator<? super PriorityQueueItem> comparator) {
        this.comparator = comparator;
        this.pQueue = new PriorityQueue(this.comparator);
    }

    public Comparator<? super PriorityQueueItem> getComparator() {
        return this.comparator;
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
     * Initializes the priorities to zero. As we want to maximize distance, we want the priorities to initially be
     * small.
     * @param source
     * @return
     */
    private PriorityQueue initializeDistancesToZero(GraphNode source) {
        int numNodes = this.graph.getNumNodes();
        for(int id = 0; id < numNodes; ++id) {
            if (id != source.getId()) {
                PriorityQueueItem item = new PriorityQueueItem(this.graph.getNodeById(id), Double.NEGATIVE_INFINITY,
                                                               0.0);
                this.pQueue.add(item);
            }
        }
        PriorityQueueItem sourceItem = new PriorityQueueItem(source, Double.POSITIVE_INFINITY, 0.0);
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

        while(!pQueue.isEmpty()) {
            PriorityQueueItem current = pQueue.poll();

            // when the sink is polled from the queue, it is at its minimum. The shortest path to it will not
            // change. So, we can stop early.
            if (current.getNode() == sink) {
                this.pQueue.clear();
                prevNode.put(source, source); // stopping condition.
                return routeFromIds(source, sink, prevNode);
            }
            for (GraphNode neighbor : current.getNode().getNeighbors()) {
                if (this.pQueue.contains(neighbor)) {
                    PriorityQueueItem neighborPQueueItem = this.pQueue.remove(neighbor);
                    Double currentToNeighbor = GraphNode.computeDistance(current.getNode(), neighbor);
                    Double altDistance = current.getDistanceFromSource() + currentToNeighbor;
                    if (altDistance < neighborPQueueItem.getDistanceFromSource()) {
                        // update the previous node in shortest path to node neighbor.
                        prevNode.put(neighbor, current.getNode());
                    }
                    Double newDist = Math.min(altDistance, neighborPQueueItem.getDistanceFromSource());
                    PriorityQueueItem maybeUpdatedNeighbor = new PriorityQueueItem(neighbor, newDist, newDist);
                    this.pQueue.offer(maybeUpdatedNeighbor);
                }

            }
        }
        return new Route();
    }

    /**
     * Computes the path with the min. elevation gain from source to sink
     * That is within the specified maxDistance
     */
    public Route minElevationGainPath(GraphNode source, GraphNode sink, double maxDistance){
        HashMap<GraphNode, GraphNode> prevNode = new HashMap<GraphNode, GraphNode>();

        initializeDistancesToInfinity(source);

        while(!pQueue.isEmpty()) {
            PriorityQueueItem current = pQueue.poll();
            // when the sink is polled from the queue, it is at its minimum. The shortest path to it will not
            // change. So, we can stop early.
            if (current.getNode() == sink) {
                this.pQueue.clear();
                // make the previous node of source be itself. This is the stopping condition for the route construction
                // function.
                prevNode.put(source, source);
                return routeFromIds(source, sink, prevNode);
            }
            if (current.getDistanceFromSource() < maxDistance) {
                for (GraphNode neighbor : current.getNode().getNeighbors()) {
                    if (this.pQueue.contains(neighbor)) {
                        PriorityQueueItem neighborPQueueItem = this.pQueue.remove(neighbor);
                        Double currentToNeighbor = GraphNode.computeElevationGain(current.getNode(), neighbor);
                        Double altElevation = current.getElevationGainFromSource() + currentToNeighbor;
                        if (altElevation <= neighborPQueueItem.getElevationGainFromSource()) {
                            // update the previous node in shortest path to node neighbor.
                            prevNode.put(neighbor, current.getNode());
                        }
                        // neighbor needs to be replaced in the pqueue whether or not the priority was changed because we
                        // removed it.
                        Double newElev = Math.min(altElevation, neighborPQueueItem.getElevationGainFromSource());

                        Double currentToNeighborDist = GraphNode.computeDistance(current.getNode(), neighbor);
                        Double newDist = Math.min(neighborPQueueItem.getDistanceFromSource(),
                                current.getDistanceFromSource() + currentToNeighborDist);

                        PriorityQueueItem maybeUpdatedNeighbor = new PriorityQueueItem(neighbor, newElev, newDist);
                        maybeUpdatedNeighbor.setElevationGainFromSource(newElev);
                        this.pQueue.offer(maybeUpdatedNeighbor);
                    }
                }
            }
        }
        return new Route();
    }

    /**
     * Computes the path with the max. elevation gain from source to sink
     * That is within the specified maxDistance
     */
    public Route maxElevationGainPath(GraphNode source, GraphNode sink, double maxDistance){
        HashMap<GraphNode, GraphNode> prevNode = new HashMap<GraphNode, GraphNode>();

        initializeDistancesToZero(source);

        while(!pQueue.isEmpty()) {
            PriorityQueueItem current = pQueue.poll();
            if (current.getNode() == sink) {
                this.pQueue.clear();
                prevNode.put(source, source); // set the stopping condition.
                return routeFromIds(source, sink, prevNode);
            }
            // don't consider nodes that are too long.
            if (current.getDistanceFromSource() < maxDistance) {
                for (GraphNode neighbor : current.getNode().getNeighbors()) {
                    if (this.pQueue.contains(neighbor)) {
                        PriorityQueueItem neighborPQueueItem = this.pQueue.remove(neighbor);

                        Double currentToNeighbor = GraphNode.computeElevationGain(current.getNode(), neighbor);
                        Double altElevation = current.getElevationGainFromSource() + currentToNeighbor;

                        if (altElevation >= neighborPQueueItem.getElevationGainFromSource()) {
                            // prev of neighbor is not current.
                            prevNode.put(neighbor, current.getNode());
                        }

                        Double newElev = Math.max(altElevation, neighborPQueueItem.getElevationGainFromSource());
                        Double currentToNeighborDist = GraphNode.computeDistance(current.getNode(), neighbor);
                        Double newDist = Math.min(neighborPQueueItem.getDistanceFromSource(),
                                                  current.getDistanceFromSource() + currentToNeighborDist);
                        PriorityQueueItem maybeUpdatedNeighbor = new PriorityQueueItem(neighbor, newElev, newDist);
                        maybeUpdatedNeighbor.setElevationGainFromSource(newElev);
                        this.pQueue.offer(maybeUpdatedNeighbor);
                    }
                }
            }
        }
        return new Route();
    }

}
