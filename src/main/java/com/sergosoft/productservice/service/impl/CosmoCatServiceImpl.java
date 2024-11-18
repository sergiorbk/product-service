package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.domain.CosmoCat;
import com.sergosoft.productservice.repository.CosmoCatRepository;
import com.sergosoft.productservice.service.CosmoCatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CosmoCatServiceImpl implements CosmoCatService {

    private final CosmoCatRepository cosmoCatRepository;

    @Override
    public List<CosmoCat> getCosmoCats() {
        return cosmoCatRepository.findAll();
    }
}
