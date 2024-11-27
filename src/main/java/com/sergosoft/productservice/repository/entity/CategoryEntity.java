package com.sergosoft.productservice.repository.entity;

import com.sergosoft.productservice.util.SlugGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import java.util.List;

@Data
@Entity
@Table(name = "category", indexes = {
        @Index(name = "idx_category_slug", columnList = "slug")
})
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String title;

    /**
     * Slugged category title <p>
     * Natural id for CategoryEntity, being used as a URL part for categories and products<p>
     * Example: if title is "Computers and Laptops" slug is "computers-and-laptops"
     */
    @NaturalId
    @Column(nullable = false, unique = true)
    private String slug;

    @ManyToOne
    private CategoryEntity parent;

    @OneToMany
    private List<CategoryEntity> subcategories;

    @PrePersist
    private void generateSlug() {
        this.slug = SlugGenerator.generateSlug(this.title);
    }
}
