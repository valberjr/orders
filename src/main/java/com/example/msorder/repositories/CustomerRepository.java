package com.example.msorder.repositories;

import com.example.msorder.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository
        extends JpaRepository<Customer, UUID> {
}
