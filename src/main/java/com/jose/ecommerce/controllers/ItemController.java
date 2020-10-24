package com.jose.ecommerce.controllers;

import java.util.List;

import com.jose.ecommerce.model.persistence.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jose.ecommerce.model.persistence.repositories.ItemRepository;

@RestController
@RequestMapping("/api/item")
@Slf4j
public class ItemController {

	@Autowired
	private final ItemRepository itemRepository;

	public ItemController(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@GetMapping
	public ResponseEntity<List<Item>> getItems() {
		return ResponseEntity.ok(itemRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable Long id) {
		log.info("Find User By Id: {}", id);
		return ResponseEntity.of(itemRepository.findById(id));
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
		List<Item> items = itemRepository.findByName(name);
		if(items == null || items.isEmpty()) {
			log.info("No Items Found");
			return ResponseEntity.notFound().build();
		} else {
			log.info("Found Items: {}", items);
			return ResponseEntity.ok(items);
		}
	}
	
}
