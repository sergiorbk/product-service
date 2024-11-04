package com.sergosoft.productservice.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sergosoft.productservice.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
