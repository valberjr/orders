package com.example.msorder.controller;

import com.example.msorder.dto.OrderRequest;
import com.example.msorder.dto.OrderResponse;
import com.example.msorder.service.OrderService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@Validated
public class OrderController {

    @Inject
    private OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderResponse> findAll(Pageable pageable) {
        return orderService.findAll(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable String id) {
        return orderService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@RequestBody @Valid OrderRequest orderRequest) {
        return this.orderService.createOrder(orderRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        this.orderService.delete(id);
    }
}
