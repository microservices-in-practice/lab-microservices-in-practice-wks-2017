package com.capgemini.ms.orderservice.repository;

import com.capgemini.ms.orderservice.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    OrderItem findByNameAndVersion(String name, int version);
}
