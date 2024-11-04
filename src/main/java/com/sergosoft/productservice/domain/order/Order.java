package com.sergosoft.productservice.domain.order;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<OrderItem> items;

    // this microservice is going to keep the conception of P2P trading
    UUID sellerId;
    UUID buyerId;

    BigDecimal totalPrice;
    Instant createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }
}
