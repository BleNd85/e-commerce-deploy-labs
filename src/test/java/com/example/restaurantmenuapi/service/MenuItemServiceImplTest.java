package com.example.restaurantmenuapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.example.restaurantmenuapi.config.MappersTestConfiguration;
import com.example.restaurantmenuapi.dto.MenuItemDto;
import com.example.restaurantmenuapi.dto.create.CreateMenuItemDto;
import com.example.restaurantmenuapi.repository.MenuItemRepository;
import com.example.restaurantmenuapi.repository.entity.MenuItemEntity;
import com.example.restaurantmenuapi.service.exception.MenuItemNotFoundException;
import com.example.restaurantmenuapi.service.implementation.MenuItemServiceImpl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(classes = { MenuItemServiceImpl.class })
@Import(MappersTestConfiguration.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("MenuItem Service Test")
class MenuItemServiceImplTest {

    @MockitoBean
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuItemServiceImpl menuItemService;

    @Test
    void testGetAllMenuItems() {
        List<MenuItemEntity> entities = List.of(new MenuItemEntity());
        Page<MenuItemEntity> page = new PageImpl<>(entities);

        when(menuItemRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<MenuItemDto> result = menuItemService.getAll(PageRequest.of(0, 10));

        assertEquals(entities.size(), result.getContent().size());
    }

    @Test
    void testGetMenuItemById() {
        UUID id = UUID.randomUUID();
        MenuItemEntity entity = new MenuItemEntity();
        entity.setId(id);

        when(menuItemRepository.findById(id)).thenReturn(Optional.of(entity));

        MenuItemDto result = menuItemService.getById(id);

        assertEquals(id, result.getId());
    }

    @Test
    void testGetMenuItemByIdNotFound() {
        UUID id = UUID.randomUUID();

        when(menuItemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class,
                () -> menuItemService.getById(id));
    }

    @Test
    void testSaveMenuItem() {
        CreateMenuItemDto create = CreateMenuItemDto.builder()
                .name("Burger")
                .price(100)
                .weight(1.0)
                .ingredients("test")
                .build();

        MenuItemEntity entity = new MenuItemEntity();
        entity.setId(UUID.randomUUID());
        entity.setName("Burger");
        entity.setPrice(100);
        entity.setWeight(1.0);
        entity.setIngredients("test");

        when(menuItemRepository.save(any(MenuItemEntity.class))).thenReturn(entity);

        MenuItemDto result = menuItemService.save(create);

        assertEquals("Burger", result.getName());
        assertEquals(100, result.getPrice());
        assertEquals(1.0, result.getWeight());
        assertEquals("test", result.getIngredients());
    }

    @Test
    void testUpdateMenuItem() {
        UUID id = UUID.randomUUID();

        MenuItemEntity entity = new MenuItemEntity();
        entity.setId(id);

        CreateMenuItemDto dto = CreateMenuItemDto.builder()
                .name("Updated")
                .price(10)
                .weight(1.0)
                .ingredients("ing")
                .build();

        when(menuItemRepository.findById(id)).thenReturn(Optional.of(entity));
        when(menuItemRepository.save(entity)).thenReturn(entity);

        MenuItemDto result = menuItemService.updateById(id, dto);

        assertEquals("Updated", result.getName());
        assertEquals(10, result.getPrice());
        assertEquals(1.0, result.getWeight());
        assertEquals("ing", result.getIngredients());
    }

    @Test
    void testUpdateMenuItemNotFound() {
        UUID id = UUID.randomUUID();

        when(menuItemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class,
                () -> menuItemService.updateById(id, CreateMenuItemDto.builder()
                        .name("Updated")
                        .price(10)
                        .weight(1.0)
                        .ingredients("ing")
                        .build()));
    }

    @Test
    void testDeleteMenuItem() {
        UUID id = UUID.randomUUID();

        menuItemService.deleteById(id);

        verify(menuItemRepository).deleteById(id);
    }
}