package com.sergosoft.productservice.dto;

import lombok.Builder;
import lombok.Value;
import java.time.LocalDate;

@Value
@Builder
public class CosmoCatResponseDto {

    Long id;
    String email;
    String name;
    LocalDate registrationDate;
}
