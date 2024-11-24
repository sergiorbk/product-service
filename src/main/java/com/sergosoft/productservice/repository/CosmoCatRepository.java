package com.sergosoft.productservice.repository;

import com.sergosoft.productservice.domain.CosmoCat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CosmoCatRepository extends JpaRepository<CosmoCat, Long> {

}
