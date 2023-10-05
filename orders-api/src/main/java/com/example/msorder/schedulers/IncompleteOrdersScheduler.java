package com.example.msorder.schedulers;

import com.example.msorder.dtos.OrderDto;
import com.example.msorder.enums.Status;
import com.example.msorder.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class IncompleteOrdersScheduler {

    private final OrderService service;

    @Scheduled(cron = "0 */2 * ? * *")
    public void updateIncompleteStatus() {
        this.service
                .findIncompleteOrders()
                .forEach(order -> {
                    order.setStatus(Status.ABANDONED);
                    OrderDto updatedOrder = this.service.updateIncompleteStatusOrder(order);
                    log.info("Status order {} updated to ABANDONED", order.getId());
                    this.service.sendToQueue(updatedOrder);
                });
    }
}
