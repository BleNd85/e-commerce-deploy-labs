package com.example.restaurantmenuapi.service.exception;

import java.util.UUID;

public class MenuItemNotFoundException extends RuntimeException {
    private static final String MENU_ITEM_NOT_FOUND_EXCEPTION = "Menu item with id %s not found";

    public MenuItemNotFoundException(UUID id) {
        super(String.format(MENU_ITEM_NOT_FOUND_EXCEPTION, id));
    }
}
