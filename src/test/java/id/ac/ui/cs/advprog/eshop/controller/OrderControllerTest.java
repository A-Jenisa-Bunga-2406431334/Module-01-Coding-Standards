package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    @MockBean
    PaymentService paymentService;

    private Order createOrder() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(1);

        List<Product> products = new ArrayList<>();
        products.add(product);

        return new Order(
                "1",
                products,
                System.currentTimeMillis(),
                "Bunga"
        );
    }

    @Test
    void testCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk());
    }

    @Test
    void testHistoryPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk());
    }

    @Test
    void testHistoryPost() throws Exception {
        when(orderService.findAllByAuthor("Bunga")).thenReturn(List.of());

        mockMvc.perform(post("/order/history")
                .param("author", "Bunga"))
                .andExpect(status().isOk());
    }

    @Test
    void testPayPage() throws Exception {
        Order order = createOrder();

        when(orderService.findById("1")).thenReturn(order);

        mockMvc.perform(get("/order/pay/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testPayVoucher() throws Exception {
        Order order = createOrder();

        Map<String,String> paymentData = new HashMap<>();
        paymentData.put("voucherCode","ESHOP12345678ABCD");

        Payment payment = new Payment(
                "p1",
                "VOUCHER",
                order,
                paymentData
        );

        when(orderService.findById("1")).thenReturn(order);
        when(paymentService.addPayment(any(), eq("VOUCHER"), any()))
                .thenReturn(payment);

        mockMvc.perform(post("/order/pay/1")
                .param("method", "VOUCHER")
                .param("voucherCode", "ESHOP12345678ABCD"))
                .andExpect(status().isOk());
    }

    @Test
    void testPayBankTransfer() throws Exception {
        Order order = createOrder();

        Map<String,String> paymentData = new HashMap<>();
        paymentData.put("bankName","BCA");
        paymentData.put("referenceCode","REF123");

        Payment payment = new Payment(
                "p2",
                "BANK_TRANSFER",
                order,
                paymentData
        );

        when(orderService.findById("1")).thenReturn(order);
        when(paymentService.addPayment(any(), eq("BANK_TRANSFER"), any()))
                .thenReturn(payment);

        mockMvc.perform(post("/order/pay/1")
                .param("method", "BANK_TRANSFER")
                .param("bankName", "BCA")
                .param("referenceCode", "REF123"))
                .andExpect(status().isOk());
    }
}