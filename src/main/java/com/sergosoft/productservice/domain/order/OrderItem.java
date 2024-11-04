package com.sergosoft.productservice.domain.order;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

import com.sergosoft.productservice.domain.Product;

@Entity
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    Integer quantity;
    BigDecimal price;
}
