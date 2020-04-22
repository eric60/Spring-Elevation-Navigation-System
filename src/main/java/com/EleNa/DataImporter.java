package com.EleNa;

import com.EleNa.graph.Graph;
import com.EleNa.graph.GraphNode;
import com.EleNa.model.DataStructures.BufferNode;
import com.EleNa.model.DataStructures.Edge;
import com.EleNa.model.DataStructures.Node;
import com.EleNa.repositories.EdgeRepositoryFillImpl;
import com.EleNa.repositories.NodeRepositoryFillImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class DataImporter {
    private static List<BufferNode> bufferNodes = new ArrayList<BufferNode>();
    private static List<Edge> edges = new ArrayList<Edge>();
    private static Graph graph = new Graph();
    private static NodeRepositoryFillImpl nodeRepo = new NodeRepositoryFillImpl();
    private static EdgeRepositoryFillImpl edgeRepo = new EdgeRepositoryFillImpl();
    private final static double NOT_SET = 200;


    @Autowired
    public DataImporter(NodeRepositoryFillImpl nodeRepo, EdgeRepositoryFillImpl edgeRepo) {
        this.nodeRepo = nodeRepo;
        this.edgeRepo = edgeRepo;
    }

    //Populate a graph will all Nodes in the database
    public static Graph fillGraph(){
        bufferNodes = nodeRepo.getBufferNodes();
        edges = edgeRepo.getEdges();

        // Initialize graph with nodes
        for(int i=0; i<bufferNodes.size(); i++) {
            graph.addNode(new GraphNode(bufferNodes.get(i).getId(), bufferNodes.get(i).getLongitude(),
                    bufferNodes.get(i).getLatitude(), bufferNodes.get(i).getElevation()));
        }

        // Add all neighbors
        for(int i=0; i<edges.size(); i++) {
            if(((graph.getNodeById(edges.get(i).getSrc())) != null) &&
                    (graph.getNodeById(edges.get(i).getDest()) != null)) {
                graph.getNodeById(edges.get(i).getSrc()).addNeighbor(graph.getNodeById(edges.get(i).getDest()));
            }
        }


        /*
        // BufferNode {long id, String point, double elevation, long src, long dest}
        for(int i=0; i<bufferNodes.size(); i++) {
            // Create new GraphNode and add it to graph -- addNode() checks if node already exists
            if(graph.getNodeById(bufferNodes.get(i).getId()) == null) {
                graph.addNode(new GraphNode(bufferNodes.get(i).getId(), bufferNodes.get(i).getLongitude(),
                        bufferNodes.get(i).getLatitude(), bufferNodes.get(i).getElevation()));
            }
            // If node was created / inserted, but longitude, latitude, and elevation weren't set
            else if(graph.getNodeById(bufferNodes.get(i).getId()).getLongitude() == NOT_SET) {
                graph.getNodeById(bufferNodes.get(i).getId()).setLongitude(bufferNodes.get(i).getLongitude());
                graph.getNodeById(bufferNodes.get(i).getId()).setLatitude(bufferNodes.get(i).getLatitude());
                graph.getNodeById(bufferNodes.get(i).getId()).setElevation(bufferNodes.get(i).getElevation());
            }

            // Source GraphNode will always exist since nodesAndEdges table has: nodes.id=edges.src
            // If dest GraphNode hasn't been created / inserted into graph yet
            // Create GraphNode for dest and insert it into graph
            if(graph.getNodeById(bufferNodes.get(i).getDest()) == null) {
                graph.addNode(new GraphNode(bufferNodes.get(i).getDest()));
            }
            // Add neighbor
            graph.getNodeById(bufferNodes.get(i).getSrc())
                    .addNeighbor(graph.getNodeById(bufferNodes.get(i).getDest()));
        }
        */

        return graph;
    }

    public static long getClosestNode(double lon, double lat) {
        long closestID = nodeRepo.getClosestID(lon, lat);
        return closestID;
    }

    public static void main(String[] args) {
        // Tested with map_small_test.osm file
        // lat="32.8516860" lon="-95.3200780" = first node
        // lat="32.8432690" lon="-95.3058210" = last node

        // Tested with map2Small.osm file
        // -72.519473, 42.367418
        // -72.512548, 42.366545

        long id = getClosestNode(-72.519473, 42.367418);
        Graph myGraph = fillGraph();
        //myGraph.printGraph();
        //System.out.println(myGraph.getNodeById(1).getNeighbors().get(0).getNeighbors().get(0).getId());
        //System.out.println(myGraph.getNodeById(1).getNeighbors().get(0).getNeighbors().get(1).getId());
        System.out.println(id);
    }
}
