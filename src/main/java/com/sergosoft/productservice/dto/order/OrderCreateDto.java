package com.sergosoft.productservice.dto.order;

import com.sergosoft.productservice.dto.order.item.OrderItemCreateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class OrderCreateDto {

    @NotNull(message = "Seller reference must not be null")
    UUID sellerReference;

    @NotNull(message = "Buyer reference must not be null")
    UUID buyerReference;

    @NotEmpty(message = "Order items must not be empty")
    List<@Valid OrderItemCreateDto> items;

}
