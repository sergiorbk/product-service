package com.sergosoft.productservice.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private OrderEntity order;

    @ManyToOne
    private ProductEntity product;

    @Column(nullable = false)
    Integer quantity;

    @Column(nullable = false)
    BigDecimal price;
}
