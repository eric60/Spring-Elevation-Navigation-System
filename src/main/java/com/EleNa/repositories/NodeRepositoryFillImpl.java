package com.EleNa.repositories;

import com.EleNa.graph.Graph;
import com.EleNa.graph.GraphNode;
import com.EleNa.model.DataStructures.Edge;
import com.EleNa.model.DataStructures.Node;
import com.EleNa.model.DataStructures.BufferNode;
import com.EleNa.JPAUtil;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.data.jpa.repository.Query;
import javax.persistence.Query;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Dictionary;

import com.vividsolutions.jts.geom.Point;
import java.sql.PreparedStatement;
import java.sql.Connection;

@Repository
public class NodeRepositoryFillImpl implements NodeRepositoryFill{

    //EntityManagerFactory emf = Persistence.createEntityManagerFactory("javax.persistence-api");

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Graph getLocalPoints(double sourceLong, double sourceLat, double sinkLong, double sinkLat, int searchRadius) {
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        ArrayList<BufferNode> buffNodes;
        ArrayList<Long> keys = new ArrayList<Long>();
        Graph graphNodes = new Graph();
        ArrayList<Edge> edges = new ArrayList<Edge>();

        String queryString = "select n.id, ST_AsText(n.point) as point, n.elevation, e.src, e.dest " +
                "from nodes n inner join edges e on n.id=e.src "+
                "where ST_DWithin(n.point, ST_MakeLine(ST_MakePoint(:sourceLong, :sourceLat), " +
                "ST_MakePoint(:sinkLong, :sinkLat)), :searchRadius);";

        Query query = entityManager.createNativeQuery(queryString, BufferNode.class);

        query.setParameter("sourceLong", sourceLong);
        query.setParameter("sourceLat", sourceLat);
        query.setParameter("sinkLong", sinkLong);
        query.setParameter("sinkLat", sinkLat);
        query.setParameter("searchRadius", searchRadius);
        System.out.println(query.toString());

        //System.out.println(query.getResultList());

        buffNodes = new ArrayList<BufferNode>(query.getResultList());


        entityManager.close();

        BufferNode temp;

        for(int i=0; i<buffNodes.size(); i++) {
            temp = buffNodes.get(i);
            graphNodes.addNode(new GraphNode(temp.getId(), temp.getLongitude(),
                    temp.getLatitude(), temp.getElevation()));
            if (!keys.contains(new Long(temp.getId()))) {
                keys.add(new Long(temp.getId()));
            }

            edges.add(new Edge(temp.getSrc(), temp.getDest()));
        }

        GraphNode curr;
        boolean neighbor = true;
        for(int i=0; i<graphNodes.getNumNodes(); i++) {
            curr = graphNodes.getNodeById(keys.get(i).longValue());
            if(curr.getId() == edges.get(0).getSrc()) {     // If node is the source of an edge
                if(!curr.getNeighbors().contains(edges.get(0).getSrc()) && keys.contains(edges.get(0).getDest())) {    // If neighbor doesn't already exist
                    curr.addNeighbor(graphNodes.getNodeById(edges.remove(0).getDest()));
                }
            }
        }

        return graphNodes;
    }

    @Override
    @Transactional
    public long getClosestID(double lon, double lat) {
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        long closestID;

        String queryString = "select n.id from nodes n " +
                "order by ST_Distance(ST_MakePoint(:lon, :lat), n.point)" +
                "limit 1;";

        Query query = entityManager.createNativeQuery(queryString);

        query.setParameter("lon", lon);
        query.setParameter("lat", lat);

        closestID = (new BigInteger(query.getSingleResult().toString())).longValue();

        return closestID;
    }
}
