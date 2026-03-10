package id.ac.ui.cs.advprog.eshop.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class Order {
    String id;
    List<Product> products;
    Long orderTime;
    String author;
    String status;

    // Constructor dengan 4 parameter (tanpa status)
    public Order(String id, List<Product> products, Long orderTime, String author) {
    }

    // Constructor dengan 5 parameter (dengan status)
    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
    }
}