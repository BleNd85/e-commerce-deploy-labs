package com.example.restaurantmenuapi.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class OrderEntity {

    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Integer table;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderEntryEntity> orderEntries;

    public void addEntry(OrderEntryEntity orderEntry) {
        orderEntries.add(orderEntry);
        orderEntry.setOrder(this);
    }

    public void removeEntry(OrderEntryEntity orderEntry) {
        orderEntries.remove(orderEntry);
        orderEntry.setOrder(null);
    }
}
