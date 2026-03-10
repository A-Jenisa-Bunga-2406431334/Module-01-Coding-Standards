package id.ac.ui.cs.advprog.eshop.model;

import java.util.Arrays;
import java.util.List;

public class Order {
    String id;
    List<Product> products;
    Long orderTime;
    String author;
    String status;

    // Step 2: Constructor tanpa status parameter
    public Order(String id, List<Product> products, Long orderTime, String author) {
        this.id = id;
        this.orderTime = orderTime;
        this.author = author;
        this.status = "WAITING_PAYMENT";
        
        if (products.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.products = products;
        }
    }

    // Step 4: Constructor dengan status parameter
    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
        this(id, products, orderTime, author);
        
        String[] statusList = {"WAITING_PAYMENT", "FAILED", "SUCCESS", "CANCELLED"};
        if (Arrays.stream(statusList).noneMatch(item -> (item.equals(status)))) {
            throw new IllegalArgumentException();
        } else {
            this.status = status;
        }
    }

    // Step 7: setStatus method
    public void setStatus(String status) {
        String[] statusList = {"WAITING_PAYMENT", "FAILED", "SUCCESS", "CANCELLED"};
        if (Arrays.stream(statusList).noneMatch(item -> (item.equals(status)))) {
            throw new IllegalArgumentException();
        } else {
            this.status = status;
        }
    }

    // Getter methods
    public String getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Long getOrderTime() {
        return orderTime;
    }

    public String getAuthor() {
        return author;
    }

    public String getStatus() {
        return status;
    }
}