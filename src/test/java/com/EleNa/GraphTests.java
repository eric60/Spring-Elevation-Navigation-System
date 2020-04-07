package com.EleNa;

import com.EleNa.graph.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

public class GraphTests {
    private GraphNode nodeA, nodeB;
    private GraphEdge edgeA, edgeB;
    private Graph graph;

    @BeforeEach
    void startUp(){
        nodeA = new GraphNode(0, 0.0, 0.0, 0.0);
        nodeB = new GraphNode(1, 1.0, 2.0, 100.0);
        edgeA = new GraphEdge(nodeA, nodeB);
        edgeB = new GraphEdge(nodeB, nodeA);
        graph = new Graph();
    }

    @AfterEach
    void tearDown(){
        nodeA = null;
        nodeB = null;
        edgeA = null;
        edgeB = null;
        graph = null;
    }

    @Test
    void testGraphConstructor(){
        assertNotEquals(null, graph);
    }

    @Test
    void testGraphGetNumNodes(){
        assertEquals(0,graph.getNumNodes());
    }

    @Test
    void testGraphGetNumEdges(){
        assertEquals(0,graph.getNumEdges());
    }

    @Test
    void testGraphAddIllegalNode(){
        assertThrows(IllegalArgumentException.class,() -> graph.addNode(null));
    }

    @Test
    void testGraphAddNode(){
        graph.addNode(nodeA);

        assertEquals(1, graph.getNumNodes());
    }

    @Test
    void testGraphAddIllegalEdge(){
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(null));
    }

    @Test
    void testGraphAddEdge(){
        graph.addEdge(edgeA);

        assertEquals(1, graph.getNumEdges());
        assertEquals(2,graph.getNumNodes());

        graph.addEdge(edgeB);

        assertEquals(2, graph.getNumEdges());
        assertEquals(2,graph.getNumNodes());
    }

    @Test
    void testGraphGetIllegalNodeById(){
        assertEquals(null, graph.getNodeById(123));
    }

    @Test
    void testGraphGetNodeById(){
        graph.addNode(nodeA);
        graph.addNode(nodeB);

        assertEquals(nodeA, graph.getNodeById(0));
        assertEquals(nodeB, graph.getNodeById(1));
    }

}
