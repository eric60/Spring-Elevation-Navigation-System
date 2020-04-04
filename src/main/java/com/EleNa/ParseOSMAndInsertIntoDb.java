package com.EleNa;

import com.EleNa.repositories.CustomerRepository;
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
    List<Node> nodes;
    List<Edge> edges;

    public ParseOSMAndInsertIntoDb() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public static void parseOSMFile(File osmFile) {
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read( osmFile );
            System.out.println("Root element :" + document.getRootElement().getName());

            List<org.dom4j.Node> nodeNodes = document.selectNodes("/osm/node" );
            List<org.dom4j.Node> wayNodes = document.selectNodes("/osm/way" );

            System.out.println(nodeNodes.size());

            int i = 0;
            for (org.dom4j.Node node : nodeNodes) {
                String currNode = node.getName();
                System.out.println(currNode);
                String nodeId = node.valueOf("@id");
                String lon = node.valueOf("@lon");
                String lat = node.valueOf("@lat");
                System.out.println(nodeId);
                i++;
                if(i == 1000) {
                    System.out.println("1000 elements Inserting into Nodes table");
                }
            }
            int j = 0;
            for(org.dom4j.Node wayNode : wayNodes) {
                String currNode = wayNode.getName();
                List<org.dom4j.Node> wayChildrenNodes = wayNode.selectNodes("/way/nd");

                int ndIdx = 1;
                for(org.dom4j.Node ndRefNode : wayChildrenNodes) {

                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String filePath = "C:/Users/T450-180519/Downloads/map.osm";
        File osmFile = new File(filePath);
        parseOSMFile(osmFile);
    }

}
