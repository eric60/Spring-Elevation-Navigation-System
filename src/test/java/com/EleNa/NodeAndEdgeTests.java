package com.EleNa;

import com.EleNa.graph.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

class NodeAndEdgeTests{
    private GraphNode nodeA, nodeB;
    private GraphEdge edge;

    @BeforeEach
    void startUp(){
        nodeA = new GraphNode(0, 0.0, 0.0, 0.0);
        nodeB = new GraphNode(1, 1.0, 2.0, 100.0);
        edge = new GraphEdge(nodeA, nodeB);
    }

    @AfterEach
    void tearDown(){
        nodeA = null;
        nodeB = null;
        edge = null;
    }

    @Test
    public void testNodeConstructor(){
        assertNotEquals(null, nodeA);
        assertNotEquals(null, nodeB);
    }

    @Test
    public void testNodeGetId(){
        assertEquals(0, nodeA.getId());
        assertEquals(1, nodeB.getId());
    }

    @Test
    public void testNodeGetLatitude(){
        assertEquals(0.0, nodeA.getLatitude());
        assertEquals(1.0, nodeB.getLatitude());
    }

    @Test
    public void testNodeGetLongitude(){
        assertEquals(0.0, nodeA.getLongitude());
        assertEquals(2.0, nodeB.getLongitude());
    }

    @Test
    public void testNodeGetElevation(){
        assertEquals(0.0, nodeA.getElevation());
        assertEquals(100.0, nodeB.getElevation());
    }

    @Test
    public void testEdgeConstructor(){
        assertNotEquals(null, edge);
    }

    @Test
    public void testEdgeGetSource(){
        assertEquals(nodeA, edge.getSource());
    }

    @Test
    public void testEdgeGetSink(){
        assertEquals(nodeB, edge.getSink());
    }

    @Test
    public void testEdgeGetWeight(){
        assertEquals(true,edge.getWeight() > 248600 && edge.getWeight() < 248700);
    }

    @Test
    public void testNodeAddIllegalEdge(){
        assertThrows(IllegalArgumentException.class, () -> nodeA.addEdge(null));
    }

    @Test
    void testNodeAddAndGetEdge(){
        nodeA.addEdge(edge);

        assertEquals(edge, nodeA.getEdges().get(0));
    }
}
