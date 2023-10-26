package com.example.msorder.controller;

import com.example.msorder.dto.OrderRequest;
import com.example.msorder.dto.OrderResponse;
import com.example.msorder.exception.NestedExceptionErrorMessageHandler;
import com.example.msorder.exception.OrderNotFoundException;
import com.example.msorder.service.OrderService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/orders")
@Validated
@Slf4j
public class OrderController {

    @Inject
    private OrderService orderService;
    @Inject
    private NestedExceptionErrorMessageHandler nestedErrorMessage;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderResponse> findAll(Pageable pageable) {
        return orderService.findAll(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable String id) {
        try {
            return orderService.findById(id);
        } catch (OrderNotFoundException e) {
            log.error("An OrderNotFoundException has occurred", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    e.getMessage(), e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@Valid @RequestBody OrderRequest orderRequest) {
        try {
            return this.orderService.createOrder(orderRequest);
        } catch (TransactionSystemException e) {
            log.error("An TransactionSystemException has occurred", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    nestedErrorMessage.messageFromNestedException(e), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        try {
            this.orderService.delete(id);
        } catch (OrderNotFoundException e) {
            log.error("An OrderNotFoundException has occurred", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    e.getMessage(), e);
        }
    }
}
