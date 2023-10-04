package com.example.msorder.services;

import com.example.msorder.dtos.OrderDto;
import com.example.msorder.dtos.mappers.OrderMapper;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate;

    private final CustomerService customerService;

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = mapper.toEntity(orderDto);
        order.setCustomer(this.customerService.findById(orderDto.customer().id()));
        Order saved = repository.save(order);
        return mapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Order> findIncompleteOrders() {
        return repository.findAllByStatusAndCreatedAtBefore(Status.INCOMPLETE, LocalDateTime.now().minusHours(48))
                .stream()
                .toList();
    }

    @Transactional
    public OrderDto updateIncompleteStatusOrder(Order order) {
        return mapper.toDto(repository.save(order));
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
        HttpEntity<OrderDto> request = new HttpEntity<>(orderDto, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(webhookUrl, request, String.class);
        log.info("Response from {} : {}", webhookUrl, response.getBody());
    }

}
