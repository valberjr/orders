package com.example.msorder.services;

import com.example.msorder.dtos.*;
import com.example.msorder.enums.Status;
import com.example.msorder.models.Order;
import com.example.msorder.models.OrderItem;
import com.example.msorder.models.Product;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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


    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(orderService, "queue", "queue");
    }

    @Test
    void shouldCreateOrder() {
        // given
        var order = createOrder();
        // when
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        orderService.createOrder(createOrderRequest());
        // then
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void shouldReturnAListOfOrders() {
        // given
        var expectedOrders = List.of(createOrderResponse());
        // when
        when(orderRepository.findAll()).thenReturn(createOrdersList());
        List<OrderResponse> actualOrder = orderService.findAll();
        // then
        assertEquals(expectedOrders, actualOrder);
    }

    @Test
    void shouldReturnAnOrderWhenIdIsProvided() {
        // given
        var id = "9a63732e-589f-11fe-7c89-0141ac130203";
        var order = createOrder();
        order.setId(UUID.fromString(id));
        // when
        when(orderRepository.findById(any(UUID.class))).thenReturn(Optional.of(order));
        var actualOrder = orderService.findById(id);
        // then
        assertNotNull(actualOrder);
    }

    @Test
    void shouldReturnAListOfIncompleteOrders() {
        // given
        var order = createOrder();
        order.setCreatedAt(LocalDateTime.now().minusDays(3));
        var incompleteOrders = createOrdersList();
        // when
        when(orderRepository
                .findAllByStatusAndCreatedAtBefore(eq(Status.INCOMPLETE), any(LocalDateTime.class)))
                .thenReturn(incompleteOrders);
        var actualIncompleteOrders = orderService.findIncompleteOrders();
        // then
        assertNotNull(actualIncompleteOrders);
        assertFalse(actualIncompleteOrders.isEmpty());
    }

    @Test
    void shouldUpdateIncompleteStatusOrder() {
        // given
        var order = createOrder();
        // when
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        orderService.updateIncompleteStatusOrder(order);
        // then
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void shouldSendToQueue() {
        // given
        var order = createOrder();
        order.setId(UUID.fromString("9a63732e-589f-11fe-7c89-0141ac130203"));
        // when
        orderService.sendToQueue(order);
        // then
        verify(rabbitTemplate, times(1))
                .convertAndSend(eq("queue"), any(OrderQueue.class));
    }

    @Test
    void shouldSendToWebhookSite() {
        // given
        var order = createOrder();
        var response = new ResponseEntity<>(HttpStatus.OK);
        // when
        when(restTemplate.postForEntity(any(String.class), any(), any())).thenReturn(response);
        orderService.sendToWebhookSite(order);
        // then
        verify(restTemplate, times(1)).postForEntity(any(String.class), any(), any());
    }

    private Order createOrder() {
        var orderRequest = createOrderRequest();
        return new Order(orderRequest.status(), orderRequest.user(), orderRequest.items());
    }

    private List<Order> createOrdersList() {

        var user = User.builder()
                .id(UUID.fromString("8a45689e-639e-11ee-8c99-0242ac120002"))
                .name("jessica")
                .build();

        var product = Product.builder()
                .name("product1")
                .price(new BigDecimal("10.00"))
                .build();

        var orderItem = OrderItem.builder()
                .quantity(1)
                .products(List.of(product))
                .build();

        var order = Order.builder()
                .id(UUID.fromString("9a63732e-589f-11fe-7c89-0141ac130203"))
                .status(Status.INCOMPLETE)
                .user(user)
                .items(List.of(orderItem))
                .build();

        return List.of(order);
    }

    private OrderRequest createOrderRequest() {
        return new OrderRequest(
                "INCOMPLETE",
                new UserRequest("8a45689e-639e-11ee-8c99-0242ac120002", "jessica"),
                List.of(new OrderItemRequest(
                        1,
                        List.of(new ProductRequest("product1", new BigDecimal("10.00"))))
                )
        );
    }

    private OrderResponse createOrderResponse() {
        return new OrderResponse(
                "9a63732e-589f-11fe-7c89-0141ac130203",
                "INCOMPLETE",
                new UserResponse("jessica"),
                List.of(new OrderItemResponse(
                        1,
                        List.of(new ProductResponse("product1", new BigDecimal("10.00"))))
                )
        );
    }

}