package com.capgemini.ms.catalogservice.controller;


import com.capgemini.ms.catalogservice.domain.Item;
import com.capgemini.ms.catalogservice.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
@WebMvcTest(ItemController.class)
public class ItemControllerTest {

  public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8")
  );

  @Autowired
  MockMvc mvc;

  @MockBean
  ItemService itemService;


  @Test
  public void shouldGetItem() throws Exception {
    String itemJson = "{\"id\": 1,\"name\": \"Item 1\",\"description\": \"Item 1\",\"price\": 100,\"version\": 0}";
    Item item = new Item();
    String name = "Item 1";
    String email = "Item1@email.com";
    item.setName("Item 1");
    item.setDescription("Item 1");
    item.setPrice(100f);
    item.setId(1L);

    given(this.itemService.getItem(1L)).willReturn(item);

    this.mvc.perform(get("/items/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("id", is(1)))
        .andExpect(content().json(itemJson));

    verify(this.itemService, times(1)).getItem(1L);
    verifyNoMoreInteractions(this.itemService);
  }

  @Test
  public void shouldGetItems() throws Exception {
    String itemJson = "[{\"id\": 1,\"name\": \"Item 1\",\"description\": \"Item 1\",\"price\": 100,\"version\": 0}]";
    Item item = new Item();
    String name = "Item 1";
    String email = "Item1@email.com";
    item.setName("Item 1");
    item.setDescription("Item 1");
    item.setPrice(100f);
    item.setId(1L);

    List<Item> cList = new ArrayList<Item>();
    cList.add(item);
    given(this.itemService.findItems()).willReturn(cList);

    this.mvc.perform(get("/items").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(content().json(itemJson));

    verify(this.itemService, times(1)).findItems();
    verifyNoMoreInteractions(this.itemService);
  }

  @Test
  public void shouldCreateItem() throws Exception {
    String itemJson = "{\"id\": 1,\"name\": \"Item 1\",\"description\": \"Item 1\",\"price\": 100,\"version\": 0}";
    String updatedItemJson = "{\"id\": 1,\"name\": \"Item 1\",\"description\": \"Item 1\",\"price\": 100,\"version\": 1}";
    Item item = new Item();
    item.setName("Item 1");
    item.setDescription("Item 1");
    item.setPrice(100f);
    item.setVersion(0);
    item.setId(1L);

    Item updatedItem = new Item();
    updatedItem.setName("Item 1");
    updatedItem.setDescription("Item 1");
    updatedItem.setPrice(100f);
    updatedItem.setVersion(1);
    updatedItem.setId(1L);

    given(this.itemService.getItem(1L)).willReturn(item);
    given(this.itemService.update(item)).willReturn(updatedItem);

    this.mvc.perform(post("/items/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(itemJson))
        .andExpect(status().isOk())
        .andExpect(content().json(updatedItemJson));
  }
}
