package com.capgemini.ms.catalogservice.controller;


import com.capgemini.ms.catalogservice.domain.Item;
import com.capgemini.ms.catalogservice.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {

  @Autowired
  private ItemService itemService;


  @GetMapping(path = "/{id}")
  public ResponseEntity<Item> getItem(@PathVariable long id) {
    return Optional.ofNullable(itemService.getItem(id))
        .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping()
  public ResponseEntity<List<Item>> getItems() {
    return Optional.ofNullable(itemService.findItems())
        .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping()
  public ResponseEntity<Item> saveOrUpdate(@RequestBody Item item) {
    return Optional.ofNullable(item.getId() != null ?
        itemService.update(item) : itemService.create(item))
        .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

}
