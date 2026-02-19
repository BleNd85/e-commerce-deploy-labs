package com.example.restaurantmenuapi.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "menu_items")
public class MenuItemEntity {
    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private String ingredients;

    @OneToMany(mappedBy = "menuItem")
    private List<OrderEntryEntity> orderEntries;
}
