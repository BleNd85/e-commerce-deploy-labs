package com.example.restaurantmenuapi.service.mapper;

import com.example.restaurantmenuapi.dto.OrderDto;
import com.example.restaurantmenuapi.dto.create.CreateOrderDto;
import com.example.restaurantmenuapi.repository.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MenuItemMapper.class})
public interface OrderMapper {

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "date", expression = "java(new java.util.Date())")
    @Mapping(target = "orderEntries", expression = "java(new ArrayList<>())")
    OrderEntity toEntity(CreateOrderDto createOrderDto);

    OrderDto toDto(OrderEntity orderEntity);

    List<OrderDto> toDto(List<OrderEntity> orderEntities);
}
