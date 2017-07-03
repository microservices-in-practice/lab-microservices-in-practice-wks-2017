package com.capgemini.ms.catalogservice.repository;

import com.capgemini.ms.catalogservice.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
