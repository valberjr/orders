package com.example.msorder.model;

import com.example.msorder.dto.OrderItemQueue;
import com.example.msorder.dto.ProductRequest;
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
@Builder
@Table(name = "tb_order_items")
public class OrderItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Min(value = 1, message = "Order item quantity cannot be less than 1")
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    @NotEmpty(message = "List of products cannot be empty")
    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    private List<Product> products = new ArrayList<>();

    public OrderItem(Integer quantity) {
        this.quantity = quantity;
    }

    public OrderItem(Integer quantity, List<ProductRequest> products) {
        this.quantity = quantity;
        products.forEach(product -> addProduct(new Product(product.name(), product.price())));
    }

    public OrderItem(OrderItemQueue orderItemQueue) {
        this.quantity = orderItemQueue.quantity();
        orderItemQueue.products().forEach(product -> addProduct(new Product(product.name(), product.price())));
    }

    public <T extends Product> void addProduct(T product) {
        products.add(product);
        product.setOrderItem(this);
    }

    public <T extends Product> void removeProduct(T product) {
        products.remove(product);
        product.setOrderItem(null);
    }
}
