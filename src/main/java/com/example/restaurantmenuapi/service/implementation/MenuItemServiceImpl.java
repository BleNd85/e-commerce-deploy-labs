package com.example.restaurantmenuapi.service.implementation;

import com.example.restaurantmenuapi.dto.MenuItemDto;
import com.example.restaurantmenuapi.dto.create.CreateMenuItemDto;
import com.example.restaurantmenuapi.repository.MenuItemRepository;
import com.example.restaurantmenuapi.repository.entity.MenuItemEntity;
import com.example.restaurantmenuapi.service.MenuItemService;
import com.example.restaurantmenuapi.service.exception.MenuItemNotFoundException;
import com.example.restaurantmenuapi.service.mapper.MenuItemMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository repository;
    private final MenuItemMapper mapper;

    public MenuItemServiceImpl(MenuItemRepository repository, MenuItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MenuItemDto> getAll(Pageable pageable) {
        Page<MenuItemEntity> pages = repository.findAll(pageable);
        return pages.map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public MenuItemDto getById(UUID id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new MenuItemNotFoundException(id)));
    }

    @Override
    @Transactional
    public MenuItemDto save(CreateMenuItemDto createMenuItemDto) {
        MenuItemEntity entity = mapper.toEntity(createMenuItemDto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    public MenuItemDto updateById(UUID id, CreateMenuItemDto createMenuItemDto) {
        MenuItemEntity entity = repository.findById(id).orElseThrow(() -> new MenuItemNotFoundException(id));
        entity.setName(createMenuItemDto.getName());
        entity.setPrice(createMenuItemDto.getPrice());
        entity.setWeight(createMenuItemDto.getWeight());
        entity.setIngredients(createMenuItemDto.getIngredients());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
