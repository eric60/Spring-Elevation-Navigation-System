package com.EleNa.model.DataStructures;

import org.postgis.Point;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "nodes")
public class Node {
    @Id
    private long id;

    @Column(name = "geog")
    private Point point;

    @Column(name = "elevation")
    private double elevation;

    //NOTE: elevation is expected in meters
    public Node(long id, Point point){
        this.id = id;
        this.point = point;
    }


}