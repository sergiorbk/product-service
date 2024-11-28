//package com.sergosoft.productservice.web;
//
//import com.sergosoft.productservice.IntegrationTest;
//import com.sergosoft.productservice.domain.category.CategoryDetails;
//import com.sergosoft.productservice.domain.product.Product;
//import com.sergosoft.productservice.dto.product.ProductCreationDto;
//import com.sergosoft.productservice.dto.product.ProductResponseDto;
//import com.sergosoft.productservice.featuretoggle.FeatureToggleExtension;
//import com.sergosoft.productservice.featuretoggle.FeatureToggles;
//import com.sergosoft.productservice.featuretoggle.annotation.DisabledFeatureToggle;
//import com.sergosoft.productservice.featuretoggle.annotation.EnabledFeatureToggle;
//import com.sergosoft.productservice.repository.entity.CategoryEntity;
//import com.sergosoft.productservice.repository.entity.ProductEntity;
//import com.sergosoft.productservice.service.CategoryService;
//import com.sergosoft.productservice.service.ProductService;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
//
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.math.BigDecimal;
//import java.time.Instant;
//import java.util.HashSet;
//import java.util.List;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@DisplayName("Product Controller IT")
//@Tag("product-service")
//@ExtendWith(FeatureToggleExtension.class)
//class ProductControllerIT extends IntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ProductService productService;
//
//    @MockBean
//    private CategoryService categoryService;
//
//    @MockBean
//    private ProductMapper productMapper;
//
//    @MockBean
//    private CategoryMapper categoryMapper;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private ProductEntity productEntity;
//    private CategoryEntity categoryEntity;
//    private Product product;
//    private CategoryDetails categoryDetails;
//    private ProductCreationDto productCreationDto;
//    private ProductResponseDto productResponseDto;
//    private Long productId;
//
//    @BeforeEach
//    void setUp() {
//        CategoryEntity createdCategory = new CategoryEntity(1L, "Category1", null);
//        when(categoryService.createCategory(any())).thenReturn(categoryDetails);
//        categoryMapper.toCategory(createdCategory);
//        productEntity = new ProductEntity(productId, "Product Title",
//                "Product Description",
//                new HashSet<>(),
//                BigDecimal.valueOf(100),
//                Instant.now()
//        );
//
//        productCreationDto = ProductCreationDto.builder()
//                .title("Product Title")
//                .description("Product Description")
//                .categoriesIds(List.of(createdCategory.getId()))
//                .price(BigDecimal.valueOf(100.0))
//                .build();
//
//        productResponseDto = new ProductResponseDto(productId, null, "Product Title", "Product Description", List.of(), BigDecimal.valueOf(100), product.getCreatedAt());
//    }
//
//    @Test
//    @DisabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
//    void shouldGet404FeatureDisabled() throws Exception {
//        mockMvc.perform(get("/api/v1/products/217da666-2bbd-45b5-bce4-17d4ebc7abec")).andExpect(status().isServiceUnavailable());
//    }
//
//    @Test
//    @EnabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
//    void shouldGet200() throws Exception {
//        mockMvc.perform(get("/api/v1/products/217da666-2bbd-45b5-bce4-17d4ebc7abec")).andExpect(status().isOk());
//    }
//
//    @Test
//    void getProductById_ShouldReturnProduct() throws Exception {
//        when(productService.getProductById(productId)).thenReturn(product);
//        when(productMapper.toDto(product)).thenReturn(productResponseDto);
//
//        mockMvc.perform(get("/api/v1/products/{id}", productId))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.productId").value(productId.toString()))
//                .andExpect(jsonPath("$.title").value(productResponseDto.getTitle()));
//    }
//
//    @Test
//    void createProduct_ShouldReturnCreatedProduct() throws Exception {
//        when(productService.createProduct(any(ProductCreationDto.class))).thenReturn(product);
//        when(productMapper.toDto(product)).thenReturn(productResponseDto);
//
//        mockMvc.perform(post("/api/v1/products")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(productCreationDto)))
//                .andExpect(status().isCreated())
//                .andExpect(header().string("Location", "http://localhost/api/v1/products/" + productId))
//                .andExpect(jsonPath("$.productId").value(productId.toString()))
//                .andExpect(jsonPath("$.title").value(productResponseDto.getTitle()));
//    }
//
//    @Test
//    void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
//        when(productService.updateProduct(any(Long.class), any(ProductCreationDto.class))).thenReturn(product);
//        when(productMapper.toDto(product)).thenReturn(productResponseDto);
//
//        mockMvc.perform(put("/api/v1/products/{id}", productId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(productCreationDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.productId").value(productId.toString()))
//                .andExpect(jsonPath("$.title").value(productResponseDto.getTitle()));
//    }
//
//    @Test
//    void deleteProduct_ShouldReturnNoContent() throws Exception {
//        mockMvc.perform(delete("/api/v1/products/{id}", productId))
//                .andExpect(status().isNoContent());
//    }
//}
