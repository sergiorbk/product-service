package com.sergosoft.productservice.repository.projection;

import java.math.BigDecimal;
import java.util.UUID;

public interface OrderSummaryProjection {

    UUID getBuyerReference();
    BigDecimal getTotalPrice();

}
