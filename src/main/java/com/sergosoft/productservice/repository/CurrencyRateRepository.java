package com.sergosoft.productservice.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sergosoft.productservice.domain.CurrencyRate;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, String> {

}
