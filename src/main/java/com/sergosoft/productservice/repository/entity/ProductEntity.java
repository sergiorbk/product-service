package com.sergosoft.productservice.repository.entity;

import com.sergosoft.productservice.util.Base64Utils;
import com.sergosoft.productservice.util.SlugGenerator;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
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
        @Index(name = "idx_product_natural_id", columnList = "naturalId")
})
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NaturalId
    @Column(unique = true, nullable = false)
    private String naturalId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private String description;

    /**
     * Slugged product title
     * <p>
     * Example: if title is "Sell 1kg of coffee Nescafe 3 in 1" slug is "sell-1kg-coffee-nescafe-3-in-1"
     */
    @Column(nullable = false)
    private String slug;

    @ManyToMany(mappedBy = "relatedProducts")
    private Set<CategoryEntity> categories = new HashSet<>();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Timestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist() {
        this.slug = SlugGenerator.generateSlug(this.title);
        this.naturalId = generateNaturalId();
    }

    private String generateNaturalId() {
        String encodedId = Base64Utils.encode(this.id.toString());
        return String.format("%s-%s", slug, encodedId);
    }
}
