package com.capgemini.ms.orderservice.repository;

import com.capgemini.ms.orderservice.domain.*;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRespositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderCustomerRepository customerRepository;

    @Autowired
    private OrderItemRepository itemRepository;


    @Test
    public void shouldFindOrder() {
        // given

        OrderCustomer customer = customerRepository.save(getCustomer());
        OrderItem item = itemRepository.save(getItem());
        Order savedOrder = entityManager.persist(getOrder(customer, Lists.newArrayList(item)));

        entityManager.flush();

        // when

        Order found = orderRepository.findOne(savedOrder.getId());

        // then

        assertThat(found.getId())
                .isEqualTo(savedOrder.getId());
        assertThat(found.getBillingAddress())
                .isEqualToComparingFieldByField(savedOrder.getBillingAddress());
        assertThat(found.getShippingAddress())
                .isEqualToComparingFieldByField(savedOrder.getShippingAddress());
        assertThat(found.getCustomer())
                .isEqualToComparingFieldByField(savedOrder.getCustomer());
        assertThat(found.getOrderLine())
                .hasSize(savedOrder.getOrderLine().size());
    }

    @Test
    public void shouldSaveOrder() {
        // given

        OrderCustomer customer = customerRepository.save(getCustomer());
        OrderItem item = itemRepository.save(getItem());
        Order savedOrder = orderRepository.save(getOrder(customer, Lists.newArrayList(item)));

        entityManager.flush();

        // when

        Order found = entityManager.find(Order.class, savedOrder.getId());

        // then

        assertThat(found.getId())
                .isEqualTo(savedOrder.getId());
        assertThat(found.getBillingAddress())
                .isEqualToComparingFieldByField(savedOrder.getBillingAddress());
        assertThat(found.getShippingAddress())
                .isEqualToComparingFieldByField(savedOrder.getShippingAddress());
        assertThat(found.getCustomer())
                .isEqualToComparingFieldByField(savedOrder.getCustomer());
        assertThat(found.getOrderLine())
                .hasSize(savedOrder.getOrderLine().size());

    }

    @Test
    public void shouldFindByCustomerName() {
        // given

        OrderCustomer customer = customerRepository.save(getCustomer());
        OrderItem item = itemRepository.save(getItem());
        Order savedOrder = orderRepository.save(getOrder(customer, Lists.newArrayList(item)));

        entityManager.flush();

        // when

        List<Order> orders = orderRepository.findByCustomerName(customer.getName());

        // then

        assertThat(orders).isNotNull();
        assertThat(orders).hasSize(1);

    }

    private OrderCustomer getCustomer() {
        OrderCustomer customer = new OrderCustomer();
        customer.setName("Customer 1");
        customer.setEmail("Customer1@email.com");
        customer.setVersion(1);

        return customer;
    }

    private OrderItem getItem() {
        OrderItem item = new OrderItem();
        item.setName("Item 1");
        item.setDescription("Item 1");
        item.setPrice(100f);
        item.setVersion(1);

        return item;
    }

    private Order getOrder(OrderCustomer customer, List<OrderItem> items) {
        Order order = new Order();
        order.setCustomer(customer);
        Address billingAddres = new Address();
        billingAddres.setCity("Wroclaw");
        billingAddres.setStreet("Strzegomska");
        billingAddres.setZip("53-611");
        order.setBillingAddress(billingAddres);
        Address shippingAddres = new Address();
        shippingAddres.setCity("Wroclaw");
        shippingAddres.setStreet("Strzegomska");
        shippingAddres.setZip("53-611");
        order.setShippingAddress(shippingAddres);

        order.setOrderLine(items.stream().map(i -> {
            OrderLine line = new OrderLine();
            line.setAmount(1);
            line.setItem(i);
            return line;
        }).collect(Collectors.toSet()));

        return order;
    }
}
