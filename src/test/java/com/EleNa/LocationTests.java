package com.EleNa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import com.EleNa.model.DataStructures.Location;

class LocationTests {

    private Location location1, location2;

    @BeforeEach
    void startUp(){
        location1 = new Location(45.0, 90.0);
        location2 = new Location(90.0, 45.0, 100.0);
    }
    @AfterEach
    void tearDown() {
        location1 = null;
        location2 = null;
    }

	@Test
	public void testFirstConstructor(){
        assertNotEquals(null, location1);
    }

    @Test
    public void testSecondConstructor(){
        assertNotEquals(null, location2);
    }

    @Test
    public void testInvalidLongitudeUpperBound(){
        assertThrows(IllegalArgumentException.class, () -> new Location(200.0, 45.0));
    }

    @Test
    public void testInvalidLongitudeLowerBound(){
        assertThrows(IllegalArgumentException.class, () -> new Location(-200, 45.0));
    }

    @Test
    public void testInvalidLatitudeUpperBound(){
        assertThrows(IllegalArgumentException.class, () -> new Location(45.0, 200.0));
    }

    @Test
    public void testInvalidLatitudeLowerBound(){
        assertThrows(IllegalArgumentException.class, () -> new Location(45.0, -200.0));
    }

    @Test
    public void testGetLongitude(){
        assertEquals(45.0, location1.getLongitude());
        assertEquals(90.0, location2.getLongitude());
    }

    @Test
    public void testGetLatitude(){
        assertEquals(90.0, location1.getLatitude());
        assertEquals(45.0, location2.getLatitude());
    }

    @Test
    public void testGetElevation(){
        assertEquals(0.0, location1.getElevation());
        assertEquals(100.0, location2.getElevation());
    }

}