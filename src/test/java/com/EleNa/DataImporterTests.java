package com.EleNa;

import com.EleNa.repositories.EdgeRepository;
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
import static org.junit.Assert.assertEquals;


@ActiveProfiles("test")
public class DataImporterTests {
    @Mock
    private NodeRepository nodeRepo;

    @Mock
    private EdgeRepository edgeRepo;

    @Mock
    private NodeRepositoryFillImpl nodeFillRepo;

    @InjectMocks
    private DataImporter dataImporter;

    private OsmParser importer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        System.out.println("trigger setup");
    }

    @Test
    public void test_given_osm_when_importData_then_Correct_number_of_nodes_and_ways() {
        //String windowsFile = "C:\\Users\\T450-180519\\Documents\\Coding_Projects\\520-Project\\src\\main\\resources\\map_small_test.osm";
        String macFile = "/Users/JohnBurns/Desktop/CS520/Elena/520-Project/src/main/resources/map_small_test.osm";
        Mockito.when(nodeRepo.saveAll(null)).thenReturn(null);
        Mockito.when(edgeRepo.saveAll(null)).thenReturn(null);
        importer = new OsmParser(nodeRepo, edgeRepo);
        importer.importData(macFile);
        assertEquals(50, importer.nodeCnt);
        assertEquals(3, importer.wayCnt);
        assertEquals(45, importer.edgeCnt);
    }


}
