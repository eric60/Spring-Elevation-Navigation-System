package com.EleNa;

import com.EleNa.graph.*;
import com.EleNa.routing.PriorityQueueItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class GraphNodeTests {
    private GraphNode nodeA, nodeB;

    @BeforeEach
    void startUp(){
        nodeA = new GraphNode(0, 0.0, 0.0, 0.0);
        nodeB = new GraphNode(1, 1.0, 2.0, 100.0);
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
    public void testNodeSetIllegalPrevNode(){
        assertThrows(IllegalArgumentException.class, () -> nodeA.setPrevNode(null));
    }

    @Test
    public void testNodeSetAndGetPrevNode(){
        nodeA.setPrevNode(nodeB);

        assertEquals(nodeB, nodeA.getPrevNode());
    }

    @Test
    public void testNodeAddIllegalNeighbor(){
        assertThrows(IllegalArgumentException.class, () -> nodeA.addNeighbor(null));
    }

    @Test
    void testNodeAddAndGetNeighbor(){
        nodeA.addNeighbor(nodeB);

        assertEquals(nodeB, nodeA.getNeighbors().get(0));
    }

    @Test
    void testNodeEqualsItem(){
        PriorityQueueItem itemA = new PriorityQueueItem(nodeA);
        assertEquals(true, nodeA.equals(itemA));
    }

    @Test
    void testWriteAndReadObject() throws IOException, ClassNotFoundException {

        nodeA.addNeighbor(nodeB);

        FileOutputStream fileOutputStream = new FileOutputStream("Node.bin");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(nodeA);
        objectOutputStream.flush();
        objectOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream("Node.bin");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        GraphNode node = (GraphNode) objectInputStream.readObject();
        objectInputStream.close();

        assertEquals(1, node.getNeighbors().get(0).getId());
        assertEquals(1, node.getNeighbors().get(0).getLatitude());
        assertEquals(2, node.getNeighbors().get(0).getLongitude());
        assertEquals(100, node.getNeighbors().get(0).getElevation());
        assertEquals(0.0, node.getElevation());
        assertEquals(0.0, node.getLongitude());
        assertEquals(0.0, node.getLatitude());
        assertEquals(0, node.getId());
    }
}
