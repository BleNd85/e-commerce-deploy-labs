package com.example.restaurantmenuapi.dto.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class CreateMenuItemDto {
    @NotBlank(message = "Name is mandatory.")
    @Max(value = 128, message = "Name can not exceed 128 characters.")
    String name;

    @NotNull(message = "Price is mandatory.")
    Integer price;

    @NotNull(message = "Weight is mandatory.")
    Double weight;

    @NotBlank(message = "Ingredients are mandatory.")
    @Min(value = 12, message = "Ingredients list must be at least 12 characters long.")
    @Max(value = 128, message = "Ingredients list can not exceed 128 characters.")
    String ingredients;
}
