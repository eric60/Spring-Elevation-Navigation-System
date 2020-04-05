package com.EleNa;

import com.EleNa.repositories.EdgeRepository;
import com.EleNa.repositories.NodeRepository;
import com.EleNa.repositories.NodeRepositoryCustom;
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

import org.postgis.Point;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class ParseOSMAndInsertIntoDb {

    private static NodeRepository nodeRepo;
    private static EdgeRepository edgeRepo;
    private static NodeRepositoryCustom  nodeRepoCustom;

    private static List<Node> nodes = new ArrayList<>();
    private static List<Edge> edges = new ArrayList<>();

    @Autowired
    public ParseOSMAndInsertIntoDb(NodeRepository nodeRepo, EdgeRepository edgeRepo, NodeRepositoryCustom nodeRepoCustom) {
        this.nodeRepo = nodeRepo;
        this.edgeRepo = edgeRepo;
        this.nodeRepoCustom = nodeRepoCustom;
    }

    public static String parseOSMFile(File osmFile){
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read( osmFile );
            System.out.println("Root element :" + document.getRootElement().getName());

            List<org.dom4j.Node> nodeNodes = document.selectNodes("/osm/node" );
            List<org.dom4j.Node> wayNodes = document.selectNodes("/osm/way" );

            System.out.println("nodes size: " + nodeNodes.size());
            System.out.println("ways size: " + wayNodes.size());

            // Parsing Node Nodes
            int i = 0;
            for (org.dom4j.Node node : nodeNodes) {
                String nodeId = node.valueOf("@id");
                String lon = node.valueOf("@lon");
                String lat = node.valueOf("@lat");

                Coordinate coordinate = new Coordinate(Double.parseDouble(lon), Double.parseDouble(lat));
                Node osmNode = new Node(Long.parseLong(nodeId), coordinate);
                nodes.add(osmNode);
                i++;
                if(i % 10 == 0) {
                    nodeRepo.saveAll(nodes);
                    System.out.println("Reached 100 node elements. Inserted 100 nodes into Nodes table");
                    nodes.clear();
                }
            }
            System.out.println("---finished nodes size: " + nodes.size());

            // Parsing way Nodes
            int j = 0;
            Edge prev = null;
            for(org.dom4j.Node wayNode : wayNodes) {
                List<org.dom4j.Node> wayChildrenNodes = wayNode.selectNodes(".//nd");
                System.out.println("wayChildrenNodes for " + wayNode.getName()+ ": " + wayChildrenNodes.size());
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
                        System.out.println("last Way elem");
                    }
                    ndIdx++;
                }
                j++;
                if(j % 100 == 0) {
                    System.out.println("Reached 100 way nd elements. Inserting edges into edges table");
                    edgeRepo.saveAll(edges);
                    edges.clear();
                }
            }
            System.out.println("--Finished edges size: " + edges.size());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "<h1>Imported node and way data";
    }

    @GetMapping("/importData")
    public void importData(@RequestParam String path) {
        File osmFile = new File(path);
        parseOSMFile(osmFile);
    }

    public static void main(String[] args) {
        String filePath = "C:/Users/T450-180519/Downloads/map.osm";
        File osmFile = new File(filePath);
        parseOSMFile(osmFile);
    }

}
