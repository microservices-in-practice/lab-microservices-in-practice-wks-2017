package com.capgemini.ms.orderservice.repository;

import com.capgemini.ms.orderservice.domain.OrderCustomer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderCustomerRespositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private OrderCustomerRepository customerRepository;


  @Test
  public void shouldFindCustomer() {
    // given

    OrderCustomer customer = getCustomer();
    OrderCustomer savedCustomer = entityManager.persist(customer);
    entityManager.flush();

    // when

    OrderCustomer found = customerRepository.findOne(savedCustomer.getId());

    // then

    assertThat(found.getName())
        .isEqualTo(customer.getName());
    assertThat(found.getEmail())
        .isEqualTo(customer.getEmail());
    assertThat(found.getVersion())
            .isEqualTo(customer.getVersion());
  }

  @Test
  public void shouldSaveCustomer() {
    // given

    OrderCustomer customer = getCustomer();
    OrderCustomer savedCustomer = customerRepository.save(customer);

    entityManager.flush();

    // when

    OrderCustomer found = entityManager.find(OrderCustomer.class, savedCustomer.getId());

    // then

    assertThat(found.getName())
        .isEqualTo(customer.getName());
    assertThat(found.getEmail())
        .isEqualTo(customer.getEmail());
    assertThat(found.getVersion())
            .isEqualTo(customer.getVersion());
  }

  @Test
  public void shouldFindByNameAndVersion() {
    // given

    OrderCustomer customer = getCustomer();
    OrderCustomer savedCustomer = customerRepository.save(customer);

    entityManager.flush();

    // when

    OrderCustomer found = customerRepository.findByNameAndVersion(savedCustomer.getName(), savedCustomer.getVersion());

    // then

    assertThat(found.getName())
            .isEqualTo(customer.getName());
    assertThat(found.getEmail())
            .isEqualTo(customer.getEmail());
    assertThat(found.getVersion())
            .isEqualTo(customer.getVersion());
  }


  private OrderCustomer getCustomer() {
    OrderCustomer customer = new OrderCustomer();
    customer.setName("Customer 1");
    customer.setEmail("Customer1@email.com");
    customer.setVersion(1);

    return customer;
  }
}
