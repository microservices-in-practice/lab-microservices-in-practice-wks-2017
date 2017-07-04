package com.capgemini.ms.orderservice.controller;


import com.capgemini.ms.orderservice.clients.CatalogClient;
import com.capgemini.ms.orderservice.clients.Customer;
import com.capgemini.ms.orderservice.clients.CustomerClient;
import com.capgemini.ms.orderservice.clients.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private CatalogClient catalogClient;

    @RequestMapping(path = "/customers", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getCustomers() {
        return Optional.ofNullable(customerClient.getAllCustomers())
                .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/items", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getItems() {
        return Optional.ofNullable(catalogClient.getAllItems())
                .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
