package com.capgemini.ms.catalogservice.service;

import com.capgemini.ms.catalogservice.domain.Item;
import com.capgemini.ms.catalogservice.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ItemService {

  private final Logger log = LoggerFactory.getLogger(ItemService.class);

  @Autowired
  private ItemRepository repository;

  public Item create(Item item) {
    return repository.save(item);
  }


  public List<Item> findItems() {
    return repository.findAll();
  }

  public Item getItem(long id) {
    // Get the item from the repository
    Item item = repository.findOne(id);
    Assert.notNull(item, "An item with the supplied id doesn't exist");
    return item;
  }

  public Item update(Item item) {
    Assert.notNull(item.getId(), "Item id must be present in the resource URL");
    Assert.notNull(item, "Item request body cannot be null");
    Assert.state(repository.exists(item.getId()), "The item with the supplied id does not exist");
    Item currentItem = getItem(item.getId());
    currentItem.setName(item.getName());
    currentItem.setPrice(item.getPrice());
    currentItem.setDescription(item.getDescription());
    return repository.save(item);
  }

  public void setItemRepository(ItemRepository repository) {
    this.repository = repository;
  }
}
