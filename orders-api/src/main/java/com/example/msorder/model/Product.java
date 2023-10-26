package com.example.msorder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "tb_products")
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Product name cannot be blank")
    @Length(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    @Column(length = 100, nullable = false)
    private String name;

    @DecimalMin(value = "0.00", message = "Product price cannot be less than 0.00")
    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

}
