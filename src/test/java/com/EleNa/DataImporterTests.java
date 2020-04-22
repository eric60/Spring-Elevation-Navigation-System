package com.EleNa;

import com.EleNa.graph.Graph;
import com.EleNa.model.DataStructures.BufferNode;
import com.EleNa.model.DataStructures.Edge;
import com.EleNa.repositories.EdgeRepository;
import com.EleNa.repositories.EdgeRepositoryFillImpl;
import com.EleNa.repositories.NodeRepository;

import com.EleNa.repositories.NodeRepositoryFillImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.test.context.ActiveProfiles;

import java.nio.Buffer;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


@ActiveProfiles("test")
public class DataImporterTests {
    @Mock
    private NodeRepositoryFillImpl nodeRepo;

    @Mock
    private EdgeRepositoryFillImpl edgeRepo;

    @InjectMocks
    private DataImporter dataImporter;

    private Graph myGraph;
    private ArrayList<BufferNode> bufferNodes = new ArrayList<BufferNode>();
    private ArrayList<Edge> edges = new ArrayList<Edge>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

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

    @Test
    public void test_given_query_response_when_getNodesEdges_then_correct_number_of_nodes_and_edges() {

        Mockito.when(nodeRepo.getBufferNodes()).thenReturn(bufferNodes);
        Mockito.when(edgeRepo.getEdges()).thenReturn(edges);

        dataImporter = new DataImporter(nodeRepo, edgeRepo);
        myGraph = dataImporter.fillGraph();

        assertEquals(bufferNodes.size(), myGraph.getNumNodes());
        assertEquals(2 * edges.size(), myGraph.numEdges());

    }
}
