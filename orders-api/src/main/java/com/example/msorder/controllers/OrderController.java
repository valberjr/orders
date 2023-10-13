package com.example.msorder.controllers;

import com.example.msorder.dtos.OrderDto;
import com.example.msorder.services.OrderService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Validated
public class OrderController {

    @Inject
    private OrderService orderService;

    @GetMapping
    public List<OrderDto> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderDto findById(@PathVariable String id) {
        return orderService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@RequestBody @Valid OrderDto orderDto) {
        return this.orderService.createOrder(orderDto);
    }
}
