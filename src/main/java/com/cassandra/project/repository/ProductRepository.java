package com.cassandra.project.repository;

import com.cassandra.project.model.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends CassandraRepository<Product,Integer> {

}
