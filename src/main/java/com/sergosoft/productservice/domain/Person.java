package com.sergosoft.productservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@AllArgsConstructor
public class Person {

    UUID id;
    String username;
    String email;
}
