package com.EleNa;

import com.EleNa.repositories.EdgeRepository;
import com.EleNa.repositories.NodeRepository;
//import org.locationtech.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.EleNa.model.DataStructures.Node;
import com.EleNa.model.DataStructures.Edge;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OsmParser {
    private static NodeRepository nodeRepo;
    private static EdgeRepository edgeRepo;

    private static List<Node> nodes = new ArrayList<>();
    private static List<Edge> edges = new ArrayList<>();

    private static final int SQL_BATCH_INSERT = 500;
    private static final int REACH_NOTIFICATION = 50;

    private static int excludedWays = 0;
    public static int nodeCnt, wayCnt, edgeCnt;

    @Autowired
    public OsmParser(NodeRepository nodeRepo, EdgeRepository edgeRepo) {
        this.nodeRepo = nodeRepo;
        this.edgeRepo = edgeRepo;
    }

    public static String parseOSMFile(File osmFile){
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read( osmFile );
            System.out.println("Root element :" + document.getRootElement().getName());

            List<org.dom4j.Node> nodeNodes = document.selectNodes("/osm/node" );
            List<org.dom4j.Node> wayNodes = document.selectNodes("/osm/way" );

            System.out.println("Number of Node elements: " + nodeNodes.size());
            System.out.println("Number of Way elements: " + wayNodes.size());

            parseNodeNodes(nodeNodes);
            parseWayNodes(wayNodes);
            System.out.println("Excluded " + excludedWays + " ways");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("Finished");
        return "<h1>Imported node and way data</h1>";
    }

    public static void parseNodeNodes(List<org.dom4j.Node> nodeNodes) {
        int i = 0;
        for (org.dom4j.Node node : nodeNodes) {
            String nodeId = node.valueOf("@id");
            String lon = node.valueOf("@lon");
            String lat = node.valueOf("@lat");

            Coordinate coordinate = new Coordinate(Double.parseDouble(lon), Double.parseDouble(lat));
            Node osmNode = new Node(Long.parseLong(nodeId), coordinate);
            nodes.add(osmNode);
            i++;
            if(i % REACH_NOTIFICATION == 0) {
                System.out.println("Reached " + i + " node elements");
            }
            if(i % SQL_BATCH_INSERT == 0) {
                nodeRepo.saveAll(nodes);
                System.out.println("--- Reached " + i + " node elements and inserted into Nodes table");
                nodes.clear();
            }
        }
        nodeRepo.saveAll(nodes);
        OsmParser.nodeCnt = i;
        System.out.println("--- Finished inserting " + i + " nodes");
    }

    public static void parseWayNodes(List<org.dom4j.Node> wayNodes) {
        int wayCnt = 0;
        int ndCnt = 0;
        Edge prev = null;
        for(org.dom4j.Node wayNode : wayNodes) {
            List<org.dom4j.Node> wayChildrenNodes = wayNode.selectNodes(".//nd");
            List<org.dom4j.Node> wayChildrenTagNodes = wayNode.selectNodes(".//tag");

            boolean excludeCurrWay = excludeWay(wayChildrenTagNodes);
            if (excludeCurrWay) { continue;}

            int ndIdx = 0;
            for(Iterator<org.dom4j.Node> iter = wayChildrenNodes.iterator(); iter.hasNext();) {
                org.dom4j.Node ndRefNode = iter.next();
                Long ndRef = Long.parseLong(ndRefNode.valueOf("@ref"));
                if(ndIdx != 0) {
                    prev.setDest(ndRef);
                }

                if(iter.hasNext()) {
                    Edge edge = new Edge(ndRef);
                    edges.add(edge);
                    prev = edge;
                } else {
//                        System.out.println("NdIdx: " + ndIdx + ", last Way nd trigger don't create new Edge");
                }
                ndIdx++;
                ndCnt++;
            }
            wayCnt++;
            if(ndCnt % REACH_NOTIFICATION == 0) {
                System.out.println("Reached " + ndCnt + " nd elements");
            }
            if(ndCnt % SQL_BATCH_INSERT == 0) {
                edgeRepo.saveAll(edges);
                System.out.println("--- Reached " + ndCnt + " way nd elements and inserted into Edges table");
                edges.clear();
            }
        }
        edgeRepo.saveAll(edges);
        int total_edges = ndCnt - wayCnt;
        System.out.println("--- Finished inserting " + total_edges + " edges for " + wayCnt + " ways");
        OsmParser.wayCnt = wayCnt;
        OsmParser.edgeCnt = total_edges;
    }

    public static boolean excludeWay(List<org.dom4j.Node> wayChildrenTagNodes) {
        for (org.dom4j.Node node: wayChildrenTagNodes) {
            if (node.valueOf("@k").equals("highway")) {
                String v = node.valueOf("@v");
                if (v.equals("motorway") || v.equals("trunk")) {
                    System.out.println("--- Not inserting highway:motorway/trunk way");
                    excludedWays++;
                    return true;
                }
            }
        }
        return false;
    }

    @GetMapping("/importData")
    public String importData(@RequestParam String path) {
        File osmFile = new File(path);
        return parseOSMFile(osmFile);
    }

    public static void main(String[] args) {
        String filePath = "C:/Users/T450-180519/Downloads/map.osm";
        File osmFile = new File(filePath);
        parseOSMFile(osmFile);
    }

}
