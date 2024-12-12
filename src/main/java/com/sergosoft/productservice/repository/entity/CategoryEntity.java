package com.sergosoft.productservice.repository.entity;

import com.sergosoft.productservice.domain.category.CategoryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "categories",
       indexes = { @Index(name = "idx_category_slug", columnList = "slug")}
)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Slugged category title <p>
     * Being used as a URL part for categories and products<p>
     * Example: if title is "Computers and Laptops" slug is "computers-and-laptops"
     */
    @NaturalId
    @Column(unique = true, updatable = false, nullable = false)
    private String slug;

    @Column(nullable = false, unique = true, length = 100)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<CategoryEntity> subcategories = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<ProductEntity> relatedProducts = new HashSet<>();

}
