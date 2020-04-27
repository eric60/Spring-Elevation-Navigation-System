package com.EleNa.routing;

import java.util.ArrayList;
import com.EleNa.graph.GraphNode;

public class Route {

    //Member variables

    /*
     *The route is represented as an ArrayList of GraphEdges
     *The start of the route is the source of the first element in the ArrayList
     *The end of the route is the sink of the last element in the ArrayList
     */
    protected ArrayList<GraphNode> route;

    //Constructor
    public Route(){
        this.route = new ArrayList<GraphNode>();
    }

    //public methods

    //Returns the route this object represents
    public ArrayList<GraphNode> getRoute(){
        return this.route;
    }

    //Returns the total distance of the route
    public double getLength(){
        double distance = 0.0;

        for(int i = 0; i < route.size() - 1; i++){
            distance += GraphNode.computeDistance(route.get(i), route.get(i+1));
        }

        return distance;
    }

    //Returns the number of Nodes in this Route
    public int size(){
        return this.route.size();
    }

    //Returns the Node in this Route at the specified index
    public GraphNode getNode(int i){
        return this.route.get(i);
    }

    //Returns the elevation gain along this route
    public double getElevationGain(){
        double elevationGain = 0.0;

        for(int i = 0; i < route.size() - 1; i++){
            GraphNode a = route.get(i);
            GraphNode b = route.get(i+1);
            if(a.getElevation() < b.getElevation()) {
                elevationGain += b.getElevation() - a.getElevation();
            }
        }

        return elevationGain;
    }

    //Adds a Node to the end of this Route
    public void appendNode(GraphNode node) throws IllegalArgumentException{

        //Check that node != null
        if(node == null){
            throw new IllegalArgumentException("ERROR: node must be non-null");
        }

        route.add(node);
    }

    //Adds a Node to the beginning of this Route
    public void prependNode(GraphNode node) throws IllegalArgumentException{

        //Check that node != null
        if(node == null){
            throw new IllegalArgumentException("Error: node must be non-null");
        }

        route.add(0,node);
    }

    //Reconstructs the route from sink to source by following GraphNode.prev backwards
    //NOTE: Only use if a path is guaranteed, otherwise will result in an infinite loop
    public static Route reconstructRoute(GraphNode source, GraphNode sink){
        Route route = new Route();

        GraphNode temp = sink;

        while(temp.getId() != source.getId()){
            route.prependNode(temp);
            temp = temp.getPrevNode();
        }

        route.prependNode(source);
        return route;
    }
}
