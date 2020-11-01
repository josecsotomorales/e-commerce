package com.jose.ecommerce.controllers

import com.jose.ecommerce.model.persistence.Item
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.jose.ecommerce.model.persistence.repositories.ItemRepository

@RestController
@RequestMapping("/api/item")
@Slf4j
class ItemController {

	@Autowired
	private final ItemRepository itemRepository

	ItemController(ItemRepository itemRepository) {
		this.itemRepository = itemRepository
	}

	@GetMapping
	ResponseEntity<List<Item>> getItems() {
		return ResponseEntity.ok(itemRepository.findAll())
	}
	
	@GetMapping("/{id}")
	ResponseEntity<Item> getItemById(@PathVariable Long id) {
		return ResponseEntity.of(itemRepository.findById(id))
	}
	
	@GetMapping("/name/{name}")
	ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
		List<Item> items = itemRepository.findByName(name)
		if(items == null || items.isEmpty()) {
			log.info("No Items Found")
			return ResponseEntity.notFound().build()
		} else {
			return ResponseEntity.ok(items)
		}
	}
	
}
