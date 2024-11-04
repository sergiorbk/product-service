package com.sergosoft.productservice.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    UUID id;

    UUID ownerId;
    String title;
    String description;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    Set<Category> categories;

    BigDecimal price;

    Instant createdAt;
//    CurrencyRate currency;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }
}
