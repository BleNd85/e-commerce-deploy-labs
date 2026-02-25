package com.example.restaurantmenuapi.repository;

import com.example.restaurantmenuapi.repository.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    @EntityGraph(attributePaths = {"orderEntries", "orderEntries.menuItem"})
    Optional<OrderEntity> findById(UUID id);

    Page<OrderEntity> findAllByTableAndDate(Integer tableNum, Date start, Date end, Pageable pageable);

    Page<OrderEntity> findAllByDate(Date start, Date end, Pageable pageable);

    Integer table(Integer table);
}
