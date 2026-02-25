package com.example.restaurantmenuapi.web;

import com.example.restaurantmenuapi.dto.OrderDto;
import com.example.restaurantmenuapi.dto.create.CreateOrderDto;
import com.example.restaurantmenuapi.dto.create.CreateOrderEntryDto;
import com.example.restaurantmenuapi.service.OrderService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<OrderDto>> getAll(
            @RequestParam(name = "tableNum", required = false) Integer tableNum,
            @RequestParam(name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end,
            @ParameterObject @PageableDefault(size = 10, sort = "date") Pageable pageable) {

        return ResponseEntity.ok(service.getAll(tableNum, start, end, pageable));
    }

    @PostMapping
    public ResponseEntity<OrderDto> create(@RequestBody @Valid CreateOrderDto createOrderDto) {
        return ResponseEntity.ok(service.save(createOrderDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDto> updateById(@PathVariable("id") UUID id, @RequestBody @Valid CreateOrderDto createOrderDto) {
        return ResponseEntity.ok(service.updateTableById(id, createOrderDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/entries")
    public ResponseEntity<OrderDto> createEntry(@PathVariable("id") UUID id, @RequestBody @Valid CreateOrderEntryDto createOrderEntryDto) {
        return ResponseEntity.ok(service.addEntry(id, createOrderEntryDto));
    }

    @PatchMapping("/{orderId}/entries/{entryId}")
    public ResponseEntity<OrderDto> updateEntryById(@PathVariable("orderId") UUID orderId, @PathVariable("entryId") UUID entryId, @RequestBody @Valid CreateOrderEntryDto createOrderEntryDto) {
        return ResponseEntity.ok(service.updateEntry(orderId, entryId, createOrderEntryDto));
    }

    @DeleteMapping("/{orderId}/entries/{entryId}")
    public ResponseEntity<OrderDto> deleteEntryById(@PathVariable("orderId") UUID orderId, @PathVariable("entryId") UUID entryId) {
        return ResponseEntity.ok(service.removeEntry(orderId, entryId));
    }
}
