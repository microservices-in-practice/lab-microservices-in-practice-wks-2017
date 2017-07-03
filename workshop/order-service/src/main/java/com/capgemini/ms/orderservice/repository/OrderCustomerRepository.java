package com.capgemini.ms.orderservice.repository;

import com.capgemini.ms.orderservice.domain.OrderCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCustomerRepository extends JpaRepository<OrderCustomer, Long> {

    OrderCustomer findByNameAndVersion(String name, int version);
}
