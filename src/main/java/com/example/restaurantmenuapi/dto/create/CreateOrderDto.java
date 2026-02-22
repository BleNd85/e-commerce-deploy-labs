package com.example.restaurantmenuapi.dto.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreateOrderDto {
    @Min(value = 1, message = "Table number can not be lower than 1.")
    @NotNull(message = "Table number is mandatory.")
    Integer table;
}
