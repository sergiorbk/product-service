package com.sergosoft.productservice.service.exception;

public class OrderItemNotFoundException extends RuntimeException {

    private static final String ORDER_ITEM_NOT_FOUND_MESSAGE = "Order Item with id %s not found";

    public OrderItemNotFoundException(Long id) {
        super(String.format(ORDER_ITEM_NOT_FOUND_MESSAGE, id));
    }
}
