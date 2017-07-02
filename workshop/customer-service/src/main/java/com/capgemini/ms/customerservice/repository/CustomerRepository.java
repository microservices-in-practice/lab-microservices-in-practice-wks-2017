package com.capgemini.ms.customerservice.repository;

import com.capgemini.ms.customerservice.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
