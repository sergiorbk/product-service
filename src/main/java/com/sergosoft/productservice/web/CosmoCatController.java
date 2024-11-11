package com.sergosoft.productservice.web;

import com.sergosoft.productservice.dto.CosmoCatResponseDto;
import com.sergosoft.productservice.service.CosmoCatService;
import com.sergosoft.productservice.service.mapper.CosmoCatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cosmocats")
@RequiredArgsConstructor
public class CosmoCatController {

    private final CosmoCatService cosmoCatService;
    private final CosmoCatMapper cosmoCatMapper;

    @GetMapping("/all")
    public ResponseEntity<List<CosmoCatResponseDto>> getAllCosmoCats() {
        return ResponseEntity.ok(cosmoCatService.getCosmoCats().stream().map(cosmoCatMapper::toDto).toList());
    }
}
