package com.sergosoft.productservice.domain.category;

import com.sergosoft.productservice.domain.product.ProductDetails;
import lombok.Builder;
import lombok.Value;

/**
 * Represents a category of {@link ProductDetails}s.
 * <p>
 * Categories can be organized hierarchically, allowing each category to have a
 * parent category.</p>
 * <p>
 * Root categories do not have a parent, while subcategories
 * can have multiple levels of parent-child relationships.</p>
 */
@Value
@Builder
public class CategoryDetails {

    String slug;
    String title;
    CategoryStatus status;
    CategoryDetails parent;
    String imageUrl;

}
