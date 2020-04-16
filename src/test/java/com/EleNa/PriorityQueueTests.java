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
    void testConstructor(){
        assertNotEquals(null, pQueue);
    }

    @Test
    void testAddAndOfferAndSize(){
        assertEquals(0,pQueue.size());

        pQueue.add(itemA);

        assertEquals(1,pQueue.size());
    }

    @Test
    void testIndexOf(){
        pQueue.add(itemA);

        int idx = pQueue.indexOf(nodeA);

        assertEquals(0, idx);
    }

    @Test
    void testRemove(){
        pQueue.add(itemA);

        assertEquals(itemA, pQueue.remove(nodeA));
        assertEquals(0,pQueue.size());
    }

    @Test
    void testContains(){
        assertEquals(false,pQueue.contains(nodeA));

        pQueue.add(itemB);

        assertEquals(true, pQueue.contains(nodeB));
    }

    @Test
    void testIsEmpty(){
        assertEquals(true,pQueue.isEmpty());

        pQueue.add(itemA);

        assertEquals(false,pQueue.isEmpty());
    }

    @Test
    void testClear(){
        pQueue.add(itemA);
        pQueue.add(itemB);

        assertEquals(2,pQueue.size());
        assertEquals(false,pQueue.isEmpty());

        pQueue.clear();

        assertEquals(0,pQueue.size());
        assertEquals(true,pQueue.isEmpty());
    }

    @Test
    void testPoll(){
        itemA.setPriority(0.0);
        itemB.setPriority(100.0);

        pQueue.add(itemB);
        pQueue.add(itemA);

        assertEquals(itemA,pQueue.poll());
        assertEquals(1,pQueue.size());
        assertEquals(itemB,pQueue.poll());
        assertEquals(true,pQueue.isEmpty());
    }
}
