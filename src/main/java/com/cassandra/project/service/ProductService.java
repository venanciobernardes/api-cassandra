package com.cassandra.project.service;

import com.cassandra.project.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product save(Product product);
    List<Product> findAll();
    Optional<Product> findById(int id);
    void delete(Product product);


}
