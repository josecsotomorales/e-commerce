package com.jose.ecommerce.controllers;

import java.util.Optional;
import java.util.stream.IntStream;

import com.jose.ecommerce.model.persistence.Cart;
import com.jose.ecommerce.model.persistence.Item;
import com.jose.ecommerce.model.persistence.User;
import com.jose.ecommerce.model.persistence.repositories.CartRepository;
import com.jose.ecommerce.model.persistence.repositories.ItemRepository;
import com.jose.ecommerce.model.persistence.repositories.UserRepository;
import com.jose.ecommerce.model.requests.ModifyCartRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@Slf4j
public class CartController {
	
	@Autowired
	private final UserRepository userRepository;
	
	@Autowired
	private final CartRepository cartRepository;
	
	@Autowired
	private final ItemRepository itemRepository;

	public CartController(UserRepository userRepository, CartRepository cartRepository, ItemRepository itemRepository) {
		this.userRepository = userRepository;
		this.cartRepository = cartRepository;
		this.itemRepository = itemRepository;
	}

	@PostMapping("/addToCart")
	public ResponseEntity<Cart> addTocart(@RequestBody ModifyCartRequest request) {
		User user = userRepository.findByUsername(request.getUsername());
		if(user == null) {
			log.info("User Not Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		log.info("User Found: {}", user.getUsername());
		Optional<Item> item = itemRepository.findById(request.getItemId());
		if(item.isEmpty()) {
			log.info("Item Not Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		log.info("Item Found: {}", item.get().getName());
		Cart cart = user.getCart();
		IntStream.range(0, request.getQuantity())
			.forEach(i -> cart.addItem(item.get()));
		cartRepository.save(cart);
		log.info("Cart Items Added");
		return ResponseEntity.ok(cart);
	}
	
	@PostMapping("/removeFromCart")
	public ResponseEntity<Cart> removeFromCart(@RequestBody ModifyCartRequest request) {
		User user = userRepository.findByUsername(request.getUsername());
		if(user == null) {
			log.info("User Not Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		log.info("User Found: {}", user.getUsername());
		Optional<Item> item = itemRepository.findById(request.getItemId());
		if(item.isEmpty()) {
			log.info("Item Not Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		log.info("Item Found: {}", item.get().getName());
		Cart cart = user.getCart();
		IntStream.range(0, request.getQuantity())
			.forEach(i -> cart.removeItem(item.get()));
		cartRepository.save(cart);
		log.info("Cart Items Removed");
		return ResponseEntity.ok(cart);
	}
		
}
