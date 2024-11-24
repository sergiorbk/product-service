package com.sergosoft.productservice.domain;

import lombok.Builder;
import lombok.Value;
import java.time.LocalDate;

@Value
@Builder
public class Customer {

    Long id;
    String email;
    String name;
    LocalDate registrationDate;
}
