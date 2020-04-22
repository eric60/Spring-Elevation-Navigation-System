package com.EleNa;

import com.EleNa.graph.Graph;
import com.EleNa.graph.GraphNode;
import com.EleNa.routing.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static org.junit.jupiter.api.Assertions.*;


public class AStarRouteFinderTests {

    private GraphNode nodeA;
    private GraphNode nodeB;
    private GraphNode nodeC;
    private GraphNode nodeD;
    private GraphNode nodeE;

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
        nodeE = new GraphNode(4, 1.5,1.5,100.0);

        nodeA.addNeighbor(nodeB);
        nodeA.addNeighbor(nodeC);
        nodeA.addNeighbor(nodeE);
        nodeB.addNeighbor(nodeD);
        nodeC.addNeighbor(nodeD);
        nodeE.addNeighbor(nodeD);

        minComp = new MinPriorityComparator();
        maxComp = new MaxPriorityComparator();

        graph = new Graph();
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);

        routeFinder = new AStarRouteFinder();
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
    void testShortestPath(){
        graph.resetNodes(Double.POSITIVE_INFINITY);

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

        graph.resetNodes(Double.POSITIVE_INFINITY);

        Route minElevationRoute = routeFinder.minElevationGainPath(nodeA,nodeD,Double.POSITIVE_INFINITY);

        assertEquals(3,minElevationRoute.getRoute().size());
        assertEquals(0.0,minElevationRoute.getElevationGain());
        assertEquals(268444.0,Math.floor(minElevationRoute.getLength()));

        assertEquals(nodeA,minElevationRoute.getRoute().get(0));
        assertEquals(nodeC,minElevationRoute.getRoute().get(1));
        assertEquals(nodeD,minElevationRoute.getRoute().get(2));
    }

    @Test
    void testMaxElevationGainPath(){

        graph.resetNodes(Double.NEGATIVE_INFINITY);

        Route maxElevationRoute = routeFinder.maxElevationGainPath(nodeA,nodeD,Double.POSITIVE_INFINITY);

        assertEquals(3,maxElevationRoute.getRoute().size());
        assertEquals(100.0,maxElevationRoute.getElevationGain());

        assertEquals(nodeA,maxElevationRoute.getRoute().get(0));
        assertEquals(nodeE,maxElevationRoute.getRoute().get(1));
        assertEquals(nodeD,maxElevationRoute.getRoute().get(2));
    }

    @Test
    void testUMassToAmherstCollege() throws IOException, ClassNotFoundException {
        Graph graph = DataImporter.fillGraph();

        //UMass
        GraphNode source = graph.getNodeById(DataImporter.getClosestNode(42.3857854,-72.5268343));

        //Amherst College
        GraphNode sink = graph.getNodeById(DataImporter.getClosestNode(42.375818,-72.510099));

        System.out.printf("Source Coordinates:(%f,%f)\nSink Coordinates:(%f,%f)\n",
                source.getLatitude(),source.getLongitude(),sink.getLatitude(),sink.getLongitude());

        Route optimalRoute = routeFinder.shortestPath(source, sink);
        System.out.printf("Optimal Route has %d Nodes with a total length of %f meters and a total elevation gain of %f meters\n",
                optimalRoute.size(), optimalRoute.getLength(), optimalRoute.getElevationGain());

        Route minElevationRoute = routeFinder.minElevationGainPath(source, sink, 2 * optimalRoute.getLength());
        System.out.printf("Minimum Elevation Route has %d Nodes with a total length of %f meters and a total elevation gain of %f meters\n",
                minElevationRoute.size(), minElevationRoute.getLength(), minElevationRoute.getElevationGain());
        Route maxElevationRoute = routeFinder.maxElevationGainPath(source, sink, 2 * optimalRoute.getLength());
        System.out.printf("Maximum Elevation Route has %d Nodes with a total length of %f meters and a total elevation gain of %f meters\n",
                maxElevationRoute.size(), maxElevationRoute.getLength(), maxElevationRoute.getElevationGain());
        //TODO
        //Make assertions of what the three Routes should look like
    }
}
