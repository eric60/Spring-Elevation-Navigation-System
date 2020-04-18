package com.EleNa;

import com.EleNa.graph.Graph;
import com.EleNa.graph.GraphNode;
import com.EleNa.model.DataStructures.Node;
import com.EleNa.repositories.EdgeRepository;
import com.EleNa.repositories.NodeRepository;
import com.EleNa.repositories.NodeRepositoryCustom;
import com.EleNa.repositories.NodeRepositoryFillImpl;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.lang.Iterable;
import java.util.List;

public class DataImporter {
    private static List<Node> dbNodesList = new ArrayList<Node>();
    private static ArrayList<GraphNode> dbNodes;
    private static Graph graph = new Graph();
    private static NodeRepositoryFillImpl nodeRepo = new NodeRepositoryFillImpl();


    @Autowired
    public DataImporter(NodeRepositoryFillImpl nodeRepo) {
        this.nodeRepo = nodeRepo;
    }

    // Fill graph with data points within reasonable proximity to path from source to sink
    public static Graph fillGraph(double sourceLong, double sourceLat, double sinkLong, double sinkLat) {
        GeometryFactory gf = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);

        Coordinate sourceCoord = new Coordinate(sourceLong, sourceLat);
        Coordinate sinkCoord = new Coordinate(sinkLong, sinkLat);

        Point sourcePoint = gf.createPoint(sourceCoord);
        Point sinkPoint = gf.createPoint(sinkCoord);

        graph = nodeRepo.getLocalPoints(sourceLong, sourceLat, sinkLong, sinkLat, 1000);

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

        long id = getClosestNode(-95.3200780, 32.8516860);
        Graph myGraph = fillGraph(-95.3200780, 32.8516860, -95.3058210, 32.8432690);
        System.out.println(id);
    }
}
