package com.EleNa.repositories;

import com.EleNa.JPAUtil;
import com.EleNa.model.DataStructures.Edge;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;

@Repository
public class EdgeRepositoryFillImpl implements EdgeRepositoryFill{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ArrayList<Edge> getEdges() {
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        ArrayList<Edge> edges;

        String queryString = "select * " +
                "from edges e order by e.src";

        Query query = entityManager.createNativeQuery(queryString, Edge.class);

        edges = new ArrayList<Edge>(query.getResultList());

        entityManager.close();

        return edges;
    }
}