package com.example.msorder.configs.db;

import com.example.msorder.enums.Status;
import com.example.msorder.models.Order;
import com.example.msorder.models.OrderItem;
import com.example.msorder.models.Product;
import com.example.msorder.repositories.OrderRepository;
import com.example.msorder.repositories.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Named
@org.springframework.core.annotation.Order(2)
@Slf4j
public class OrderDatabaseInitializer implements CommandLineRunner {

    @Inject
    private OrderRepository orderRepository;
    @Inject
    private UserRepository userRepository;

    @Override
    public void run(String... args) {

        var order = new Order();
        order.setStatus(Status.INCOMPLETE);
        order.setCreatedAt(LocalDateTime.now().minusHours(72));

        // finds a user
        this.userRepository.findAll().stream().findFirst().ifPresent(order::setUser);

        // add order items with products
        OrderItem orderItem1 = new OrderItem(1);
        order.addItem(orderItem1);
        orderItem1.addProduct(new Product("Product 1", BigDecimal.valueOf(10)));

        OrderItem orderItem2 = new OrderItem(2);
        order.addItem(orderItem2);
        orderItem2.addProduct(new Product("Product 2", BigDecimal.valueOf(20)));

        this.orderRepository.save(order);
        log.debug("Database initialized with new orders.");
    }
}
