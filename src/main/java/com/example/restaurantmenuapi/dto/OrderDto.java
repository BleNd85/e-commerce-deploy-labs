package com.example.restaurantmenuapi.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class OrderDto {
    UUID id;
    Date date;
    Integer table;
    List<OrderEntryDto> orderEntries;
}
