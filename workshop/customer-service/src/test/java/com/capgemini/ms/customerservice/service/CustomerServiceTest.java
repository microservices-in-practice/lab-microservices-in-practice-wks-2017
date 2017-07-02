package com.capgemini.ms.customerservice.service;

import com.capgemini.ms.customerservice.domain.Customer;
import com.capgemini.ms.customerservice.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@DirtiesContext
public class CustomerServiceTest {


  @Autowired
  CustomerService customerService;

  @Test
  public void shouldCreateCustomer() {

    // given

    Customer newCustomer = getNewCustomer();

    // when

    customerService.create(newCustomer);

    // then

    verify(CustomerServiceImplTestContextConfiguration.repository, times(1)).save(newCustomer);
  }

  @Test
  public void shouldUpdateCustomer() {

    // given

    given(CustomerServiceImplTestContextConfiguration.repository.findOne(1L)).willReturn(getCustomer());
    given(CustomerServiceImplTestContextConfiguration.repository.exists(1L)).willReturn(true);
    Customer customer = CustomerServiceImplTestContextConfiguration.repository.findOne(1L);
    customer.setName("Updated");

    // when

    customerService.update(customer);

    // then

    verify(CustomerServiceImplTestContextConfiguration.repository, times(1)).save(customer);
  }

  @Test
  public void shouldGetCustomer() {

    // given

    given(CustomerServiceImplTestContextConfiguration.repository.findOne(1L)).willReturn(getCustomer());

    // when

    Customer customer = customerService.getCustomer(1L);

    // then

    assertThat(customer.getEmail()).isEqualTo(getCustomer().getEmail());
    assertThat(customer.getName()).isEqualTo(getCustomer().getName());
  }

  @Test
  public void shouldGetCustomers() {

    // given

    List<Customer> getList = Collections.singletonList(getCustomer());
    given(CustomerServiceImplTestContextConfiguration.repository.findAll()).willReturn(getList);

    // when

    List<Customer> getCustomerList = customerService.findCustomers();

    // then

    assertThat(getCustomerList.size()).isEqualTo(1);
    assertThat(getCustomerList.get(0).getName()).isEqualTo(getCustomer().getName());
  }

  private Customer getCustomer() {
    Customer customer = new Customer();
    customer.setName("Customer 1");
    customer.setEmail("Customer1@gft.com");
    customer.setId(1L);
    return customer;
  }

  private Customer getNewCustomer() {
    Customer customer = new Customer();
    customer.setName("Customer 1");
    customer.setEmail("Customer1@gft.com");
    return customer;
  }

  @TestConfiguration
  static class CustomerServiceImplTestContextConfiguration {

    @MockBean
    private static CustomerRepository repository;

    @Bean
    public CustomerService customerService() {
      CustomerService customerService = new CustomerService();
      customerService.setCustomerRepository(repository);
      return customerService;
    }
  }

}
