package com.capgemini.ms.orderservice.repository;

import com.capgemini.ms.orderservice.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.customer.name = :name")
    List<Order> findByCustomerName(@Param("name") String name);
}
