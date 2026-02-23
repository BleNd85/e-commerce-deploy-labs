package com.example.restaurantmenuapi.config;

import com.example.restaurantmenuapi.service.mapper.MenuItemMapperTest;
import com.example.restaurantmenuapi.service.mapper.OrderMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MappersTestConfiguration {
    @Bean
    public MenuItemMapperTest menuItemMapper() {
        return Mappers.getMapper(MenuItemMapperTest.class);
    }

    @Bean
    public OrderMapper orderMapper() {
        return Mappers.getMapper(OrderMapper.class);
    }
}
