package com.EleNa.model.DataStructures;

import com.EleNa.ElevationFinder;

//import org.locationtech.jts.geom.Coordinate;
//import org.locationtech.jts.geom.GeometryFactory;
//import org.locationtech.jts.geom.Point;
//import org.locationtech.jts.geom.PrecisionModel;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

import org.hibernate.spatial.JTSGeometryType;
//import org.locationtech.jts.geom.PrecisionModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "nodes")
public class Node {
    @Id
    private long id;

    @Column(name = "point")
    private Point point;

    @Column(name = "elevation")
    private double elevation;

    //NOTE: elevation is expected in meters
    public Node(long id, Coordinate coordinate){
        this.id = id;
        GeometryFactory gf = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
        this.point = gf.createPoint(coordinate);
        try {
            this.elevation = ElevationFinder.getElevation(coordinate.x, coordinate.y);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Node(){}

    public long getId() {
        return this.id;
    }

    public Point getPoint() {
        return this.point;
    }

    public double getElevation() {
        return this.elevation;
    }
}