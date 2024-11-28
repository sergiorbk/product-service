package com.sergosoft.productservice.repository.entity;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItemEntity> items = new HashSet<>();

    /**
     * Refers to the Person who SELLS a product;
     * Responsible microservice: USER SERVICE
     */
    @Column(nullable = false)
    private UUID sellerReference;

    /**
     * Refers to the Person who BUYS a product;
     * Responsible microservice: USER SERVICE
     */
    @Column(nullable = false)
    private UUID buyerReference;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Timestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}