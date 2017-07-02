package com.capgemini.ms.customerservice.service;

import com.capgemini.ms.customerservice.domain.Customer;
import com.capgemini.ms.customerservice.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class CustomerService {

  private final Logger log = LoggerFactory.getLogger(CustomerService.class);

  @Autowired
  private CustomerRepository repository;

  public Customer create(Customer customer) {
    return repository.save(customer);
  }

  /**
   * Get All customers
   *
   * @return customers List<Customer>
   */
  public List<Customer> findCustomers() {
    return repository.findAll();
  }

  /**
   * Get a {@link Customer} entity by the given id
   *
   * @param id is the unique identifier of the {@link Customer} entity
   * @return an {@link Customer} entity
   */
  public Customer getCustomer(long id) {
    // Get the customer from the repository
    Customer customer = repository.findOne(id);
    Assert.notNull(customer, "A Customer with the supplied id doesn't exist");
    return customer;
  }

  /**
   * Update a {@link Customer} entity with the supplied identifier
   *
   * @param customer is the {@link Customer} containing the updated fields
   * @return the updated {@link Customer} entity
   */
  public Customer update(Customer customer) {
    Assert.notNull(customer.getId(), "Customer id must be present in the resource URL");
    Assert.notNull(customer, "Customer request body cannot be null");
    Assert.state(repository.exists(customer.getId()), "The customer with the supplied id does not exist");
    Customer currentCustomer = getCustomer(customer.getId());
    currentCustomer.setEmail(customer.getEmail());
    currentCustomer.setName(customer.getName());
    return repository.save(customer);
  }

  public void setCustomerRepository(CustomerRepository repository) {
    this.repository = repository;
  }
}
