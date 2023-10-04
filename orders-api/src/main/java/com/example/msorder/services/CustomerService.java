package com.example.msorder.services;

import com.example.msorder.models.Customer;
import com.example.msorder.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    @Transactional
    public Customer findById(UUID id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }
}
