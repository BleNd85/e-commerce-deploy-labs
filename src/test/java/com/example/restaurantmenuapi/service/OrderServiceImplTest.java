package com.example.restaurantmenuapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.example.restaurantmenuapi.config.MappersTestConfiguration;
import com.example.restaurantmenuapi.dto.OrderDto;
import com.example.restaurantmenuapi.dto.create.CreateOrderDto;
import com.example.restaurantmenuapi.dto.create.CreateOrderEntryDto;
import com.example.restaurantmenuapi.repository.MenuItemRepository;
import com.example.restaurantmenuapi.repository.OrderRepository;
import com.example.restaurantmenuapi.repository.entity.MenuItemEntity;
import com.example.restaurantmenuapi.repository.entity.OrderEntity;
import com.example.restaurantmenuapi.repository.entity.OrderEntryEntity;
import com.example.restaurantmenuapi.service.exception.MenuItemNotFoundException;
import com.example.restaurantmenuapi.service.exception.OrderEntryNotFoundException;
import com.example.restaurantmenuapi.service.exception.OrderNotFoundException;
import com.example.restaurantmenuapi.service.implementation.OrderServiceImpl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(classes = { OrderServiceImpl.class })
@Import(MappersTestConfiguration.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("Order Service Test")
class OrderServiceImplTest {

    @MockitoBean
    private OrderRepository orderRepository;

    @MockitoBean
    private MenuItemRepository menuItemRepository;

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    void testGetAllOrders() {
        List<OrderEntity> entities = List.of(new OrderEntity());
        Page<OrderEntity> page = new PageImpl<>(entities);

        when(orderRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<OrderDto> result = orderService.getAll(null, null, null, PageRequest.of(0, 10));

        assertEquals(entities.size(), result.getContent().size());
    }

    @Test
    void testGetAllOrdersByTableAndDate() {
        List<OrderEntity> entities = List.of(new OrderEntity());
        Page<OrderEntity> page = new PageImpl<>(entities);
        Date start = new Date();
        Date end = new Date();

        when(orderRepository.findAllByTableAndDate(eq(1), eq(start), eq(end), any(Pageable.class))).thenReturn(page);

        Page<OrderDto> result = orderService.getAll(1, start, end, PageRequest.of(0, 10));

        assertEquals(entities.size(), result.getContent().size());
    }

    @Test
    void testGetAllOrdersByDate() {
        List<OrderEntity> entities = List.of(new OrderEntity());
        Page<OrderEntity> page = new PageImpl<>(entities);
        Date start = new Date();
        Date end = new Date();

        when(orderRepository.findAllByDate(eq(start), eq(end), any(Pageable.class))).thenReturn(page);

        Page<OrderDto> result = orderService.getAll(null, start, end, PageRequest.of(0, 10));

        assertEquals(entities.size(), result.getContent().size());
    }

    @Test
    void testGetOrderById() {
        UUID id = UUID.randomUUID();
        OrderEntity entity = new OrderEntity();
        entity.setId(id);

        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));

        OrderDto result = orderService.getById(id);

        assertEquals(id, result.getId());
    }

    @Test
    void testGetOrderByIdNotFound() {
        UUID id = UUID.randomUUID();

        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> orderService.getById(id));
    }

    @Test
    void testSaveOrder() {
        CreateOrderDto create = CreateOrderDto.builder()
                .table(5)
                .build();

        OrderEntity entity = new OrderEntity();
        entity.setId(UUID.randomUUID());
        entity.setTable(5);
        entity.setDate(new Date());

        when(orderRepository.save(any(OrderEntity.class))).thenReturn(entity);

        OrderDto result = orderService.save(create);

        assertEquals(5, result.getTable());
    }

    @Test
    void testUpdateOrderTable() {
        UUID id = UUID.randomUUID();

        OrderEntity entity = new OrderEntity();
        entity.setId(id);

        CreateOrderDto dto = CreateOrderDto.builder()
                .table(10)
                .build();

        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(orderRepository.save(entity)).thenReturn(entity);

        OrderDto result = orderService.updateTableById(id, dto);

        assertEquals(10, result.getTable());
    }

    @Test
    void testUpdateOrderTableNotFound() {
        UUID id = UUID.randomUUID();

        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> orderService.updateTableById(id, CreateOrderDto.builder().table(1).build()));
    }

    @Test
    void testDeleteOrder() {
        UUID id = UUID.randomUUID();

        orderService.deleteById(id);

        verify(orderRepository).deleteById(id);
    }

    @Test
    void testAddEntry() {
        UUID orderId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();

        OrderEntity order = new OrderEntity();
        order.setId(orderId);
        order.setOrderEntries(new java.util.ArrayList<>());

        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setId(itemId);

        CreateOrderEntryDto dto = CreateOrderEntryDto.builder()
                .menuItemId(itemId)
                .quantity(2)
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(menuItemRepository.findById(itemId)).thenReturn(Optional.of(menuItem));
        when(orderRepository.save(order)).thenReturn(order);

        orderService.addEntry(orderId, dto);

        assertEquals(1, order.getOrderEntries().size());
        assertEquals(2, order.getOrderEntries().get(0).getQuantity());
    }

    @Test
    void testAddEntryOrderNotFound() {
        UUID orderId = UUID.randomUUID();

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> orderService.addEntry(orderId, CreateOrderEntryDto.builder()
                        .menuItemId(UUID.randomUUID())
                        .quantity(2)
                        .build()));
    }

    @Test
    void testAddEntryMenuItemNotFound() {
        UUID orderId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();

        OrderEntity order = new OrderEntity();
        order.setId(orderId);
        order.setOrderEntries(new java.util.ArrayList<>());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(menuItemRepository.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class,
                () -> orderService.addEntry(orderId, CreateOrderEntryDto.builder()
                        .menuItemId(itemId)
                        .quantity(2)
                        .build()));
    }

    @Test
    void testUpdateEntry() {
        UUID orderId = UUID.randomUUID();
        UUID entryId = UUID.randomUUID();

        OrderEntryEntity entry = new OrderEntryEntity();
        entry.setId(entryId);
        entry.setQuantity(1);

        OrderEntity order = new OrderEntity();
        order.setId(orderId);
        order.setOrderEntries(new java.util.ArrayList<>(List.of(entry)));

        CreateOrderEntryDto dto = CreateOrderEntryDto.builder()
                .menuItemId(UUID.randomUUID())
                .quantity(5)
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        orderService.updateEntry(orderId, entryId, dto);

        assertEquals(5, entry.getQuantity());
    }

    @Test
    void testUpdateEntryNotFound() {
        UUID orderId = UUID.randomUUID();
        UUID entryId = UUID.randomUUID();

        OrderEntity order = new OrderEntity();
        order.setId(orderId);
        order.setOrderEntries(new java.util.ArrayList<>());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(OrderEntryNotFoundException.class,
                () -> orderService.updateEntry(orderId, entryId,
                        CreateOrderEntryDto.builder()
                                .menuItemId(UUID.randomUUID())
                                .quantity(1)
                                .build()));
    }

    @Test
    void testRemoveEntry() {
        UUID orderId = UUID.randomUUID();
        UUID entryId = UUID.randomUUID();

        OrderEntryEntity entry = new OrderEntryEntity();
        entry.setId(entryId);

        OrderEntity order = new OrderEntity();
        order.setId(orderId);
        order.setOrderEntries(new java.util.ArrayList<>(List.of(entry)));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        orderService.removeEntry(orderId, entryId);

        assertEquals(0, order.getOrderEntries().size());
    }

    @Test
    void testRemoveEntryNotFound() {
        UUID orderId = UUID.randomUUID();
        UUID entryId = UUID.randomUUID();

        OrderEntity order = new OrderEntity();
        order.setId(orderId);
        order.setOrderEntries(new java.util.ArrayList<>());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(OrderEntryNotFoundException.class,
                () -> orderService.removeEntry(orderId, entryId));
    }
}