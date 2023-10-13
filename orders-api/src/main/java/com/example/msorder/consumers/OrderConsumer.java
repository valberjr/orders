package com.example.msorder.consumers;

import com.example.msorder.models.Order;
import com.example.msorder.services.OrderService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

@Named
@Slf4j
public class OrderConsumer {

    @Inject
    private OrderService orderService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload Order order) {
        log.info("Order {} received", order.getId());
        this.orderService.sendToWebhookSite(order);
    }
}
