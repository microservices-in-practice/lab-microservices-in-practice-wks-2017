package com.capgemini.ms.orderservice.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class CatalogClient {

  private final Logger log = LoggerFactory.getLogger(CatalogClient.class);

  @Value("${catalog.service.url}")
  private String catalogServiceUrl;

  private RestTemplate restTemplate = new RestTemplate();

  public List<Item> getAllItems() {
    return restTemplate.getForObject(catalogServiceUrl, List.class);
  }

  public Item getItem(long id) {
    return restTemplate.getForObject(catalogServiceUrl + id, Item.class);
  }

}
