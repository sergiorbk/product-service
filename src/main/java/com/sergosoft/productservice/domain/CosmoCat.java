package com.sergosoft.productservice.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class CosmoCat {

    UUID id;
    String email;
    String name;
    String password;
    LocalDate registrationDate;
}
