package com.example.restaurantmenuapi.service.mapper;

import com.example.restaurantmenuapi.dto.OrderDto;
import com.example.restaurantmenuapi.dto.create.CreateOrderDto;
import com.example.restaurantmenuapi.repository.entity.MenuItemEntity;
import com.example.restaurantmenuapi.repository.entity.OrderEntity;
import com.example.restaurantmenuapi.repository.entity.OrderEntryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderMapperTest {
    private OrderMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(OrderMapper.class);
        Mappers.getMapper(MenuItemMapper.class);
    }

    @Test
    void shouldMapCreateOrderDtoToOrderEntity() {
        CreateOrderDto createOrderDto = CreateOrderDto.builder()
                .table(4)
                .build();

        OrderEntity entity = mapper.toEntity(createOrderDto);

        assertNotNull(entity);
        assertEquals(entity.getTable(), createOrderDto.getTable());
    }

    @Test
    void shouldMapOrderEntityToOrderDto() {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(UUID.randomUUID())
                .table(4)
                .date(new Date())
                .orderEntries(new ArrayList<>())
                .build();

        MenuItemEntity menuItemEntity = MenuItemEntity.builder()
                .id(UUID.randomUUID())
                .name("Test item 1")
                .price(100)
                .weight(2.5)
                .ingredients("Ingredient 1, ingredient 2")
                .orderEntries(new ArrayList<>())
                .build();

        OrderEntryEntity orderEntryEntity = OrderEntryEntity.builder()
                .id(UUID.randomUUID())
                .order(orderEntity)
                .menuItem(menuItemEntity)
                .quantity(3)
                .build();

        orderEntity.addEntry(orderEntryEntity);
        menuItemEntity.setOrderEntries(List.of(orderEntryEntity));

        OrderDto orderDto = mapper.toDto(orderEntity);

        assertNotNull(orderDto);
        assertEquals(orderDto.getId(), orderEntity.getId());
        assertEquals(orderDto.getDate(), orderEntity.getDate());
        assertEquals(orderDto.getTable(), orderEntity.getTable());
        assertThat(orderDto.getOrderEntries())
                .usingRecursiveComparison()
                .isEqualTo(orderEntity.getOrderEntries());
    }
}
