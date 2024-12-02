package com.sergosoft.productservice.repository.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "order_items")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    @ToString.Exclude
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}
