package com.example.msorder.services;

import com.example.msorder.dtos.OrderDto;
import com.example.msorder.enums.Status;
import com.example.msorder.models.Order;
import com.example.msorder.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate;
    private final UserService userService;

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        var order = new Order(orderDto);
        order.setUser(this.userService.findById(UUID.fromString(orderDto.user().id())));
        return OrderDto.toEntity(repository.save(order));
    }

    @Transactional(readOnly = true)
    public List<OrderDto> findAll() {
        return repository.findAll()
                .stream()
                .map(OrderDto::toEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Order> findIncompleteOrders() {
        return repository
                .findAllByStatusAndCreatedAtBefore(Status.INCOMPLETE, LocalDateTime.now().minusDays(2))
                .stream()
                .toList();
    }

    @Transactional
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
