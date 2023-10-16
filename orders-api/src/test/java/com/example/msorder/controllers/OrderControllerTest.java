package com.example.msorder.controllers;

import com.example.msorder.dtos.*;
import com.example.msorder.models.User;
import com.example.msorder.services.OrderService;
import com.example.msorder.services.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Inject
    private MockMvc mockMvc;
    @Inject
    private OrderService orderService;
    @Inject
    private UserService userService;
    @Inject
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldReturnListOfOrders() throws Exception {
        // In this test, the redis server should be properly configured

        // given
        var result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        // when
        var responseBody = result.getResponse().getContentAsString();
        var orderDtoList = objectMapper.readValue(responseBody, new TypeReference<List<OrderResponse>>() {
        });
        // then
        assertThat(orderDtoList).isNotEmpty();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldReturnOrderById() throws Exception {
        // given
        var orderId = getOrderId();
        var result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/orders/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        // when
        var responseBody = result.getResponse().getContentAsString();
        var orderResponse = objectMapper.readValue(responseBody, OrderResponse.class);
        // then
        assertThat(orderResponse).isNotNull();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldCreateOrder() throws Exception {
        // given
        var orderDto = createRequest();
        var requestJson = objectMapper.writeValueAsString(orderDto);
        // when
        var result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        var responseBody = result.getResponse().getContentAsString();
        var orderResponse = objectMapper.readValue(responseBody, OrderResponse.class);
        // then
        assertThat(orderResponse).isNotNull();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldDeleteOrder() throws Exception {
        // given
        var orderId = getOrderId();
        // when
        var result = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/api/orders/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();
        // then
        assertThat(result.getResponse().getContentAsString()).isEmpty();
    }

    private OrderRequest createRequest() {
        var user = createUserRequest();
        var product = new ProductRequest("product1", new BigDecimal("10.00"));
        var orderItem = new OrderItemRequest(1, List.of(product));
        return new OrderRequest("INCOMPLETE", user, List.of(orderItem));
    }

    private UserRequest createUserRequest() {
        var user = User.builder()
                .username("jessica")
                .password("password")
                .name("jessica")
                .build();
        var savedUser = this.userService.save(user);
        return new UserRequest(String.valueOf(savedUser.getId()), savedUser.getName());
    }

    private OrderResponse createResponse() {
        return new OrderResponse(
                "8a45689e-639e-11ee-8c99-0242ac120002",
                "INCOMPLETE",
                new UserResponse("jessica"),
                List.of());
    }

    private String getOrderId() {
        return this.orderService.findAll()
                .stream()
                .findFirst()
                .map(OrderResponse::id)
                .orElseThrow();
    }
}