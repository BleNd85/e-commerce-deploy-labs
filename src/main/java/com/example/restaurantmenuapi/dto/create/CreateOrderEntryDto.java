package com.example.restaurantmenuapi.dto.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreateOrderEntryDto {

    @NotNull(message = "Menu item id is mandatory.")
    private UUID menuItemId;

    @NotNull(message = "Quantity is mandatory.")
    @Min(value = 1, message = "Quantity can not be lower that 1.")
    @Max(value = 50, message = "Quantity can not be bigger than 50.")
    private Integer quantity;
}
