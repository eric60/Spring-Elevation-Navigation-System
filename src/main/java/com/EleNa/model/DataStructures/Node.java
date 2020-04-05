package com.EleNa.model.DataStructures;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import org.hibernate.annotations.Type;
import com.vividsolutions.jts.geom.Point;

import com.vividsolutions.jts.geom.Coordinate;
//import org.postgis.Point;
import org.hibernate.spatial.GeolatteGeometryType;

import javax.persistence.*;

@Entity
@Table(name = "nodes")
public class Node {
    @Id
    private long id;

    @Column(name = "point", columnDefinition = "Geometry", nullable = true)
    @Type(type="org.hibernate.spatial.JTSGeometryType")
    private Point point;

    @Column(name = "elevation")
    private double elevation;

    //NOTE: elevation is expected in meters
    public Node(long id, Coordinate coordinate){
        this.id = id;
        GeometryFactory gf = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
        System.out.println(gf.getSRID());
        System.out.println("id: " + id + "Cooridnate x " + coordinate.x);
        System.out.println("Corrdinate y " + coordinate.y);
        this.point = gf.createPoint(coordinate);
        this.elevation = 1.0;
    }

    public Node(){}
}