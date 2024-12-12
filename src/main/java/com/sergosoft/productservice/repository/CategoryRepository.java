package com.sergosoft.productservice.repository;

import com.sergosoft.productservice.repository.entity.CategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends NaturalIdRepository<CategoryEntity, UUID, String> {

    List<CategoryEntity> findByParentNull();
    List<CategoryEntity> findBySlugIn(List<String> slugList);

}
