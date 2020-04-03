package com.EleNa;

import com.EleNa.model.DataStructures.Node;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import com.EleNa.ElevationFinder;

public class ElevationFinderTests {
    ElevationFinder ef;
    double longA;
    double latA;

    @BeforeEach
    void startUp(){
        ef = new ElevationFinder();
        longA = -72.5614722;
        latA = 42.3653286;
    }

    @AfterEach
    void tearDown(){
        longA = 0;
        latA = 0;
    }

    @Test
    void getElevationTest() throws Exception {
        double elevation = ef.getElevation(longA, latA);
        assertEquals(elevation, 85.22);
    }
}
