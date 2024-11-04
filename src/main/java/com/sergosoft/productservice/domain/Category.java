package com.sergosoft.productservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Represents a category of {@link Product}s.
 * <p>
 * Categories can be organized hierarchically, allowing each category to have a
 * parent category.</p>
 * <p>
 * Root categories do not have a parent, while subcategories
 * can have multiple levels of parent-child relationships.</p>
 */
@Entity
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    /**
     * Unique identifier of the product category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    /**
     * Title or name of the product category.
     */
    String title;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    Category parent;

    /**
     * List of subcategories of this category.
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Category> subcategories;

    @ManyToMany(mappedBy = "categories")
    Set<Product> products;
}
