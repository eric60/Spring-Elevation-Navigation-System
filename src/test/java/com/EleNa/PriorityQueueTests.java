package com.EleNa;

import com.EleNa.routing.PriorityQueue;
import com.EleNa.routing.PriorityQueueItem;
import com.EleNa.routing.MinPriorityComparator;
import com.EleNa.graph.GraphNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;


public class PriorityQueueTests {

    private GraphNode nodeA;
    private GraphNode nodeB;
    private PriorityQueueItem itemA;
    private PriorityQueueItem itemB;
    private PriorityQueue pQueue;

    @BeforeEach
    void startUp() {
        nodeA = new GraphNode(0, 0.0, 0.0, 0.0);
        nodeB = new GraphNode(1,0.1,0.1,0.1);

        itemA = new PriorityQueueItem(nodeA);
        itemB = new PriorityQueueItem(nodeB);

        pQueue = new PriorityQueue(new MinPriorityComparator());
    }

    @AfterEach
    void tearDown() {
        nodeA = null;
        nodeB = null;

        itemA = null;
        itemB = null;

        pQueue = null;
    }

    @Test
    void testisEmpty(){
        assertEquals(true, pQueue.isEmpty());

        pQueue.add(itemA);

        assertEquals(false, pQueue.isEmpty());
    }
}
