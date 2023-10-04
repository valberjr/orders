package com.example.msorder.dtos.mappers;

import com.example.msorder.dtos.CustomerDto;
import com.example.msorder.models.Customer;
import com.example.msorder.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapper {

    private final CustomerRepository repository;

    public CustomerDto toDto(Customer customer) {
        if (customer == null) {
            return null;
        }
        return new CustomerDto(customer.getId(), customer.getName());
    }

    public Customer toEntity(CustomerDto dto) {
        if (dto == null) {
            return null;
        }
        var customer = new Customer();
        if (dto.id() != null) {
            customer.setId(dto.id());
        }
        customer.setName(dto.name());
        return customer;
    }

}
