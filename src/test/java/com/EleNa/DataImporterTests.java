package com.EleNa;

import com.EleNa.graph.Graph;
import com.EleNa.model.DataStructures.BufferNode;
import com.EleNa.model.DataStructures.Edge;
import com.EleNa.repositories.EdgeRepository;
import com.EleNa.repositories.EdgeRepositoryFillImpl;
import com.EleNa.repositories.NodeRepository;

import com.EleNa.repositories.NodeRepositoryFillImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.test.context.ActiveProfiles;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;

import static org.junit.Assert.*;


@ActiveProfiles("test")
public class DataImporterTests {
    @Mock
    private NodeRepositoryFillImpl nodeRepo;

    @Mock
    private EdgeRepositoryFillImpl edgeRepo;

    @InjectMocks
    private DataImporter dataImporter;

    private ArrayList<BufferNode> bufferNodes = new ArrayList<BufferNode>();
    private ArrayList<Edge> edges = new ArrayList<Edge>();

    String nodePath = "./src/main/resources/nodes.bin";
    String edgePath = "./src/main/resources/edges.bin";

    File nodeBin = new File(nodePath);
    File edgeBin = new File(edgePath);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        if(nodeBin.exists()){
            nodeBin.delete();
        }

        if(edgeBin.exists()){
            edgeBin.delete();
        }

        bufferNodes.add(new BufferNode(0, "POINT(0 0)", 0));
        bufferNodes.add(new BufferNode(1, "POINT(0 1)", 0));
        bufferNodes.add(new BufferNode(2, "POINT(0 2)", 0));
        bufferNodes.add(new BufferNode(3, "POINT(0 3)", 0));
        bufferNodes.add(new BufferNode(4, "POINT(0 4)", 0));
        bufferNodes.add(new BufferNode(5, "POINT(0 5)", 0));
        bufferNodes.add(new BufferNode(6, "POINT(1 1)", 0));
        bufferNodes.add(new BufferNode(7, "POINT(1 2)", 0));
        bufferNodes.add(new BufferNode(8, "POINT(2 1)", 0));
        bufferNodes.add(new BufferNode(9, "POINT(2 0)", 0));

        edges.add(new Edge(0, 0, 1, 1));
        edges.add(new Edge(1, 1, 2, 1));
        edges.add(new Edge(2, 2, 3, 1));
        edges.add(new Edge(3, 3, 4, 1));
        edges.add(new Edge(4, 4, 5, 1));
        edges.add(new Edge(5, 1, 6, 1));
        edges.add(new Edge(6, 6, 7, 1));
        edges.add(new Edge(7, 6, 8, 1));
        edges.add(new Edge(8, 8, 9, 1));

        System.out.println("trigger setup");
    }

    @AfterAll
    public static void cleanUp(){
        String nodePath = "./src/main/resources/nodes.bin";
        String edgePath = "./src/main/resources/edges.bin";

        File nodeBin = new File(nodePath);
        File edgeBin = new File(edgePath);

        if(nodeBin.exists()){
            nodeBin.delete();
        }

        if(edgeBin.exists()){
            edgeBin.delete();
        }
    }

    @Test
    public void test_given_query_response_when_getNodesEdges_then_correct_number_of_nodes_and_edges() throws ClassNotFoundException, IOException {

        Mockito.when(nodeRepo.getBufferNodes()).thenReturn(bufferNodes);
        Mockito.when(edgeRepo.getEdges()).thenReturn(edges);

        assertTrue(!nodeBin.exists());
        assertTrue(!edgeBin.exists());

        dataImporter = new DataImporter(nodeRepo, edgeRepo);
        Graph freshGraph = dataImporter.fillGraph();

        assertTrue(nodeBin.exists());
        assertTrue(edgeBin.exists());

        Graph binGraph = dataImporter.fillGraph();

        // Check initial creation of graph from DB is correct
        assertEquals(bufferNodes.size(), freshGraph.getNumNodes());
        assertEquals(2 * edges.size(), freshGraph.numEdges());

        // Check recreated graph from bin files is correct
        assertEquals(bufferNodes.size(), binGraph.getNumNodes());
        assertEquals(2 * edges.size(), binGraph.numEdges());
    }
}
