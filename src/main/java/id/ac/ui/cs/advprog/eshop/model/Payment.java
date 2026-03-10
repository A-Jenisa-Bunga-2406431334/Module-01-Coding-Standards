package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, String method, Order order, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = "REJECTED";
    }

    public String getId() { return id; }
    public String getMethod() { return method; }
    public String getStatus() { return status; }
    public Map<String, String> getPaymentData() { return paymentData; }
}