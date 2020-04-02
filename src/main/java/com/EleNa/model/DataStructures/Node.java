package com.EleNa.model.DataStructures;

import org.springframework.data.geo.Point;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "nodes")
public class Node{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected double id;

    @Column(name = "geog")
    protected Point geog;

    @Column(name = "elevation")
    protected double elevation;

    protected Node() {

    }

    //NOTE: elevation is expected in meters
    public Node(int id, double longitude, double latitude, double elevation){
        this.id = id;
//        this.location = new Location(longitude, latitude, elevation);
//        this.edges = new ArrayList<Edge>();
    }


}