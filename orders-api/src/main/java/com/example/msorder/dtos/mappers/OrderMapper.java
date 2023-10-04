package com.example.msorder.dtos.mappers;

import com.example.msorder.dtos.OrderDto;
import com.example.msorder.dtos.OrderItemDto;
import com.example.msorder.dtos.ProductDto;
import com.example.msorder.enums.Status;
import com.example.msorder.models.Order;
import com.example.msorder.models.OrderItem;
import com.example.msorder.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final UserMapper userMapper;

    public OrderDto toDto(Order order) {
        if (order == null) {
            return null;
        }
        var items = order.getItems()
                .stream()
                .map(item -> new OrderItemDto(
                        item.getId(),
                        item.getQuantity(),
                        item.getProducts().stream()
                                .map(product -> new ProductDto(
                                        product.getId(),
                                        product.getName(),
                                        product.getPrice()))
                                .toList()
                )).toList();
        return new OrderDto(order.getId(), order.getStatus().getValue(), userMapper.toDto(order.getUser()), items);
    }

    public Order toEntity(OrderDto dto) {
        if (dto == null) {
            return null;
        }
        var order = new Order();
        if (dto.id() != null) {
            order.setId(dto.id());
        }
        order.setStatus(convertStatusValue(dto.status()));
        order.setUser(userMapper.toEntity(dto.user()));
        dto.items().forEach(dtoItem -> {
            var orderItem = new OrderItem();
            orderItem.setQuantity(dtoItem.quantity());
            order.addItem(orderItem);
            dtoItem.products().forEach(dtoProduct -> {
                var product = new Product();
                product.setName(dtoProduct.name());
                product.setPrice(dtoProduct.price());
                orderItem.addProduct(product);
            });
        });
        return order;
    }

    public Status convertStatusValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value.toUpperCase()) {
            case "ABANDONED" -> Status.ABANDONED;
            case "INCOMPLETE" -> Status.INCOMPLETE;
            default -> throw new IllegalArgumentException("Invalid status: " + value);
        };
    }
}
