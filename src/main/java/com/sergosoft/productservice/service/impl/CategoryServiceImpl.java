package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.repository.CategoryRepository;
import com.sergosoft.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
        // todo logging
    }

    @Override
    public void deleteCategoryById(Integer categoryId) {
        categoryRepository.delete(categoryId);
        // todo logging
    }

    // todo
}
