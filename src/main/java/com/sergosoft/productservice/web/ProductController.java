//package com.sergosoft.productservice.web;
//
//import com.sergosoft.productservice.domain.Product;
//import com.sergosoft.productservice.dto.product.ProductCreationDto;
//import com.sergosoft.productservice.dto.product.ProductResponseDto;
//import jakarta.validation.Valid;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import com.sergosoft.productservice.service.ProductService;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import java.net.URI;
//
//@RestController
//@RequestMapping("/api/v1/products")
//@Validated
//public class ProductController {
//
//    private final ProductService productService;
//    private final ProductMapper productMapper;
//
//    public ProductController(ProductService productService, ProductMapper productMapper) {
//        this.productService = productService;
//        this.productMapper = productMapper;
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
//        Product retrievedProduct = productService.getProductById(id);
//        ProductResponseDto productResponseDto = productMapper.toDto(retrievedProduct);
//        return ResponseEntity.ok(productResponseDto);
//    }
//
//    @PostMapping
//    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid ProductCreationDto categoryDto) {
//        Product createdProduct = productService.createProduct(categoryDto);
//        ProductResponseDto createdProductResponseDto = productMapper.toDto(createdProduct);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(createdProductResponseDto.getProductId())
//                .toUri();
//        return ResponseEntity.created(location).body(createdProductResponseDto);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id,
//                                                            @RequestBody @Valid ProductCreationDto productDto) {
//        Product updatedProduct = productService.updateProduct(id, productDto);
//        return ResponseEntity.ok(productMapper.toDto(updatedProduct));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
//        productService.deleteProductById(id);
//        return ResponseEntity.noContent().build();
//    }
//}
