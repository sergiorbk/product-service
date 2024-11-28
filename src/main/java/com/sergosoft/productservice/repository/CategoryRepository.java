package com.sergosoft.productservice.repository;

import com.sergosoft.productservice.repository.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    Set<CategoryEntity> findByParentNull();
    Optional<CategoryEntity> findBySlug(String slug);

}
