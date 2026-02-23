package com.example.restaurantmenuapi.service.mapper;

import com.example.restaurantmenuapi.dto.create.CreateOrderDto;
import com.example.restaurantmenuapi.repository.entity.OrderEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

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
}
