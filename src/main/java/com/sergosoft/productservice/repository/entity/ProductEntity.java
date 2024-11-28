package com.sergosoft.productservice.repository.entity;

import com.sergosoft.productservice.domain.product.ProductStatus;
import com.sergosoft.productservice.util.Base64Utils;
import com.sergosoft.productservice.util.SlugGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_product_natural_id", columnList = "base64Id")
})
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Base 64 encoded id
     */
    @NaturalId
    @Column(unique = true, nullable = false)
    private String base64Id;

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
    private Set<CategoryEntity> categories = new HashSet<>();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist() {
        this.status = ProductStatus.PENDING;
        this.slug = SlugGenerator.generateSlug(this.title);
        this.base64Id = Base64Utils.encode(this.id.toString());
        this.createdAt = LocalDateTime.now();
    }

    /**
     * @return {slugged-title} - {base64 public id}
     */
    private String combineSlugAndBase64Id(String slug, String base64Id) {
        return String.format("%s-%s", slug, base64Id);
    }
}
