package com.cassandra.project.controller;

import com.cassandra.project.exceptions.ResourceNotFoundException;
import com.cassandra.project.model.Product;
import com.cassandra.project.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import static org.hamcrest.core.Is.is;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    private MockMvc mockMvc;

    @Test
    void addProduct() throws Exception {
        Product mockProduct = new Product();
        given(productService.save(any(Product.class))).willReturn(mockProduct);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void returnOk() throws Exception {
        mockMvc.perform(get("/api/products/ok"))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
    }

    @Test
    void findById() throws Exception {
        Integer productId = 1;
        Product mockProduct = new Product();
        given(productService.findById(productId)).willReturn(Optional.of(mockProduct));

        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void findById_ResourceNotFoundException() throws Exception {
        Integer productId = 1;
        given(productService.findById(productId)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getProducts() throws Exception {
        List<Product> mockProducts = Collections.singletonList(new Product());
        given(productService.findAll()).willReturn(mockProducts);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void updateProduct() throws Exception {
        Integer productId = 1;
        Product productDetails = new Product();
        productDetails.setName("UpdatedProduct");

        Product mockProduct = new Product();
        given(productService.findById(productId)).willReturn(Optional.of(mockProduct));
        given(productService.save(mockProduct)).willReturn(productDetails);

        mockMvc.perform(put("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"UpdatedProduct\"}"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name", BDDMockito.eq("UpdatedProduct")));
    }

    @Test
    void updateProduct_ResourceNotFoundException() throws Exception {
        Integer productId = 1;
        given(productService.findById(productId)).willReturn(Optional.empty());

        mockMvc.perform(put("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"UpdatedProduct\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct() throws Exception {
        Integer productId = 1;
        Product mockProduct = new Product();
        given(productService.findById(productId)).willReturn(Optional.of(mockProduct));

        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProduct_ResourceNotFoundException() throws Exception {
        Integer productId = 1;
        given(productService.findById(productId)).willReturn(Optional.empty());

        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isNotFound());
    }
}
