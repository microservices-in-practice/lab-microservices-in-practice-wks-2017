package com.capgemini.ms.catalogservice.service;


import com.capgemini.ms.catalogservice.domain.Item;
import com.capgemini.ms.catalogservice.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@DirtiesContext
public class ItemServiceTest {


  @Autowired
  ItemService itemService;

  @Test
  public void shouldCreateItem() {

    // given

    Item newItem = getNewItem();

    // when

    itemService.create(newItem);

    // then

    verify(ItemServiceImplTestContextConfiguration.repository, times(1)).save(newItem);
  }

  @Test
  public void shouldUpdateItem() {

    // given

    given(ItemServiceImplTestContextConfiguration.repository.findOne(1L)).willReturn(getItem());
    given(ItemServiceImplTestContextConfiguration.repository.exists(1L)).willReturn(true);
    Item item = ItemServiceImplTestContextConfiguration.repository.findOne(1L);
    item.setName("Updated");

    // when

    itemService.update(item);

    // then

    verify(ItemServiceImplTestContextConfiguration.repository, times(1)).save(item);
  }

  @Test
  public void shouldGetItem() {

    // given

    given(ItemServiceImplTestContextConfiguration.repository.findOne(1L)).willReturn(getItem());

    // when

    Item item = itemService.getItem(1L);

    // then

    assertThat(item.getPrice()).isEqualTo(getItem().getPrice());
    assertThat(item.getDescription()).isEqualTo(getItem().getDescription());
    assertThat(item.getName()).isEqualTo(getItem().getName());
  }

  @Test
  public void shouldGetItems() {

    // given

    List<Item> getList = Collections.singletonList(getItem());
    given(ItemServiceImplTestContextConfiguration.repository.findAll()).willReturn(getList);

    // when

    List<Item> getItemList = itemService.findItems();

    // then

    assertThat(getItemList.size()).isEqualTo(1);
    assertThat(getItemList.get(0).getName()).isEqualTo(getItem().getName());
  }

  private Item getItem() {
    Item item = new Item();
    item.setName("Item 1");
    item.setDescription("Item 1");
    item.setPrice(100f);
    item.setId(1L);
    return item;
  }

  private Item getNewItem() {
    Item item = new Item();
    item.setName("Item 1");
    item.setDescription("Item 1");
    item.setPrice(100f);
    return item;
  }

  @TestConfiguration
  static class ItemServiceImplTestContextConfiguration {

    @MockBean
    private static ItemRepository repository;

    @Bean
    public ItemService itemService() {
      ItemService itemService = new ItemService();
      itemService.setItemRepository(repository);
      return itemService;
    }
  }

}
