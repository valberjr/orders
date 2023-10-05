package com.example.msorder.repositories;

import com.example.msorder.enums.Status;
import com.example.msorder.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface OrderRepository
        extends JpaRepository<Order, UUID> {

    List<Order> findAllByStatusAndCreatedAtBefore(Status status, LocalDate thresholdDate);
}
