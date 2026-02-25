package com.example.restaurantmenuapi.service.exception;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ParamsViolationDetails {
    String fieldName;
    String reason;
}
