package com.capgemini.ms.customerservice.repository;

import com.capgemini.ms.customerservice.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRespositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private CustomerRepository customerRepository;


  @Test
  public void shouldFindCustomer() {
    // given

    Customer customer = getCustomer();
    Customer savedCustomer = entityManager.persist(customer);
    entityManager.flush();

    // when

    Customer found = customerRepository.findOne(savedCustomer.getId());

    // then

    assertThat(found.getName())
        .isEqualTo(customer.getName());
    assertThat(found.getEmail())
        .isEqualTo(customer.getEmail());
  }

  @Test
  public void shouldSaveCustomer() {
    // given

    Customer customer = getCustomer();
    Customer savedCustomer = customerRepository.save(customer);

    entityManager.flush();

    // when

    Customer found = entityManager.find(Customer.class, savedCustomer.getId());

    // then

    assertThat(found.getName())
        .isEqualTo(customer.getName());
    assertThat(found.getEmail())
        .isEqualTo(customer.getEmail());
  }


  private Customer getCustomer() {
    Customer customer = new Customer();
    customer.setName("Customer 1");
    customer.setEmail("Customer1@email.com");

    return customer;
  }
}
