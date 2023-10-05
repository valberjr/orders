package com.example.msorder.services;

import com.example.msorder.dtos.OrderDto;
import com.example.msorder.dtos.OrderItemDto;
import com.example.msorder.dtos.ProductDto;
import com.example.msorder.dtos.UserDto;
import com.example.msorder.enums.Status;
import com.example.msorder.models.Order;
import com.example.msorder.models.User;
import com.example.msorder.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private UserService userService;
    @InjectMocks
    private OrderService orderService;

    private OrderDto orderDto;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {

        userDto = new UserDto(
                "8a45689e-639e-11ee-8c99-0242ac120002",
                "jessica"
        );

        var productDto = new ProductDto(
                null,
                "product1",
                new BigDecimal("10.00")
        );

        var orderItemDto = new OrderItemDto(
                null,
                1,
                List.of(productDto)
        );

        orderDto = new OrderDto(
                null,
                "INCOMPLETE",
                userDto,
                List.of(orderItemDto)
        );

        ReflectionTestUtils.setField(orderService, "queue", "queue");
    }

    @Test
    void shouldCreateOrder() {
        // given
        var order = new Order(orderDto);
        var user = new User(userDto);
        // when
        when(userService.findById(any(UUID.class))).thenReturn(user);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        orderService.createOrder(orderDto);
        // then
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void shouldReturnAListOfOrderDto() {
        // given
        var orders = List.of(new Order(orderDto));
        var expectedOrders = List.of(orderDto);
        // when
        when(orderRepository.findAll()).thenReturn(orders);
        List<OrderDto> actualOrder = orderService.findAll();
        // then
        assertEquals(expectedOrders, actualOrder);
    }

    @Test
    void shouldReturnAListOfIncompleteOrders() {
        // given
        var order = new Order(orderDto);
        order.setCreatedAt(LocalDateTime.now().minusDays(3));
        var incompleteOrders = List.of(order);
        // when
        when(orderRepository.findAllByStatusAndCreatedAtBefore(
                Status.INCOMPLETE,
                LocalDate.now().minusDays(2)
        )).thenReturn(incompleteOrders);
        var actualIncompleteOrders = orderService.findIncompleteOrders();
        // then
        assertEquals(incompleteOrders, actualIncompleteOrders);
    }

    @Test
    void shouldUpdateIncompleteStatusOrder() {
        // given
        var order = new Order(orderDto);
        // when
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        orderService.updateIncompleteStatusOrder(order);
        // then
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void shouldSendToQueue() {
        // given
        // when
        orderService.sendToQueue(orderDto);
        // then
        verify(rabbitTemplate, times(1)).convertAndSend(eq("queue"), any(OrderDto.class));
    }

    @Test
    void shouldSendToWebhookSite() {
        // given
        var response = new ResponseEntity<>(HttpStatus.OK);
        // when
        when(restTemplate.postForEntity(any(String.class), any(), any())).thenReturn(response);
        orderService.sendToWebhookSite(orderDto);
        // then
        verify(restTemplate, times(1)).postForEntity(any(String.class), any(), any());
    }

}