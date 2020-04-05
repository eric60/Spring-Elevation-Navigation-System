package com.EleNa.model.DataStructures;

import com.EleNa.ElevationFinder;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.geom.Point;

import com.vividsolutions.jts.geom.Coordinate;
import org.hibernate.spatial.JTSGeometryType;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Point;

import javax.persistence.*;

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
}