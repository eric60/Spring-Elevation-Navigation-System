package com.EleNa.model.DataStructures;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "edges")
public class Edge {
    @Column(name = "src")
    private long src;

    @Column(name = "dest")
    private long dest;

    @Column(name = "geog")
    private double distance;

    public Edge(long src) {
        this.src = src;
    }

    public Edge(long src, long dest) {
        this.src = src;
        this.dest = dest;

    }

    public void setSrc(long src) {
        this.src = src;
    }

    public void setDest(long dest) {
        this.dest = dest;
    }

}