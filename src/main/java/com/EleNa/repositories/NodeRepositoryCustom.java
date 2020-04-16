package com.EleNa.repositories;

import com.EleNa.model.DataStructures.Node;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;

@Repository
public class NodeRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertWithQuery(List<Node> nodes) {
        for(Node node : nodes) {
            entityManager.persist(node);
        }
    }
}
