package com.example.msorder.repository;

import com.example.msorder.model.Status;
import com.example.msorder.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository
        extends JpaRepository<Order, UUID> {

    List<Order> findAllByStatusAndCreatedAtBefore(Status status, LocalDateTime thresholdDate);
}
