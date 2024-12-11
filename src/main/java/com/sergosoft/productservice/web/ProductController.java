package com.sergosoft.productservice.web;

import com.sergosoft.productservice.domain.product.ProductDetails;
import com.sergosoft.productservice.dto.product.ProductCreateDto;
import com.sergosoft.productservice.dto.product.ProductResponseDto;
import com.sergosoft.productservice.dto.product.ProductUpdateDto;
import com.sergosoft.productservice.service.mapper.ProductMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sergosoft.productservice.service.ProductService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@Validated
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID id) {
        ProductDetails retrievedProduct = productService.getProductById(id);
        ProductResponseDto productResponseDto = productMapper.toProductResponseDto(retrievedProduct);
        return ResponseEntity.ok(productResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getProductPageByOwnerId(
            @RequestParam(value = "owner") UUID ownerReference,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ) {
        Pageable pageable = PageRequest.of(page == 0 ? 0 : page-1, size);

        Page<ProductDetails> productDetailsPage = productService.getProductsPageByOwnerReference(ownerReference, pageable);
        Page<ProductResponseDto> productResponseDtoPage = productDetailsPage.map(productMapper::toProductResponseDto);
        return ResponseEntity.ok(productResponseDtoPage.stream().toList());
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid ProductCreateDto categoryDto) {
        ProductDetails createdProduct = productService.createProduct(categoryDto);
        ProductResponseDto createdProductResponseDto = productMapper.toProductResponseDto(createdProduct);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProductResponseDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdProductResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable UUID id,
                                                            @RequestBody @Valid ProductUpdateDto updateDto) {
        ProductDetails updatedProduct = productService.updateProduct(id, updateDto);
        return ResponseEntity.ok(productMapper.toProductResponseDto(updatedProduct));
    }

    @PutMapping("/{id}/activate")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<Void> activateProduct(@PathVariable UUID id) {
        productService.activateProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/archive")
    public ResponseEntity<Void> archiveProduct(@PathVariable UUID id) {
        productService.archiveProduct(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

}
