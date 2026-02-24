package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("test-id");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
    }

    @Test
    void testCreateProductPage() {
        String viewName = productController.createProductPage(model);
        assertEquals("CreateProduct", viewName);
        verify(model, times(1)).addAttribute(eq("product"), any(Product.class));
    }

    @Test
    void testCreateProductPost() {
        when(productService.create(any(Product.class))).thenReturn(product);
        String result = productController.createProductPost(product, model);
        assertEquals("redirect:list", result);
        verify(productService, times(1)).create(product);
    }

    @Test
    void testProductListPage() {
        List<Product> products = new ArrayList<>();
        products.add(product);
        when(productService.findAll()).thenReturn(products);

        String viewName = productController.productListPage(model);

        assertEquals("ProductList", viewName);
        verify(model, times(1)).addAttribute("products", products);
        verify(productService, times(1)).findAll();
    }

    @Test
    void testEditProductPage() {
        when(productService.findById("test-id")).thenReturn(product);
        String viewName = productController.editProductPage("test-id", model);
        assertEquals("EditProduct", viewName);
        verify(model, times(1)).addAttribute("product", product);
        verify(productService, times(1)).findById("test-id");
    }

    @Test
    void testEditProductPost() {
        when(productService.update(anyString(), any(Product.class))).thenReturn(product);
        String result = productController.editProductPost(product, model);
        assertEquals("redirect:list", result);
        verify(productService, times(1)).update(product.getProductId(), product);
    }

    @Test
    void testDeleteProduct() {
    doNothing().when(productService).delete("test-id");
    String result = productController.deleteProduct("test-id");
    assertEquals("redirect:/product/list", result);
    verify(productService, times(1)).delete("test-id");
    }
}