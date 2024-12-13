package com.sergosoft.productservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * Represents a category of {@link Product}s.
 * <p>
 * Categories can be organized hierarchically, allowing each category to have a
 * parent category.</p>
 * <p>
 * Root categories do not have a parent, while subcategories
 * can have multiple levels of parent-child relationships.</p>
 */
@Value
@Builder
@AllArgsConstructor
public class Category {

    /**
     * Unique identifier of the product category.
     */
    Integer id;

    /**
     * Title or name of the product category.
     */
    String title;

    /**
     * <p>Parent category of this category.</p>
     * <p>Contains null if this category is a root category.</p>
     */
    Category parent;
}
