package com.example.restaurantmenuapi.service.exception;

import java.util.UUID;

public class OrderEntryNotFoundException extends RuntimeException {
    private static final String ORDER_ENTRY_NOT_FOUND_EXCEPTION = "Order entry with id %s not found";

    public OrderEntryNotFoundException(UUID id) {
        super(String.format(ORDER_ENTRY_NOT_FOUND_EXCEPTION, id));
    }
}
