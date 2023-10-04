package com.example.msorder.configs;

import com.example.msorder.enums.Status;
import com.example.msorder.models.Customer;
import com.example.msorder.models.Order;
import com.example.msorder.models.OrderItem;
import com.example.msorder.models.Product;
import com.example.msorder.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderDatabaseInitializer implements
        CommandLineRunner {

    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        orderRepository.deleteAll();

        var order = new Order();
        order.setStatus(Status.INCOMPLETE);
        order.setCreatedAt(LocalDateTime.now().minusHours(72));

        // add customer
        var customer = new Customer();
        customer.setName("Jessica");
        order.setCustomer(customer);

        // add order items with products
        OrderItem orderItem1 = new OrderItem(1);
        order.addItem(orderItem1);
        orderItem1.addProduct(new Product("Product 1", BigDecimal.valueOf(10)));

        OrderItem orderItem2 = new OrderItem(2);
        order.addItem(orderItem2);
        orderItem2.addProduct(new Product("Product 2", BigDecimal.valueOf(20)));

        orderRepository.save(order);
        log.debug("Initialized database with new orders.");
    }
}
