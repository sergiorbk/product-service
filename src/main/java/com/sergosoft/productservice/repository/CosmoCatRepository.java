package com.sergosoft.productservice.repository;

import com.sergosoft.productservice.domain.CosmoCat;

import java.util.List;

public interface CosmoCatRepository {

    List<CosmoCat> findAll();
}
