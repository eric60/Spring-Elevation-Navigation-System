package com.EleNa;

import com.EleNa.graph.Graph;
import com.EleNa.routing.Route;
import com.EleNa.graph.GraphEdge;
import com.EleNa.graph.GraphNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

public class RouteTests {
    private GraphNode nodeA;
    private GraphNode nodeB;
    private GraphEdge edgeA;
    private GraphEdge edgeB;
    private Route route;

    @BeforeEach
    void startUp(){
        nodeA = new GraphNode(0, 0.0, 0.0, 0.0);
        nodeB = new GraphNode(1, 1.0, 2.0, 100.0);
        edgeA = new GraphEdge(nodeA, nodeB);
        edgeB = new GraphEdge(nodeB, nodeA);
        route = new Route();
    }

    @AfterEach
    void tearDown(){
        nodeA = null;
        nodeB = null;
        edgeA = null;
        edgeB = null;
        route = null;
    }

    @Test
    void testRouteConstructor(){
        assertNotEquals(null, route);
    }

    @Test
    void testRouteAddEdgeAndGetRoute(){
        route.addEdge(edgeA);

        assertEquals(edgeA, route.getRoute().get(0));
    }

    @Test
    void testRouteGetElevationGain(){
        route.addEdge(edgeA);
        route.addEdge(edgeB);

        assertEquals(100,route.getElevationGain());
    }

    @Test
    void testRouteGetLength(){
        route.addEdge(edgeA);
        route.addEdge(edgeB);

        assertEquals(497258,Math.floor(route.getLength()));
    }

}
