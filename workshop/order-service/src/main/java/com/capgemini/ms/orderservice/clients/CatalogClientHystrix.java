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
public class CatalogClientHystrix {

  private final Logger log = LoggerFactory.getLogger(CatalogClientHystrix.class);

  @Value("${catalog.service.url}")
  private String catalogServiceUrl;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private CacheManager cacheManager;


  @CachePut("items")
  @HystrixCommand(fallbackMethod = "getAllItemsFromCache", commandProperties = {
      @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")})
  public List<Item> getAllItems() {
    return restTemplate.getForObject(catalogServiceUrl, List.class);
  }

  public List<Item> getAllItemsFromCache() {
    if (cacheManager.getCache("items") != null && cacheManager.getCache("items").get(SimpleKey.EMPTY) != null) {
      return cacheManager.getCache("items").get(SimpleKey.EMPTY, List.class);
    } else {
      return Lists.newArrayList();
    }
  }

  @CachePut(cacheNames = "item", key = "#id")
  @HystrixCommand(fallbackMethod = "getItemFromCache", commandProperties = {
      @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")})
  public Item getItem(long id) {
    return restTemplate.getForObject(catalogServiceUrl + id, Item.class);
  }

  public Item getItemFromCache(long id) {
    if (cacheManager.getCache("item") != null && cacheManager.getCache("customers").get(SimpleKey.EMPTY) != null) {
      return cacheManager.getCache("item").get(id, Item.class);
    } else {
      return null;
    }
  }

}
