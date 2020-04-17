package com.EleNa;

import com.EleNa.repositories.EdgeRepository;
import com.EleNa.repositories.NodeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class testOsmParser {
    @Autowired
    private static NodeRepository nodeRepo;
    @Autowired
    private static EdgeRepository edgeRepo;

    private static OsmParser importer;

    @BeforeAll
    public static void setUp() {
        importer = new OsmParser(nodeRepo, edgeRepo);
        MockitoAnnotations.initMocks(importer);
    }

    @Test
    public void test_given_osm_when_importData_then_Correct_nodes_ways() {
        String file = "C:\\Users\\T450-180519\\Documents\\Coding_Projects\\520-Project\\src\\main\\resources\\map_small_test.osm";
        importer.importData(file);
        assertEquals(50, importer.nodeCnt);
        assertEquals(4, importer.wayCnt);
        assertEquals(50, importer.edgeCnt);
    }


}
