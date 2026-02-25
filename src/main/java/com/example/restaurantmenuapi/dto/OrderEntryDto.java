package com.example.restaurantmenuapi.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class OrderEntryDto {
    UUID id;
    Integer quantity;
    MenuItemDto menuItem;
}
