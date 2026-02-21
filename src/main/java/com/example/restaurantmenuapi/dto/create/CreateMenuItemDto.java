package com.example.restaurantmenuapi.dto.create;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreateMenuItemDto {
    @NotBlank(message = "Name is mandatory.")
    @Length(min = 2, max = 128, message = "Name must be 2 to 128 characters long.")
    String name;

    @NotNull(message = "Price is mandatory.")
    @Range(min = 1, max = 10000, message = "Price must be in range from 1 to 100000.")
    Integer price;

    @NotNull(message = "Weight is mandatory.")
    @DecimalMin(value = "0.001", message = "Weight must be more or equal to 0.001.")
    Double weight;

    @NotBlank(message = "Ingredients are mandatory.")
    @Length(min = 2, max = 128, message = "Ingredients list must be 10 to 128 characters long.")
    String ingredients;
}
