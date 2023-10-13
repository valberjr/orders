package com.example.msorder.schedulers;

import com.example.msorder.enums.Status;
import com.example.msorder.models.Order;
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
                .forEach(orderQueue -> {
                    var order = new Order(orderQueue);
                    order.setStatus(Status.ABANDONED);
                    this.service.updateIncompleteStatusOrder(order)
                            .ifPresent(foundOrder -> {
                                this.service.sendToQueue(foundOrder);
                                log.info("Order {} updated to ABANDONED", order.getId());
                            });
                });
    }
}
