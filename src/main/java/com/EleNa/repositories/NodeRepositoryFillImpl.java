package com.EleNa.repositories;

import com.EleNa.JPAUtil;
import com.EleNa.model.DataStructures.BufferNode;
import com.EleNa.model.DataStructures.Node;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;

@Repository
public class NodeRepositoryFillImpl implements NodeRepositoryFill{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long getClosestID(double lon, double lat) {
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        long closestID;

        String queryString = "select n.id from nodes n " +
                "order by ST_Distance(ST_MakePoint(:lon, :lat), n.point) " +
                "limit 1";

        Query query = entityManager.createNativeQuery(queryString);

        query.setParameter("lon", lon);
        query.setParameter("lat", lat);

        closestID = (new BigInteger(query.getSingleResult().toString())).longValue();

        return closestID;
    }

    @Override
    public ArrayList<BufferNode> getBufferNodes() {
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        ArrayList<BufferNode> nodes;

        String queryString = "select n.id, ST_AsText(n.point) as point, n.elevation, n.src, n.dest " +
                "from nodesAndEdges n order by n.id";

        Query query = entityManager.createNativeQuery(queryString, BufferNode.class);

        nodes = new ArrayList<BufferNode>(query.getResultList());

        entityManager.close();

        return nodes;
    }
}
