package com.EleNa.model.DataStructures;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "nodes")
public class BufferNode implements Serializable {
    @Id
    private long id;

    @Column(name = "point")
    private String point;

    @Column(name = "elevation")
    private double elevation;
    /*
    @Column(name = "src", nullable = true)
    private Long src;

    @Column(name = "dest", nullable = true)
    private Long dest;
    */
    //NOTE: elevation is expected in meters
    public BufferNode(long id, String point, double elevation){
        this.id = id;
        this.point = point;
        this.elevation = elevation;
    }

    public BufferNode(){}

    public long getId() {
        return this.id;
    }

    public String getPoint() {
        return this.point;
    }

    public double getElevation() {
        return this.elevation;
    }

    public double getLongitude() {
        int startIndexLon = point.indexOf('(') + 1;
        int endIndexLon = point.indexOf(' ');
        double lon = Double.parseDouble(point.substring(startIndexLon, endIndexLon));
        return lon;
    }

    public double getLatitude() {
        int startIndexLat = point.indexOf(' ') + 1;
        int endIndexLat = point.indexOf(')');
        double lat = Double.parseDouble(point.substring(startIndexLat, endIndexLat));
        return lat;
    }
}
