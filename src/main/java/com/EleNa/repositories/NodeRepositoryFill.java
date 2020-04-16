package com.EleNa.repositories;

import com.EleNa.graph.Graph;
import com.EleNa.graph.GraphNode;
import com.EleNa.model.DataStructures.BufferNode;
import com.EleNa.model.DataStructures.Node;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import com.vividsolutions.jts.geom.Point;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface NodeRepositoryFill {
    @Query("select * from public.nodes n where " +
            "ST_DWithin(n.point, ST_MakeLine(ST_MakePoint(:sourceLong, :sourceLat), " +
            "ST_MakePoint(:sinkLong, :sinkLat)), :searchRadius);")
    Graph getLocalPoints(double sourceLong, double sourceLat, double sinkLong, double sinkLat, int searchRadius);

    long getClosestID(double lon, double lat);
}
