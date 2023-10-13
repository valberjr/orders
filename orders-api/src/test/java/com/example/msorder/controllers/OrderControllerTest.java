//package com.example.msorder.controllers;
//
//import com.example.msorder.dtos.OrderDto;
//import com.example.msorder.dtos.OrderItemDto;
//import com.example.msorder.dtos.ProductDto;
//import com.example.msorder.dtos.UserDto;
//import com.example.msorder.models.User;
//import com.example.msorder.services.OrderService;
//import com.example.msorder.services.UserService;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class OrderControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private OrderService orderService;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//
//    @Test
//    @WithMockUser(username = "user", roles = "USER")
//    void shouldReturnListOfOrders() throws Exception {
//        // given
//        var result = mockMvc
//                .perform(MockMvcRequestBuilders.get("/api/orders")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//        // when
//        var responseBody = result.getResponse().getContentAsString();
//        var orderDtoList = objectMapper.readValue(responseBody, new TypeReference<List<OrderDto>>() {
//        });
//        // then
//        assertThat(orderDtoList).isNotEmpty();
//    }
//
//    @Test
//    @WithMockUser(username = "user", roles = "USER")
//    void shouldCreateOrder() throws Exception {
//        // given
//        var orderDto = createOrderDto();
//        var requestJson = objectMapper.writeValueAsString(orderDto);
//        // when
//        var result = mockMvc
//                .perform(MockMvcRequestBuilders.post("/api/orders")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andReturn();
//        var responseBody = result.getResponse().getContentAsString();
//        var responseOrderDto = objectMapper.readValue(responseBody, OrderDto.class);
//        // then
//        assertThat(responseOrderDto).isNotNull();
//    }
//
//    private OrderDto createOrderDto() {
//        var userDto = createUser();
//
//        var productDto = new ProductDto(
//                null,
//                "product1",
//                new BigDecimal("10.00")
//        );
//
//        var orderItemDto = new OrderItemDto(
//                null,
//                1,
//                List.of(productDto)
//        );
//
//        return new OrderDto(
//                null,
//                "INCOMPLETE",
//                userDto,
//                List.of(orderItemDto)
//        );
//    }
//
//    private UserDto createUser() {
//        var user = User.builder()
//                .id(UUID.fromString("8a45689e-639e-11ee-8c99-0242ac120002"))
//                .username("jessica")
//                .password("password")
//                .name("jessica")
//                .build();
//        return UserDto.toEntity(this.userService.save(user));
//    }
//}