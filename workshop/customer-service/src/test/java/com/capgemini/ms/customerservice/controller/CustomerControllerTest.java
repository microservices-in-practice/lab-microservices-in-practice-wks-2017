package com.capgemini.ms.customerservice.controller;


import com.capgemini.ms.customerservice.domain.Customer;
import com.capgemini.ms.customerservice.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

  public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8")
  );

  @Autowired
  MockMvc mvc;

  @MockBean
  CustomerService customerService;


  @Test
  public void shouldGetCustomer() throws Exception {
    String customerJson = "{\"id\": 1,\"name\": \"Customer 1\",\"email\": \"Customer1@email.com\",\"version\": 0}";
    Customer customer = new Customer();
    String name = "Customer 1";
    String email = "Customer1@email.com";
    customer.setName(name);
    customer.setEmail(email);
    customer.setId(1L);

    given(this.customerService.getCustomer(1L)).willReturn(customer);

    this.mvc.perform(get("/customers/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("id", is(1)))
        .andExpect(content().json(customerJson));

    verify(this.customerService, times(1)).getCustomer(1L);
    verifyNoMoreInteractions(this.customerService);
  }

  @Test
  public void shouldGetCustomers() throws Exception {
    String customerJson = "[{\"id\": 1,\"name\": \"Customer 1\",\"email\": \"Customer1@email.com\",\"version\": 0}]";
    Customer customer = new Customer();
    String name = "Customer 1";
    String email = "Customer1@email.com";
    customer.setName(name);
    customer.setEmail(email);
    customer.setId(1L);

    List<Customer> cList = new ArrayList<Customer>();
    cList.add(customer);
    given(this.customerService.findCustomers()).willReturn(cList);

    this.mvc.perform(get("/customers").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(content().json(customerJson));

    verify(this.customerService, times(1)).findCustomers();
    verifyNoMoreInteractions(this.customerService);
  }

  @Test
  public void shouldCreateCustomer() throws Exception {
    String customerJson = "{\"id\": 1,\"name\": \"Customer 1\",\"email\": \"Customer1@email.com\",\"version\": 0}";
    String updatedCustomerJson = "{\"id\": 1,\"name\": \"Customer 1\",\"email\": \"Customer1@email.com\",\"version\": 1}";
    Customer customer = new Customer();
    customer.setName("Customer 1");
    customer.setEmail("Customer1@email.com");
    customer.setVersion(0);
    customer.setId(1L);

    Customer updatedCustomer = new Customer();
    updatedCustomer.setName("Customer 1");
    updatedCustomer.setEmail("Customer1@email.com");
    updatedCustomer.setVersion(1);
    updatedCustomer.setId(1L);

    given(this.customerService.getCustomer(1L)).willReturn(customer);
    given(this.customerService.update(customer)).willReturn(updatedCustomer);

    this.mvc.perform(post("/customers/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(customerJson))
        .andExpect(status().isOk())
        .andExpect(content().json(updatedCustomerJson));
  }
}
