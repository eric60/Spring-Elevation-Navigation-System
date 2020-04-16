package com.EleNa;

import com.EleNa.graph.Graph;
import com.EleNa.graph.GraphNode;
import com.EleNa.routing.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;


public class AStarRouteFinderTests {

    private GraphNode nodeA;
    private GraphNode nodeB;
    private GraphNode nodeC;
    private GraphNode nodeD;

    private Graph graph;

    private MinPriorityComparator minComp;
    private MaxPriorityComparator maxComp;

    private AStarRouteFinder routeFinder;

    @BeforeEach
    void startUp() {
        nodeA  = new GraphNode(0,0.0,0.0,0.0);
        nodeB = new GraphNode(1,1.0,1.0,10.0);
        nodeC = new GraphNode(2,0.0,1.0,0.0);
        nodeD = new GraphNode(3,1.0,0.0,0.0);

        nodeA.addNeighbor(nodeB);
        nodeA.addNeighbor(nodeC);
        nodeB.addNeighbor(nodeD);
        nodeC.addNeighbor(nodeD);

        graph = new Graph();
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);

        minComp = new MinPriorityComparator();
        maxComp = new MaxPriorityComparator();

        routeFinder = new AStarRouteFinder(graph, minComp);
    }

    @AfterEach
    void tearDown() {
        nodeA = null;
        nodeB = null;
        nodeC = null;
        nodeD = null;

        graph = null;

        routeFinder = null;
    }

    @Test
    void testConstructor(){
        assertNotEquals(null, routeFinder);
    }

    @Test
    void testSetAndGetComparator(){
        assertEquals(minComp,routeFinder.getComparator());

        routeFinder.setComparator(maxComp);

        assertEquals(maxComp,routeFinder.getComparator());
    }

    @Test
    void testShortestPath(){
        Route optimalRoute = routeFinder.shortestPath(nodeA,nodeD);

        assertEquals(3,optimalRoute.getRoute().size());
        assertEquals(10.0,optimalRoute.getElevationGain());
        assertEquals(268427.0,Math.floor(optimalRoute.getLength()));

        assertEquals(nodeA,optimalRoute.getRoute().get(0));
        assertEquals(nodeB,optimalRoute.getRoute().get(1));
        assertEquals(nodeD,optimalRoute.getRoute().get(2));
    }

    @Test
    void testMinElevationGainPath(){
        Route optimalRoute = routeFinder.shortestPath(nodeA,nodeD);

        Route minElevationRoute = routeFinder.minElevationGainPath(nodeA,nodeD,optimalRoute.getLength() * 1.5);

        assertEquals(3,minElevationRoute.getRoute().size());
        assertEquals(0.0,minElevationRoute.getElevationGain());
        assertEquals(268444.0,Math.floor(minElevationRoute.getLength()));

        assertEquals(nodeA,minElevationRoute.getRoute().get(0));
        assertEquals(nodeC,minElevationRoute.getRoute().get(1));
        assertEquals(nodeD,minElevationRoute.getRoute().get(2));
    }
}
