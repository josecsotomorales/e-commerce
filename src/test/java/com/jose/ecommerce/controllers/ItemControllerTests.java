package com.jose.ecommerce.controllers;

import com.jose.ecommerce.model.persistence.Item;
import com.jose.ecommerce.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTests {

    private ItemController itemController;
    private final ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void init() {
        itemController = new ItemController(itemRepository);
    }

    @Test
    public void findItemById(){
        Item item = new Item();
        item.setId(1L);
        item.setName("theItem");
        item.setDescription("theDescription");
        item.setPrice(BigDecimal.valueOf(10));

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        ResponseEntity<Item> response = itemController.getItemById(1L);

        item = response.getBody();
        assertEquals(200, response.getStatusCodeValue());
        assert item != null;
        assertEquals("theItem", item.getName());
        assertEquals(BigDecimal.valueOf(10),item.getPrice());
    }

    @Test
    public void findItemByName(){
        Item item = new Item();
        item.setId(1L);
        item.setName("theItem");
        item.setDescription("theDescription");
        item.setPrice(BigDecimal.valueOf(10));

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);

        when(itemRepository.findByName("theItem")).thenReturn(itemList);

        ResponseEntity<List<Item>> response = itemController.getItemsByName("theItem");
        List<Item> items = response.getBody();
        assertEquals(200, response.getStatusCodeValue());
        assert items != null;
        assertEquals(1, items.size());
        assertEquals("theItem", items.get(0).getName());
    }

    @Test
    public void findAllItems(){
        Item item = new Item();
        item.setId(1L);
        item.setName("theItem");
        item.setDescription("theDescription");
        item.setPrice(BigDecimal.valueOf(10));

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);

        when(itemRepository.findAll()).thenReturn(itemList);

        ResponseEntity<List<Item>> response = itemController.getItems();
        List<Item> items = response.getBody();
        assertEquals(200, response.getStatusCodeValue());
        assert items != null;
        assertEquals(1, items.size());
        assertEquals("theItem", items.get(0).getName());

    }

}
