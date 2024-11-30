package com.sergosoft.productservice.service.exception.order;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {

    private static final String ORDER_NOT_FOUND_MESSAGE = "Order with id %s not found";

    public OrderNotFoundException(UUID id) {
        super(String.format(ORDER_NOT_FOUND_MESSAGE, id));
    }
}
