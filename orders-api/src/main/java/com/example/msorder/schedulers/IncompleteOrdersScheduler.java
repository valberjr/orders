package com.example.msorder.schedulers;

import com.example.msorder.dtos.OrderDto;
import com.example.msorder.enums.Status;
import com.example.msorder.services.OrderService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Named
@Slf4j
public class IncompleteOrdersScheduler {

    @Inject
    private OrderService service;

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
