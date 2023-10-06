package com.example.msorder.models;

import com.example.msorder.dtos.OrderItemDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tb_order_items")
@Builder
public class OrderItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Min(value = 1)
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    @NotEmpty
    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    List<Product> products = new ArrayList<>();

    public OrderItem(Integer quantity) {
        this.quantity = quantity;
    }

    public OrderItem(OrderItemDto orderItemDto) {
        if (orderItemDto.id() != null) {
            this.id = UUID.fromString(orderItemDto.id());
        }
        this.quantity = orderItemDto.quantity();
        orderItemDto.products().forEach(productDto -> addProduct(new Product(productDto)));
    }

    public void addProduct(Product product) {
        products.add(product);
        product.setOrderItem(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.setOrderItem(null);
    }
}