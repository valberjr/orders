package com.example.msorder.services;

import com.example.msorder.dtos.OrderDto;
import com.example.msorder.enums.Status;
import com.example.msorder.models.Order;
import com.example.msorder.repositories.OrderRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Named
@Slf4j
@Transactional
public class OrderService {

    @Inject
    private OrderRepository repository;
    @Inject
    private UserService userService;
    @Inject
    private RabbitTemplate rabbitTemplate;
    @Inject
    private RestTemplate restTemplate;

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    public OrderDto createOrder(OrderDto orderDto) {
        return OrderDto.toEntity(repository.save(new Order(orderDto)));
    }

    public List<OrderDto> findAll() {
        return repository.findAll()
                .stream()
                .map(OrderDto::toEntity)
                .toList();
    }

    @Cacheable(value = "orders", key = "#id")
    public OrderDto findById(String id) {
        return OrderDto.toEntity(repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new IllegalArgumentException("Order not found")));
    }

    public List<Order> findIncompleteOrders() {
        return repository
                .findAllByStatusAndCreatedAtBefore(Status.INCOMPLETE, LocalDateTime.now().minusDays(2))
                .stream()
                .toList();
    }

    public OrderDto updateIncompleteStatusOrder(Order order) {
        return OrderDto.toEntity(repository.save(order));
    }

    public void sendToQueue(OrderDto orderDto) {
        rabbitTemplate.convertAndSend(queue, orderDto);
        log.info("Order {} sent to queue", orderDto.id());
    }

    public void sendToWebhookSite(OrderDto orderDto) {
        var webhookUrl = "https://webhook.site/929855e8-bf4c-4865-a501-a47f5868e5f6";
        log.info("Sending order {} to {}", orderDto.id(), webhookUrl);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<>(orderDto, headers);
        var response = restTemplate.postForEntity(webhookUrl, request, String.class);
        log.info("Response from {} : {}", webhookUrl, response.getStatusCode());
    }

}
