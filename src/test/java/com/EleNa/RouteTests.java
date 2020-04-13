package com.EleNa;

import com.EleNa.routing.Route;
import com.EleNa.graph.GraphNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

public class RouteTests {
    private GraphNode nodeA;
    private GraphNode nodeB;
    private Route route;

    @BeforeEach
    void startUp(){
        nodeA = new GraphNode(0, 0.0, 0.0, 0.0);
        nodeB = new GraphNode(1, 1.0, 2.0, 100.0);
        route = new Route();
    }

    @AfterEach
    void tearDown(){
        nodeA = null;
        nodeB = null;
        route = null;
    }

    @Test
    void testRouteConstructor(){
        assertNotEquals(null, route);
    }

    @Test
    void testRouteAppendNodeAndGetRoute(){
        route.appendNode(nodeA);
        route.appendNode(nodeB);

        assertEquals(nodeA, route.getRoute().get(0));
        assertEquals(nodeB, route.getRoute().get(1));
    }

    @Test
    void testRoutePrependNodeAndGetRoute(){
        route.prependNode(nodeA);
        route.prependNode(nodeB);

        assertEquals(nodeB, route.getRoute().get(0));
        assertEquals(nodeA, route.getRoute().get(1));
    }

    @Test
    void testRouteGetElevationGain(){
        route.appendNode(nodeA);
        route.appendNode(nodeB);

        assertEquals(100,route.getElevationGain());
    }

    @Test
    void testRouteGetLength(){
        route.appendNode(nodeA);
        route.appendNode(nodeB);
        route.appendNode(nodeA);

        assertEquals(497258,Math.floor(route.getLength()));
    }

}
