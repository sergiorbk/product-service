package com.sergosoft.productservice.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class CosmoCatResponseDto {

    UUID id;
    String email;
    String name;
    LocalDate registrationDate;
}
