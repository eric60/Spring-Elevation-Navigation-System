package com.EleNa;

import com.EleNa.graph.GraphNode;
import com.EleNa.routing.PriorityQueueItem;
import com.EleNa.routing.MaxPriorityComparator;
import com.EleNa.routing.MinPriorityComparator;
import java.util.PriorityQueue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;


public class ComparatorTests {
    private MinPriorityComparator minComp;
    private MaxPriorityComparator maxComp;
    private PriorityQueueItem itemA;
    private PriorityQueueItem itemB;
    private GraphNode nodeA;
    private GraphNode nodeB;
    private PriorityQueue<PriorityQueueItem> pQueue;

    @BeforeEach
    void startUp() {
        minComp = new MinPriorityComparator();
        maxComp = new MaxPriorityComparator();
        nodeA = new GraphNode(0,0.0,0.0,0.0);
        nodeB = new GraphNode(1,1.0,1.0,10.0);
        itemA = new PriorityQueueItem(nodeA);
        itemB = new PriorityQueueItem(nodeB);

        itemA.setPriority(0.0);
        itemB.setPriority(2.0);
    }

    @AfterEach
    void tearDown() {
        minComp = null;
        maxComp = null;
        nodeA = null;
        nodeB = null;
        itemA = null;
        itemB = null;
        pQueue = null;
    }

    @Test
    void testMinPriorityComparator(){
        pQueue = new PriorityQueue<>(minComp);

        pQueue.add(itemA);
        pQueue.add(itemB);

        assertEquals(itemA,pQueue.poll());
        assertEquals(itemB,pQueue.poll());
    }

    @Test
    void testMaxPriorityComparator(){
        pQueue = new PriorityQueue<>(maxComp);

        pQueue.add(itemA);
        pQueue.add(itemB);

        assertEquals(itemB,pQueue.poll());
        assertEquals(itemA,pQueue.poll());
    }
}
