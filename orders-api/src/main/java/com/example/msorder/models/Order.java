package com.example.msorder.models;

import com.example.msorder.dtos.OrderDto;
import com.example.msorder.enums.Status;
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
    private Status status;

    private LocalDateTime createdAt = LocalDateTime.now();

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @NotEmpty
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    private List<OrderItem> items = new ArrayList<>();

    public Order(OrderDto orderDto) {
        if (orderDto.id() != null) {
            this.id = UUID.fromString(orderDto.id());
        }
        this.status = Status.valueOf(orderDto.status().toUpperCase());
        this.user = new User(orderDto.user());
        orderDto.items().forEach(orderItemDto -> addItem(new OrderItem(orderItemDto)));
    }

    public void addItem(OrderItem orderItem) {
        items.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeItem(OrderItem orderItem) {
        items.remove(orderItem);
        orderItem.setOrder(null);
    }

}
