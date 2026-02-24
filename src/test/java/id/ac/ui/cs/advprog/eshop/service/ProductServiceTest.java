package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("test-id");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
    }

    @Test
    void testCreate() {
        when(productRepository.create(any(Product.class))).thenReturn(product);
        Product result = productService.create(product);
        assertNotNull(result);
        assertEquals("Test Product", result.getProductName());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productRepository.findAll()).thenReturn(productList.iterator());
        
        List<Product> result = productService.findAll();
        
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getProductName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(productRepository.findById("test-id")).thenReturn(product);
        Product result = productService.findById("test-id");
        assertNotNull(result);
        assertEquals("test-id", result.getProductId());
        verify(productRepository, times(1)).findById("test-id");
    }

    @Test
    void testUpdate() {
        when(productRepository.update(anyString(), any(Product.class))).thenReturn(product);
        Product result = productService.update("test-id", product);
        assertNotNull(result);
        verify(productRepository, times(1)).update("test-id", product);
    }

    @Test
    void testDelete() {
    when(productRepository.delete("test-id")).thenReturn(product);
    productService.delete("test-id");
    verify(productRepository, times(1)).delete("test-id");
    }
}