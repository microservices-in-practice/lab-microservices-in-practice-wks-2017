package com.capgemini.ms.customerservice.controller;

import com.capgemini.ms.customerservice.domain.Customer;
import com.capgemini.ms.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  @Autowired
  private CustomerService customerService;


  @GetMapping(path = "/{id}")
  public ResponseEntity<Customer> getCustomer(@PathVariable long id) {
    return Optional.ofNullable(customerService.getCustomer(id))
        .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping()
  public ResponseEntity<List<Customer>> getCustomers() {
    return Optional.ofNullable(customerService.findCustomers())
        .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping()
  public ResponseEntity<Customer> saveOrUpdate(@RequestBody Customer customer) {
    return Optional.ofNullable(customer.getId() != null ?
        customerService.update(customer) : customerService.create(customer))
        .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

}
