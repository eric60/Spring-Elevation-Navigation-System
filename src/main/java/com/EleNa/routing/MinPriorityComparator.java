package com.EleNa.routing;

import java.util.Comparator;

//Use this Comparator if you want the PriorityQueue to return items with the lowest priority
public class MinPriorityComparator implements Comparator<PriorityQueueItem> {
    public int compare(PriorityQueueItem itemA, PriorityQueueItem itemB){
        if(itemA.getPriority() > itemB.getPriority()){
            return 1;
        }
        else if(itemA.getPriority() < itemB.getPriority()){
            return -1;
        }
        else{
            return 0;
        }
    }
}
