package com.EleNa.model.DataStructures;

//Class used for storing the Location a Node represents
public class Location{

    //Member variables
    protected double longitude;

    protected double latitude;

    protected double elevation;

    //Constructors
    public Location(double longitude, double latitude) throws IllegalArgumentException {
        //Check latitude and longitude are within acceptable range
        if(longitude < -180.0 || longitude > 180.0){
            throw new IllegalArgumentException("ERROR: Longitude out of bounds. Longitude was " + longitude + ". Expected: -180.0 <= longitude <= 180.0");
        }
        if(latitude < -90.0 || latitude > 90.0){
            throw new IllegalArgumentException("ERROR: Latitude out of bounds. Latitude was " + latitude + ". Expected: -90.0 <= latitude <= 90.0");
        }

        this.longitude = longitude;
        this.latitude = latitude;

        //Set dummy elevation. A Location created using this constructor should not be used as part of a Node
        this.elevation = 0.0;
    }

    public Location(double longitude, double latitude, double elevation) throws IllegalArgumentException {
        this(longitude, latitude);
        this.elevation = elevation;
    }

    //public methods

    //Returns the Location's longitude
    public double getLongitude(){
        return this.longitude;
    }

    //Returns the Location's latitude
    public double getLatitude(){
        return this.latitude;
    }

    //Returns the Location's elevation
    public double getElevation(){
        return this.elevation;
    }

}