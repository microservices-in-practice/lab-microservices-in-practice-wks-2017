package com.capgemini.ms.catalogservice.repository;

import com.capgemini.ms.catalogservice.domain.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemRespositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ItemRepository itemRepository;


  @Test
  public void shouldFindItem() {
    // given

    Item item = getItem();
    Item savedItem = entityManager.persist(item);
    entityManager.flush();

    // when

    Item found = itemRepository.findOne(savedItem.getId());

    // then

    assertThat(found.getName())
        .isEqualTo(item.getName());
    assertThat(found.getDescription())
        .isEqualTo(item.getDescription());
    assertThat(found.getPrice())
        .isEqualTo(item.getPrice());
  }

  @Test
  public void shouldSaveItem() {
    // given

    Item item = getItem();
    Item savedItem = itemRepository.save(item);

    entityManager.flush();

    // when

    Item found = entityManager.find(Item.class, savedItem.getId());

    // then


    assertThat(found.getName())
        .isEqualTo(item.getName());
    assertThat(found.getDescription())
        .isEqualTo(item.getDescription());
    assertThat(found.getPrice())
        .isEqualTo(item.getPrice());
  }


  private Item getItem() {
    Item item = new Item();
    item.setName("Item 1");
    item.setDescription("Item 1");
    item.setPrice(100f);

    return item;
  }
}
