package com.EleNa.repositories;

import com.EleNa.model.DataStructures.Edge;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface EdgeRepositoryFill {
    @Query(value = "select * " +
            "from edges e " +
            "order by e.src", nativeQuery = true)
    ArrayList<Edge> getEdges();
}
