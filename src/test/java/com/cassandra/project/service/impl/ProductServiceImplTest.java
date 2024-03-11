package com.cassandra.project.service.impl;

import com.cassandra.project.model.Product;
import com.cassandra.project.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void save() {
        Product mockProduct = new Product();
        when(productRepository.save(any(Product.class))).thenReturn(mockProduct);

        Product result = productService.save(new Product());

        assertNotNull(result);
        assertEquals(mockProduct, result);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void findAll() {
        List<Product> mockProducts = Collections.singletonList(new Product());
        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertEquals(mockProducts, result);
    }

    @Test
    void findById() {
        Integer productId = 1;
        Product mockProduct = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        Optional<Product> result = productService.findById(productId);

        assertTrue(result.isPresent());
        assertEquals(mockProduct, result.get());
    }

    @Test
    void findById_NotFound() {
        Integer productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Optional<Product> result = productService.findById(productId);

        assertFalse(result.isPresent());
    }

    @Test
    void delete() {
        Product mockProduct = new Product();

        assertDoesNotThrow(() -> productService.delete(mockProduct));

        verify(productRepository, times(1)).delete(mockProduct);
    }
}