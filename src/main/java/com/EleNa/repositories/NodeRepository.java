package com.EleNa.repositories;

import com.EleNa.model.DataStructures.Node;
import org.springframework.data.repository.CrudRepository;

/**
 * This interface helps us do all CRUD functions for class Customer.
 **/
public interface NodeRepository extends CrudRepository<Node, Long>{

}
