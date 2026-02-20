package com.example.restaurantmenuapi.service;

import com.example.restaurantmenuapi.dto.MenuItemDto;
import com.example.restaurantmenuapi.dto.create.CreateMenuItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MenuItemService {
    Page<MenuItemDto> getAll(Pageable pageable);

    MenuItemDto getById(UUID id);

    MenuItemDto save(CreateMenuItemDto createMenuItemDto);

    MenuItemDto updateById(UUID id, CreateMenuItemDto createMenuItemDto);

    void deleteByID(UUID id);
}
