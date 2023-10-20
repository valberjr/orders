package com.example.msorder.model;

import com.example.msorder.dto.OrderItemRequest;
import com.example.msorder.dto.OrderQueue;
import com.example.msorder.dto.UserRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "tb_orders")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt = LocalDateTime.now();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotEmpty
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    private List<OrderItem> items = new ArrayList<>();

    public Order(String status, UserRequest user, List<OrderItemRequest> items) {
        this.status = Status.valueOf(status.toUpperCase());
        this.user = new User(user.id(), user.name());
        items.forEach(item -> addItem(new OrderItem(item.quantity(), item.products())));
    }

    public Order(OrderQueue orderQueue) {
        this.id = UUID.fromString(orderQueue.id());
        this.status = Status.valueOf(orderQueue.status().toUpperCase());
        this.user = new User(orderQueue.user().id(), orderQueue.user().name());
        orderQueue.items().forEach(item -> addItem(new OrderItem(item)));
    }

    public <T extends OrderItem> void addItem(T orderItem) {
        items.add(orderItem);
        orderItem.setOrder(this);
    }

    public <T extends OrderItem> void removeItem(T orderItem) {
        items.remove(orderItem);
        orderItem.setOrder(null);
    }

}
