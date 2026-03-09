package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class Order {
    private String id;
    private List<Product> products;
    private Long orderTime;
    private String author;
    private String status;

    public Order() {
        this.id = UUID.randomUUID().toString();
        this.products = new ArrayList<>();
        this.orderTime = System.currentTimeMillis();
        this.status = "WAITING_PAYMENT";
    }

    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
        this.id = id;
        this.products = products;
        this.orderTime = orderTime;
        this.author = author;
        setStatus(status);
    }

    public void setStatus(String status) {
        if (status != null && !isValidStatus(status)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
        this.status = status;
    }

    private boolean isValidStatus(String status) {
        return status.equals("WAITING_PAYMENT") ||
               status.equals("FAILED") ||
               status.equals("CANCELLED") ||
               status.equals("SUCCESS");
    }

    public void setProducts(List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Products cannot be null or empty");
        }
        this.products = products;
    }
}