package com.example.restaurantmenuapi.service.mapper;

import com.example.restaurantmenuapi.dto.MenuItemDto;
import com.example.restaurantmenuapi.dto.create.CreateMenuItemDto;
import com.example.restaurantmenuapi.repository.entity.MenuItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "orderEntries", expression = "java(new ArrayList<>())")
    MenuItemEntity toEntity(CreateMenuItemDto createMenuItemDto);

    MenuItemDto toDto(MenuItemEntity menuItemEntity);

    List<MenuItemDto> toDto(List<MenuItemEntity> menuItemEntities);
}
