package com.EleNa.repositories;

import com.EleNa.model.DataStructures.Edge;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * This interface helps us do all CRUD functions for class Customer.
 **/
public interface EdgeRepository extends CrudRepository<Edge, Long>{

}
