package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    
    private List<Product> products;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        this.products.add(product1);

        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        this.products.add(product2);
    }

    // Test 4: Unhappy - create order with empty products
    @Test
    void testCreateOrderEmptyProduct() {
        this.products.clear();
        
        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order("13652556-012a-4c07-b546-54eb1396479b",
                this.products, 1708560000L, "Safira Sudrajat");
        });
    }

    // Test 5: Happy - create order with no status defined (default WAITING_PAYMENT)
    @Test
    void testCreateOrderDefaultStatus() {
        Order order = new Order("13652556-012a-4c07-b546-54eb1396479b",
            this.products, 1708560000L, "Safira Sudrajat");

        assertSame(this.products, order.getProducts());
        assertEquals(2, order.getProducts().size());
        assertEquals("Sampo Cap Bambang", order.getProducts().get(0).getProductName());
        assertEquals("Sabun Cap Usep", order.getProducts().get(1).getProductName());
        assertEquals("13652556-012a-4c07-b546-54eb1396479b", order.getId());
        assertEquals(1708560000L, order.getOrderTime());
        assertEquals("Safira Sudrajat", order.getAuthor());
        assertEquals("WAITING_PAYMENT", order.getStatus());
    }

    // Test 6: Happy - create order with SUCCESS status
    @Test
    void testCreateOrderSuccessStatus() {
        Order order = new Order("13652556-012a-4c07-b546-54eb1396479b",
            this.products, 1708560000L, "Safira Sudrajat", "SUCCESS");
        
        assertEquals("SUCCESS", order.getStatus());
    }

    // Test 7: Unhappy - create order with invalid status
    @Test
    void testCreateOrderInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order("13652556-012a-4c07-b546-54eb1396479b",
                this.products, 1708560000L, "Safira Sudrajat", "MEOW");
        });
    }

    // Test 8: Happy - edit order status to CANCELLED
    @Test
    void testSetStatusToCancelled() {
        Order order = new Order("13652556-012a-4c07-b546-54eb1396479b",
            this.products, 1708560000L, "Safira Sudrajat");
        order.setStatus("CANCELLED");
        
        assertEquals("CANCELLED", order.getStatus());
    }

    // Test 9: Unhappy - edit order status to invalid status
    @Test
    void testSetStatusToInvalidStatus() {
        Order order = new Order("13652556-012a-4c07-b546-54eb1396479b",
            this.products, 1708560000L, "Safira Sudrajat");
        
        assertThrows(IllegalArgumentException.class, () -> {
            order.setStatus("MEOW");
        });
    }
}