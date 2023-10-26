package com.example.msorder.service;

import com.example.msorder.dto.OrderQueue;
import com.example.msorder.dto.OrderRequest;
import com.example.msorder.dto.OrderResponse;
import com.example.msorder.exception.OrderNotFoundException;
import com.example.msorder.model.Order;
import com.example.msorder.model.Status;
import com.example.msorder.repository.OrderRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.msorder.dto.OrderResponse.toResponse;

@Named
@Slf4j
@Transactional
public class OrderService {

    @Inject
    private OrderRepository repository;
    @Inject
    private RabbitTemplate rabbitTemplate;
    @Inject
    private RestTemplate restTemplate;

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    @CacheEvict(value = "orders", allEntries = true)
    public OrderResponse createOrder(OrderRequest orderRequest) {
        var order = new Order(orderRequest.user(), orderRequest.items());
        this.repository.save(order);
        return toResponse(order);
    }

    @CacheEvict(value = "orders", allEntries = true)
    @Cacheable(value = "orders")
    public Page<OrderResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(OrderResponse::toResponse);
    }

    public OrderResponse findById(String id) {
        return toResponse(repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new OrderNotFoundException("Order not found")));
    }

    public List<OrderQueue> findIncompleteOrders() {
        return repository
                .findAllByStatusAndCreatedAtBefore(Status.INCOMPLETE, LocalDateTime.now().minusDays(2))
                .stream()
                .map(OrderQueue::toQueue)
                .toList();
    }

    @CacheEvict(value = "orders", allEntries = true)
    public void delete(String id) {
        repository.deleteById(UUID.fromString(id));
    }

    public Optional<Order> updateIncompleteStatusOrder(Order order) {
        return Optional.of(repository.save(order));
    }

    public void sendToQueue(Order order) {
        rabbitTemplate.convertAndSend(queue, OrderQueue.toQueue(order));
        log.info("Order {} sent to queue", order.getId());
    }

    public void sendToWebhookSite(Order order) {
        var webhookUrl = "https://webhook.site/929855e8-bf4c-4865-a501-a47f5868e5f6";
        log.info("Sending order {} to {}", order.getId(), webhookUrl);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<>(toResponse(order), headers);
        var response = restTemplate.postForEntity(webhookUrl, request, String.class);
        log.info("Response from {} : {}", webhookUrl, response.getStatusCode());
    }

}
