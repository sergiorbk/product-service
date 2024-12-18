package com.sergosoft.productservice.repository.entity;

import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<OrderItemEntity> items;

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

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

}
