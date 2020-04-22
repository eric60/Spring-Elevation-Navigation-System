package com.EleNa;

import com.EleNa.graph.Graph;
import com.EleNa.graph.GraphNode;
import com.EleNa.model.DataStructures.BufferNode;
import com.EleNa.model.DataStructures.Edge;
import com.EleNa.repositories.EdgeRepositoryFillImpl;
import com.EleNa.repositories.NodeRepositoryFillImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataImporter {
    private static List<BufferNode> bufferNodes = new ArrayList<BufferNode>();
    private static List<Edge> edges = new ArrayList<Edge>();
    private static Graph graph = new Graph();
    private static NodeRepositoryFillImpl nodeRepo = new NodeRepositoryFillImpl();
    private static EdgeRepositoryFillImpl edgeRepo = new EdgeRepositoryFillImpl();
    private final static double NOT_SET = 200;
    static final private String nodePath = "./src/main/resources/nodes.bin";
    static final private String edgePath = "./src/main/resources/edges.bin";


    @Autowired
    public DataImporter(NodeRepositoryFillImpl nodeRepo, EdgeRepositoryFillImpl edgeRepo) {
        this.nodeRepo = nodeRepo;
        this.edgeRepo = edgeRepo;
    }

    //Populate a graph will all Nodes in the database
    public static Graph fillGraph() throws IOException, ClassNotFoundException {
        File nodeBin = new File(nodePath);
        File edgeBin = new File(edgePath);

        ArrayList<BufferNode> nodes;
        ArrayList<Edge> edges;

        if(!nodeBin.exists()){
            FileOutputStream fileOutputStream = new FileOutputStream(nodePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            nodes = DataImporter.getAllNodes();

            objectOutputStream.writeObject(nodes);
            objectOutputStream.flush();
            objectOutputStream.close();
        }
        else{
            FileInputStream fileInputStream = new FileInputStream(nodePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            nodes = (ArrayList<BufferNode>) objectInputStream.readObject();

            objectInputStream.close();
        }

        if(!edgeBin.exists()){
            FileOutputStream fileOutputStream = new FileOutputStream(edgePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            edges = DataImporter.getAllEdges();

            objectOutputStream.writeObject(edges);
            objectOutputStream.flush();
            objectOutputStream.close();
        }
        else{
            FileInputStream fileInputStream = new FileInputStream(edgePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            edges = (ArrayList<Edge>) objectInputStream.readObject();

            objectInputStream.close();
        }

        Graph graph = new Graph();

        // Initialize graph with nodes
        for(BufferNode node : nodes) {
            graph.addNode(new GraphNode(node.getId(), node.getLongitude(),node.getLatitude(), node.getElevation()));
        }

        // Add all neighbors
        for(Edge edge : edges) {
            if (((graph.getNodeById(edge.getSrc())) != null) && (graph.getNodeById(edge.getDest()) != null)) {
                graph.getNodeById(edge.getSrc()).addNeighbor(graph.getNodeById(edge.getDest()));
                graph.getNodeById(edge.getDest()).addNeighbor(graph.getNodeById(edge.getSrc()));
            }
        }

        return graph;
    }

    public static ArrayList<BufferNode> getAllNodes(){
        return nodeRepo.getBufferNodes();
    }

    public static ArrayList<Edge> getAllEdges(){
        return edgeRepo.getEdges();
    }

    public static long getClosestNode(double lon, double lat) {
        long closestID = nodeRepo.getClosestID(lon, lat);
        return closestID;
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException{
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
