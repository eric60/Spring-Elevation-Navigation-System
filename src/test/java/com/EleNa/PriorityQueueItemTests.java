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

        itemA = new PriorityQueueItem(0.0, 0.0,nodeA);
        itemB = new PriorityQueueItem(1.0,1.0, nodeB);
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
        assertThrows(IllegalArgumentException.class, () -> new PriorityQueueItem(0.0,0.0,null));
    }

    @Test
    void testItemConstructor(){
        assertNotEquals(null, itemA);
        assertNotEquals(null, itemB);
    }

    @Test
    void testItemGetPriority(){
        assertEquals(0.0, itemA.getPriority());
        assertEquals(1.0, itemB.getPriority());
    }

    @Test
    void testItemGetDistanceFromSource(){
        assertEquals(0.0,itemA.getDistanceFromSource());
        assertEquals(1.0,itemB.getDistanceFromSource());
    }

    @Test
    void testItemGetNode(){
        assertEquals(nodeA,itemA.getNode());
        assertEquals(nodeB,itemB.getNode());
    }
}
