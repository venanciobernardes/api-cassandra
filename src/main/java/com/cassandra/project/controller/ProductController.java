package com.cassandra.project.controller;

import com.cassandra.project.exceptions.ResourceNotFoundException;
import com.cassandra.project.model.Product;
import com.cassandra.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product){
        productService.save(product);
        return product;

    }

    @GetMapping("/products/ok")
    public ResponseEntity<String> returnOk(){
        return ResponseEntity.ok("ok");
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") Integer productId){
        Product product=productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found" + productId));
        return ResponseEntity.ok().body(product);
    }


    @GetMapping("/products")
    public List<Product> getProducts(){
        return productService.findAll();
    }

    @PutMapping("products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Integer productId,
                                                 @RequestBody Product productDetails) {
        Product product = productService.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));
        product.setName(productDetails.getName());
        final Product updatedProduct = productService.save(product);
        return ResponseEntity.ok(updatedProduct);

    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value = "id") Integer productId) {
        Product product = productService.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not found::: " + productId));
        productService.delete(product);
        return ResponseEntity.ok().build();
    }

}
