package com.sergosoft.productservice.repository.entity;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private Set<OrderItemEntity> items = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private CustomerEntity seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private CustomerEntity buyer;

    private BigDecimal totalPrice;

    @Timestamp
    private Instant createdAt;
}
