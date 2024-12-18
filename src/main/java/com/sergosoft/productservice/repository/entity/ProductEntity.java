package com.sergosoft.productservice.repository.entity;

import com.sergosoft.productservice.domain.product.ProductStatus;
import com.sergosoft.productservice.util.SlugGenerator;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.List;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "products")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private UUID ownerReference;

     /**
     * Slugged product title<p>
     * Example: if title is "Sell 1kg of coffee Nescafe 3 in 1" slug is "sell-1kg-coffee-nescafe-3-in-1"
     */
    @Column(nullable = false)
    private String slug;

    @ManyToMany(mappedBy = "relatedProducts")
    private List<CategoryEntity> categories;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    private void prePersist() {
        this.status = ProductStatus.PENDING;
        this.slug = SlugGenerator.generateSlug(this.title);
    }
}
