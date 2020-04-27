package com.EleNa;

import com.EleNa.graph.Graph;
import com.EleNa.graph.GraphNode;
import com.EleNa.routing.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class DijkstraRouteFinderTests {

    private GraphNode nodeA;
    private GraphNode nodeB;
    private GraphNode nodeC;
    private GraphNode nodeD;
    private GraphNode nodeE;
    private GraphNode nodeF;
    private GraphNode nodeG;

    private Graph graph;

    private MinPriorityComparator minComp;
    private MaxPriorityComparator maxComp;

    private DijkstraRouteFinder routeFinder;

    @BeforeEach
    void startUp() {
        nodeA  = new GraphNode(0,0.0,0.0,0.0);
        nodeB = new GraphNode(1,1.0,1.0,10.0);
        nodeC = new GraphNode(2,0.0,1.0,0.1);
        nodeD = new GraphNode(3,1.0,0.0,0.0);
        nodeE = new GraphNode(4,0.0,1.01,1.0);
        nodeF = new GraphNode(5,0.0,1.02,2.0);
        nodeG = new GraphNode(6,0.0,1.03,3.0);


        nodeA.addNeighbor(nodeB);
        nodeA.addNeighbor(nodeC);
        nodeB.addNeighbor(nodeD);
        nodeB.addNeighbor(nodeG);
        nodeC.addNeighbor(nodeD);
        nodeC.addNeighbor(nodeE);
        nodeE.addNeighbor(nodeF);
        nodeF.addNeighbor(nodeG);

        graph = new Graph();
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);
        graph.addNode(nodeG);

        minComp = new MinPriorityComparator();
        maxComp = new MaxPriorityComparator();

        routeFinder = new DijkstraRouteFinder(graph, minComp);
    }

    @AfterEach
    void tearDown() {
        nodeA = null;
        nodeB = null;
        nodeC = null;
        nodeD = null;
        nodeE = null;
        nodeF = null;
        nodeG = null;

        graph = null;

        routeFinder = null;
    }

    @Test
    void testConstructor(){
        assertNotEquals(null, routeFinder);
    }

    @Test
    void testSetAndGetComparator(){
        assertEquals(minComp, routeFinder.getComparator());

        routeFinder.setComparator(maxComp);

        assertEquals(maxComp, routeFinder.getComparator());
    }

    @Test
    void testShortestPath(){
        Route optimalRoute = routeFinder.shortestPath(nodeA,nodeD);

        assertEquals(3, optimalRoute.getRoute().size());
        assertEquals(10.0, optimalRoute.getElevationGain());
        assertEquals(268427.0, Math.floor(optimalRoute.getLength()));

        assertEquals(nodeA,optimalRoute.getRoute().get(0));
        assertEquals(nodeB,optimalRoute.getRoute().get(1));
        assertEquals(nodeD,optimalRoute.getRoute().get(2));
    }

    /**
     * This test checks that the shortest weighted path is picked rather than a path that uses fewer nodes.
     */
    @Test
    void TestLongerShortestPath() {
        Route optimalRoute = routeFinder.shortestPath(nodeA,nodeG);

        assertEquals(5, optimalRoute.getRoute().size());
        assertEquals(3.0, optimalRoute.getElevationGain());
        assertEquals(nodeA, optimalRoute.getRoute().get(0));
        assertEquals(nodeC, optimalRoute.getRoute().get(1));
        assertEquals(nodeE, optimalRoute.getRoute().get(2));
        assertEquals(nodeF, optimalRoute.getRoute().get(3));
        assertEquals(nodeG, optimalRoute.getRoute().get(4));
    }

    @Test
    void testMinElevationGainPath(){
        Route optimalRoute = routeFinder.shortestPath(nodeA,nodeD);

        assertEquals(3, optimalRoute.getRoute().size());

        Route minElevationRoute = routeFinder.minElevationGainPath(nodeA, nodeD,
                                                        optimalRoute.getLength() * 1.5);

        assertEquals(3,minElevationRoute.getRoute().size());
        assertEquals(0.1,minElevationRoute.getElevationGain());

        assertEquals(nodeA,minElevationRoute.getRoute().get(0));
        assertEquals(nodeC,minElevationRoute.getRoute().get(1));
        assertEquals(nodeD,minElevationRoute.getRoute().get(2));
    }

    @Test
    void testMaxElevationGainPath() {
        Route optimalRoute = routeFinder.shortestPath(nodeA,nodeD);

        routeFinder.setComparator(maxComp);
        Route maxElevationRoute = routeFinder.maxElevationGainPath(nodeA,nodeD,optimalRoute.getLength() * 1.5);

        assertEquals(3,maxElevationRoute.getRoute().size());
        assertEquals(10.0,maxElevationRoute.getElevationGain());

        assertEquals(nodeA,maxElevationRoute.getRoute().get(0));
        assertEquals(nodeB,maxElevationRoute.getRoute().get(1));
        assertEquals(nodeD,maxElevationRoute.getRoute().get(2));
    }

    /**
     * Longer weighted path, but has greater elevation gain should be picked.
     */
    @Test
    void testLongerMaxElevationGainPath() {
        Route optimalRoute = routeFinder.shortestPath(nodeA,nodeG);

        routeFinder.setComparator(maxComp);
        Route maxElevationRoute = routeFinder.maxElevationGainPath(nodeA,nodeG,optimalRoute.getLength() * 1.5);

        assertEquals(3,maxElevationRoute.getRoute().size());
        assertEquals(10.0,maxElevationRoute.getElevationGain());
        assertEquals(nodeA,maxElevationRoute.getRoute().get(0));
        assertEquals(nodeB,maxElevationRoute.getRoute().get(1));
        assertEquals(nodeG,maxElevationRoute.getRoute().get(2));
    }
}
