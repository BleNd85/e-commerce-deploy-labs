package com.example.restaurantmenuapi.service.implementation;

import com.example.restaurantmenuapi.dto.OrderDto;
import com.example.restaurantmenuapi.dto.create.CreateOrderDto;
import com.example.restaurantmenuapi.dto.create.CreateOrderEntryDto;
import com.example.restaurantmenuapi.repository.MenuItemRepository;
import com.example.restaurantmenuapi.repository.OrderRepository;
import com.example.restaurantmenuapi.repository.entity.MenuItemEntity;
import com.example.restaurantmenuapi.repository.entity.OrderEntity;
import com.example.restaurantmenuapi.repository.entity.OrderEntryEntity;
import com.example.restaurantmenuapi.service.OrderService;
import com.example.restaurantmenuapi.service.exception.MenuItemNotFoundException;
import com.example.restaurantmenuapi.service.exception.OrderEntryNotFoundException;
import com.example.restaurantmenuapi.service.exception.OrderNotFoundException;
import com.example.restaurantmenuapi.service.mapper.OrderMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final MenuItemRepository menuItemRepository;

    public OrderServiceImpl(OrderRepository repository, OrderMapper mapper, MenuItemRepository menuItemRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public Page<OrderDto> getAll(Integer tableNum, Date start, Date end, Pageable pageable) {
        if (tableNum != null && start != null && end != null) {
            return repository.findAllByTableAndDate(tableNum, start, end, pageable).map(mapper::toDto);
        } else if (start != null && end != null) {
            return repository.findAllByDate(start, end, pageable).map(mapper::toDto);
        }
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto getById(UUID id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id)));
    }

    @Override
    @Transactional
    public OrderDto save(CreateOrderDto createOrderDto) {
        OrderEntity entity = mapper.toEntity(createOrderDto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    public OrderDto updateTableById(UUID id, CreateOrderDto createOrderDto) {
        OrderEntity entity = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        entity.setTable(createOrderDto.getTable());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    public OrderDto addEntry(UUID id, CreateOrderEntryDto createOrderEntryDto) {
        UUID itemId = createOrderEntryDto.getMenuItemId();
        OrderEntity orderEntity = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        MenuItemEntity menuItemEntity = menuItemRepository.findById(itemId).orElseThrow(() -> new MenuItemNotFoundException(itemId));
        OrderEntryEntity entryEntity = OrderEntryEntity.builder()
                .id(UUID.randomUUID())
                .quantity(createOrderEntryDto.getQuantity())
                .menuItem(menuItemEntity)
                .build();
        orderEntity.addEntry(entryEntity);
        return mapper.toDto(repository.save(orderEntity));
    }

    @Override
    @Transactional
    public OrderDto updateEntry(UUID id, UUID entryId, CreateOrderEntryDto dto) {
        OrderEntity orderEntity = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        OrderEntryEntity orderEntryEntity = orderEntity.getOrderEntries().stream()
                .filter(e -> e.getId().equals(entryId))
                .findFirst()
                .orElseThrow(() -> new OrderEntryNotFoundException(entryId));

        orderEntryEntity.setQuantity(dto.getQuantity());

        return mapper.toDto(repository.save(orderEntity));
    }

    @Override
    @Transactional
    public OrderDto removeEntry(UUID id, UUID entryId) {
        OrderEntity orderEntity = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        OrderEntryEntity orderEntryEntity = orderEntity.getOrderEntries().stream()
                .filter(e -> e.getId().equals(entryId))
                .findFirst()
                .orElseThrow(() -> new OrderEntryNotFoundException(entryId));
        orderEntity.removeEntry(orderEntryEntity);
        return mapper.toDto(repository.save(orderEntity));
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
