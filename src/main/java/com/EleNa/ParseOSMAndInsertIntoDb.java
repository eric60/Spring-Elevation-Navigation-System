package com.EleNa;

import com.EleNa.repositories.EdgeRepository;
import com.EleNa.repositories.NodeRepository;
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

public class ParseOSMAndInsertIntoDb {
    @Autowired
    NodeRepository nodeRepo;

    @Autowired
    EdgeRepository edgeRepo;

    String filePath = "C:/Users/T450-180519/Downloads/map.osm";
    private File osmFile = new File(filePath);
    private static List<Node> nodes = new ArrayList<>();
    private static List<Edge> edges = new ArrayList<>();

    public ParseOSMAndInsertIntoDb() {
    }

    public static void parseOSMFile(File osmFile) {
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
                String currNode = node.getName();

                String nodeId = node.valueOf("@id");
                String lon = node.valueOf("@lon");
                String lat = node.valueOf("@lat");

                Point point = new Point(Double.parseDouble(lon), Double.parseDouble(lat));
                Node osmNode = new Node(Long.parseLong(nodeId), point);
                nodes.add(osmNode);
                i++;
                if(i % 100 == 0) {
//                    System.out.println("Reached 100  elements. Inserting nodes into Nodes table");
//                    nodes.clear();
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
                        System.out.println("last elem");
                    }
                    ndIdx++;
                }
            }
            System.out.println("--Finished edges size: " + edges.size());
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public static void commitElementsToDb(String elemType) {

    }

    public static void main(String[] args) {
        String filePath = "C:/Users/T450-180519/Downloads/map.osm";
        File osmFile = new File(filePath);
        parseOSMFile(osmFile);
    }

}
