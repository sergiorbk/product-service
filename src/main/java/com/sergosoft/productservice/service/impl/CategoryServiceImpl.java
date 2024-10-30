package com.sergosoft.productservice.service.impl;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.service.CategoryService;
import com.sergosoft.productservice.repository.CategoryRepository;
import com.sergosoft.productservice.service.exception.CategoryNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Integer id) {
        log.info("Getting product category by id: {}", id);
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public Category createCategory(Category category) {
        log.info("Creating new product category: {}", category);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(Integer id) {
        log.info("Deleting category with id: {}", id);
        categoryRepository.delete(id);
    }
}
