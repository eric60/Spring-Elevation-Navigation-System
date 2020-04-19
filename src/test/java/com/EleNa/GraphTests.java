package com.EleNa;

import com.EleNa.graph.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTests {
    private GraphNode nodeA, nodeB;
    private Graph graph;

    @BeforeEach
    void startUp(){
        nodeA = new GraphNode(0, 0.0, 0.0, 0.0);
        nodeB = new GraphNode(1, 1.0, 2.0, 100.0);
        graph = new Graph();
    }

    @AfterEach
    void tearDown(){
        nodeA = null;
        nodeB = null;
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
    void testGraphAddIllegalNode(){
        assertThrows(IllegalArgumentException.class,() -> graph.addNode(null));
    }

    @Test
    void testGraphAddNode(){
        graph.addNode(nodeA);

        assertEquals(1, graph.getNumNodes());
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

    @Test
    void testWriteAndReadObject() throws IOException, ClassNotFoundException {
        graph.addNode(nodeA);
        graph.addNode(nodeB);

        FileOutputStream fileOutputStream = new FileOutputStream("./Graph.bin");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(graph);
        objectOutputStream.flush();
        objectOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream("./Graph.bin");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Graph myGraph = (Graph) objectInputStream.readObject();
        objectInputStream.close();

        assertEquals(2, myGraph.getNumNodes());
        assertEquals(0.0, myGraph.getNodeById(0).getId());
        assertEquals(0.0, myGraph.getNodeById(0).getLatitude());
        assertEquals(0.0, myGraph.getNodeById(0).getLongitude());
        assertEquals(0.0, myGraph.getNodeById(0).getElevation());
        assertEquals(1, myGraph.getNodeById(1).getId());
        assertEquals(1, myGraph.getNodeById(1).getLatitude());
        assertEquals(2, myGraph.getNodeById(1).getLongitude());
        assertEquals(100, myGraph.getNodeById(1).getElevation());
    }

}
