package com.EleNa.model.DataStructures;

import javax.persistence.*;

@Entity
@Table(name = "edges")
public class Edge {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "src")
    private long src;

    @Column(name = "dest")
    private long dest;

    @Column(name = "distance")
    private double distance;

    public Edge(long src) {
        this.src = src;
    }

    public Edge(long src, long dest) {
        this.src = src;
        this.dest = dest;
    }

    public Edge(){}

    public void setSrc(long src) {
        this.src = src;
    }

    public void setDest(long dest) {
        this.dest = dest;
    }

}