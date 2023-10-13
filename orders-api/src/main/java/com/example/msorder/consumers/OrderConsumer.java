package com.example.msorder.consumers;

import com.example.msorder.dtos.OrderDto;
import com.example.msorder.services.OrderService;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

@Named
@Slf4j
public class OrderConsumer {

    private OrderService orderService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload OrderDto orderDto) {
        log.info("Order {} received", orderDto.id());
        this.orderService.sendToWebhookSite(orderDto);
    }
}
