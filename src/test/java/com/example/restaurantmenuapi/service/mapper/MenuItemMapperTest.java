package com.example.restaurantmenuapi.service.mapper;

import com.example.restaurantmenuapi.dto.MenuItemDto;
import com.example.restaurantmenuapi.dto.create.CreateMenuItemDto;
import com.example.restaurantmenuapi.repository.entity.MenuItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MenuItemMapperTest {
    private MenuItemMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(MenuItemMapper.class);
    }

    @Test
    void shouldMapCreateMenuItemDtoToMenuItemEntity() {
        CreateMenuItemDto createMenuItemDto = CreateMenuItemDto.builder()
                .name("Test item 1")
                .price(100)
                .weight(2.5)
                .ingredients("Ingredient 1, ingredient 2")
                .build();

        MenuItemEntity menuItemEntity = mapper.toEntity(createMenuItemDto);

        assertNotNull(menuItemEntity);
        assertEquals(createMenuItemDto.getName(), menuItemEntity.getName());
        assertEquals(createMenuItemDto.getIngredients(), menuItemEntity.getIngredients());
        assertEquals(createMenuItemDto.getPrice(), menuItemEntity.getPrice());
        assertEquals(createMenuItemDto.getWeight(), menuItemEntity.getWeight());

    }
}
