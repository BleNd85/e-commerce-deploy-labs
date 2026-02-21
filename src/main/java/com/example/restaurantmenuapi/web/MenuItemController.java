package com.example.restaurantmenuapi.web;

import com.example.restaurantmenuapi.dto.MenuItemDto;
import com.example.restaurantmenuapi.dto.create.CreateMenuItemDto;
import com.example.restaurantmenuapi.service.MenuItemService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/menu-items")
public class MenuItemController {
    private final MenuItemService service;

    public MenuItemController(MenuItemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<MenuItemDto>> getAll(@ParameterObject @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<MenuItemDto> create(@RequestBody @Valid CreateMenuItemDto createMenuItemDto) {
        return ResponseEntity.ok(service.save(createMenuItemDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MenuItemDto> updateById(@PathVariable UUID id, @RequestBody @Valid CreateMenuItemDto createMenuItemDto) {
        return ResponseEntity.ok(service.updateById(id, createMenuItemDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
