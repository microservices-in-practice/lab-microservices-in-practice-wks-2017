package com.capgemini.ms.orderservice.repository;

import com.capgemini.ms.orderservice.domain.OrderItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderItemRespositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderItemRepository itemRepository;


    @Test
    public void shouldFindItem() {
        // given

        OrderItem item = getItem();
        OrderItem savedItem = entityManager.persist(item);
        entityManager.flush();

        // when

        OrderItem found = itemRepository.findOne(savedItem.getId());

        // then

        assertThat(found.getName())
                .isEqualTo(item.getName());
        assertThat(found.getDescription())
                .isEqualTo(item.getDescription());
        assertThat(found.getPrice())
                .isEqualTo(item.getPrice());
        assertThat(found.getVersion())
                .isEqualTo(item.getVersion());
    }

    @Test
    public void shouldSaveItem() {
        // given

        OrderItem item = getItem();
        OrderItem savedItem = itemRepository.save(item);

        entityManager.flush();

        // when

        OrderItem found = entityManager.find(OrderItem.class, savedItem.getId());

        // then


        assertThat(found.getName())
                .isEqualTo(item.getName());
        assertThat(found.getDescription())
                .isEqualTo(item.getDescription());
        assertThat(found.getPrice())
                .isEqualTo(item.getPrice());
        assertThat(found.getVersion())
                .isEqualTo(item.getVersion());
    }

    @Test
    public void shouldFindByNameAndVersion() {
        // given

        OrderItem item = getItem();
        OrderItem savedItem = itemRepository.save(item);

        entityManager.flush();

        // when

        OrderItem found = itemRepository.findByNameAndVersion(savedItem.getName(), savedItem.getVersion());

        // then

        assertThat(found.getName())
                .isEqualTo(item.getName());
        assertThat(found.getDescription())
                .isEqualTo(item.getDescription());
        assertThat(found.getPrice())
                .isEqualTo(item.getPrice());
        assertThat(found.getVersion())
                .isEqualTo(item.getVersion());
    }


    private OrderItem getItem() {
        OrderItem item = new OrderItem();
        item.setName("Item 1");
        item.setDescription("Item 1");
        item.setPrice(100f);
        item.setVersion(1);

        return item;
    }
}
