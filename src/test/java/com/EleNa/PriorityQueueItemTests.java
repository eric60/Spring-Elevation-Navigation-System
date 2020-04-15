package com.EleNa;

import com.EleNa.graph.GraphNode;
import com.EleNa.routing.PriorityQueueItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

public class PriorityQueueItemTests {
    private GraphNode nodeA, nodeB;
    private PriorityQueueItem itemA, itemB;

    @BeforeEach
    void startUp(){
        nodeA = new GraphNode(0, 0.0, 0.0, 0.0);
        nodeB = new GraphNode(1, 1.0, 2.0, 100.0);

        itemA = new PriorityQueueItem(nodeA);
        itemB = new PriorityQueueItem(nodeB);
    }

    @AfterEach
    void tearDown(){
        nodeA = null;
        nodeB = null;

        itemA = null;
        itemB = null;
    }

    @Test
    void testItemInvalidConstructor(){
        assertThrows(IllegalArgumentException.class, () -> new PriorityQueueItem(null));
    }

    @Test
    void testItemConstructor(){
        assertNotEquals(null, itemA);
        assertNotEquals(null, itemB);
    }

    @Test
    void testItemGetPriority(){
        assertEquals(Double.POSITIVE_INFINITY, itemA.getPriority());
        assertEquals(Double.POSITIVE_INFINITY, itemB.getPriority());
    }

    @Test
    void testItemSetPriority(){
        itemA.setPriority(1.0);

        assertEquals(1.0, itemA.getPriority());
    }

    @Test
    void testItemSetAndGetDistanceFromSource(){
        assertEquals(Double.POSITIVE_INFINITY,itemA.getDistanceFromSource());

        itemA.setDistanceFromSource(3.14);

        assertEquals(3.14, itemA.getDistanceFromSource());
    }

    @Test
    void testItemGetNode(){
        assertEquals(nodeA,itemA.getNode());
        assertEquals(nodeB,itemB.getNode());
    }

    @Test
    void testItemEquals(){
        assertEquals(true, itemA.equals(nodeA));
    }

    @Test
    void testSetAndGetElevationGainFromSource(){
        assertEquals(Double.POSITIVE_INFINITY,itemA.getElevationGainFromSource());

        itemA.setElevationGainFromSource(3.14);

        assertEquals(3.14, itemA.getElevationGainFromSource());
    }
}
