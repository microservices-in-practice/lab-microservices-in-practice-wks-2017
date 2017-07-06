package com.capgemini.ms.orderservice.clients;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class CustomerClientHystrix {

  private final Logger log = LoggerFactory.getLogger(CustomerClientHystrix.class);

  @Value("${customer.service.url}")
  private String customerServiceUrl;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private CacheManager cacheManager;


  @CachePut("customers")
  @HystrixCommand(fallbackMethod = "getAllCustomersFromCache", commandProperties = {
      @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")})
  public List<Customer> getAllCustomers() {
    return restTemplate.getForObject(customerServiceUrl, List.class);
  }

  public List<Customer> getAllCustomersFromCache() {
    if (cacheManager.getCache("customers") != null && cacheManager.getCache("customers").get(SimpleKey.EMPTY) != null) {
      return cacheManager.getCache("customers").get(SimpleKey.EMPTY, List.class);
    } else {
      return Lists.newArrayList();
    }
  }

  @CachePut(cacheNames = "customer", key = "#id")
  @HystrixCommand(fallbackMethod = "getCustomerFromCache", commandProperties = {
      @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")})
  public Customer getCustomer(long id) {
    return restTemplate.getForObject(customerServiceUrl + id, Customer.class);
  }

  public Customer getCustomerFromCache(long id) {
    if (cacheManager.getCache("customer") != null && cacheManager.getCache("customers").get(SimpleKey.EMPTY) != null) {
      return cacheManager.getCache("customer").get(id, Customer.class);
    } else {
      return null;
    }
  }

}
