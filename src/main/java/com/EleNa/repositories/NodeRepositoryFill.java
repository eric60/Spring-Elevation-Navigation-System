package com.EleNa.repositories;

import com.EleNa.graph.Graph;
import com.EleNa.graph.GraphNode;
import com.EleNa.model.DataStructures.BufferNode;
import com.EleNa.model.DataStructures.Node;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface NodeRepositoryFill {
    @Query(value = "select n.id, ST_AsText(n.point) as point, n.elevation, n.src, n.dest " +
            "from nodesAndEdges n " +
            "where ST_DWithin(n.point, ST_MakeLine(ST_MakePoint(:sourceLong, :sourceLat), " +
            "ST_MakePoint(:sinkLong, :sinkLat)), :searchRadius)", nativeQuery = true)
    Graph getLocalPoints(double sourceLong, double sourceLat, double sinkLong, double sinkLat, int searchRadius);

    @Query(value = "select n.id from nodes n " +
            "order by ST_Distance(ST_MakePoint(:lon, :lat), n.point) " +
            "limit 1", nativeQuery = true)
    long getClosestID(double lon, double lat);
}
