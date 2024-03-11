package com.cassandra.project.service.impl;

import com.cassandra.project.model.Product;
import com.cassandra.project.repository.ProductRepository;
import com.cassandra.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;



    public Product save(Product product){
       return productRepository.save(product);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Optional<Product> findById(int id){
        return productRepository.findById(id);
    }
    public void delete(Product product){
        productRepository.delete(product);
    }


}
