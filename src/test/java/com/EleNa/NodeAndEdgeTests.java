package com.EleNa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import com.EleNa.model.DataStructures.Edge;
import com.EleNa.model.DataStructures.Node;
import com.EleNa.model.DataStructures.Location;

class NodeAndEdgeTests{
    private Node nodeA, nodeB;
    private String name = "Main Street";

    @BeforeEach
    void startUp(){
        nodeA = new Node(0, 0.0, 0.0, 0.0);
        nodeB = new Node(1, 45.0, 90.0, 100.0);
    }

    @AfterEach
    void tearDown(){
        nodeA = null;
        nodeB = null;
    }

    @Test
    public void testNodeConstructor(){
        assertNotEquals(null, nodeA);
        assertNotEquals(null, nodeB);
    }

    @Test
    public void testEdgeConstructor(){
        Edge edge = new Edge(nodeA, nodeB, name);

        assertNotEquals(null, edge);
    }

/*    @Test
    public void testNodeGetId(){
        assertEquals(0, nodeA.getId());
        assertEquals(1, nodeB.getId());
    }

    @Test
    public void testNodeGetLocation(){
        Location loc = nodeB.getLocation();

        assertNotEquals(null, loc);
        assertEquals(100.0, loc.getElevation());
        assertEquals(45.0, loc.getLongitude());
        assertEquals(90.0, loc.getLatitude());
    }*/
    @Test
    public void testEdgeGetNodes(){
        Edge edge = new Edge(nodeA, nodeB, name);

        Node[] nodes = edge.getNodes();

        assertEquals(nodeA, nodes[0]);
        assertEquals(nodeB, nodes[1]);
    }

    @Test
    public void testEdgeGetName(){
        Edge edge = new Edge(nodeA, nodeB, name);
        assertEquals("Main Street", edge.getName());
    }

//    @Test
//    public void testNodeAddIllegalEdge(){
//        assertThrows(IllegalArgumentException.class, () -> nodeA.addEdge(null));
//    }
//
//    @Test
//    void testNodeAddAndGetEdge(){
//        Edge edge = new Edge(nodeA, nodeB, name);
//
//
//        nodeA.addEdge(edge);
//
//        assertEquals(edge, nodeA.getEdges().get(0));
//    }
}
