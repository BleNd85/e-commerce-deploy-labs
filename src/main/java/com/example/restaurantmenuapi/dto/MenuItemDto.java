package com.example.restaurantmenuapi.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class MenuItemDto {
    UUID id;
    String name;
    Integer price;
    Double weight;
    String ingredients;
}
