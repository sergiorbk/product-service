package com.sergosoft.productservice.service.exception;

import lombok.Value;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class ParamsViolationDetails {
    String fieldName;
    String reason;
}