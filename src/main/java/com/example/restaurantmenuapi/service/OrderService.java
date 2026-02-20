package com.example.restaurantmenuapi.service;

import com.example.restaurantmenuapi.dto.OrderDto;
import com.example.restaurantmenuapi.dto.create.CreateOrderDto;
import com.example.restaurantmenuapi.dto.create.CreateOrderEntryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.UUID;

public interface OrderService {
    Page<OrderDto> getAll(Pageable pageable);

    Page<OrderDto> getAllByTableNumAndDate(Integer tableNum, Date start, Date end, Pageable pageable);

    Page<OrderDto> getAllByDate(Date start, Date end, Pageable pageable);

    OrderDto getById(UUID id);

    OrderDto save(CreateOrderDto createOrderDto);

    OrderDto updateTableById(UUID id, Integer tableNum);

    OrderDto addEntry(UUID id, CreateOrderEntryDto createOrderEntryDto);

    OrderDto updateEntry(UUID id, CreateOrderEntryDto createOrderEntryDto);

    OrderDto removeEntry(UUID id, UUID entryId);

    void deleteById(UUID id);


}
