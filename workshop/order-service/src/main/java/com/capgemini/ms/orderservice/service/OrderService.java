package com.capgemini.ms.orderservice.service;

import com.capgemini.ms.orderservice.repository.OrderCustomerRepository;
import com.capgemini.ms.orderservice.repository.OrderItemRepository;
import com.capgemini.ms.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderCustomerRepository customerRepository;

    @Autowired
    private OrderItemRepository itemRepository;
}
