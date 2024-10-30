package com.sergosoft.productservice.domain;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Product {

    UUID id;
}
